package view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.vaadin.data.Container.ItemSetChangeEvent;

import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.PopupView.PopupVisibilityListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import exceptions.ElementNullException;
import server.SmartHSystem;
/**
 * A view for admins to manage users and their views.
 * @author Ville
 *
 */
public class AdminView extends HorizontalLayout implements View{
	
	private SmartHSystem shsystem;
	private SmartUI ui;
	
	private VerticalLayout leftLayout;
	private VerticalLayout middleLayout;
	private VerticalLayout rightLayout;
	
	//Left side
	private HorizontalLayout userSelectLayout;
	private ComboBox userSelect;
	private PopupView removeUser;
	private PopupView createUser;
	private TextField usernameField;
	private PasswordField passwordField;
	private Button saveChanges;
	
	//Right side
	private Button logoutButton;
	private Button manageHouses;
	
	//Middle
	Label userViewSelectLabel;
	private RoomListComponent[] houses;
	private ArrayList<String> users;
	
	private Hashtable<String, String> userList;
	
	// ********** KONSTRUKTORI ********** //
	 /** Construktor to build the view
	 * @param ui, the UI, to which the view is build
	 * @param shsystem, SmartHSystem for RMI calls to interact with the backend
	 */
	public AdminView(SmartUI ui, SmartHSystem shsystem ){
		
		super();
		this.ui = ui;
		this.shsystem = shsystem;
		setMargin(true);
        setSpacing(true);
 
        //Vasemman puolen rakennus
        initLeft();
        
        //Keskiosan rakennus
        initMiddle();
        
        //ja oikea puoli
        initRight();
        
        middleLayout.setWidth(500f, Unit.PIXELS);
        
	} // Konstruktor
	
	
	// ********** VASEN PUOLI ********** //
	
	private void initLeft(){
		
		// ----- Vasen puoli ----- //
        
        leftLayout = new VerticalLayout();
        leftLayout.setSpacing(true);
		addComponent(leftLayout);
		
		// ---- Edit user -Label ---- //
		Label editUserLabel = new Label("<font size=\"6\">Edit user</font>");
		editUserLabel.setContentMode(ContentMode.HTML);
		leftLayout.addComponent(editUserLabel);
        
        // ----- User Select / Remove / Create ----- //
        
        // User remove / create rivi
        userSelectLayout = new HorizontalLayout();
        userSelectLayout.setSpacing(true);
        leftLayout.addComponent(userSelectLayout);
        
        // Create user button
        CreateUserPopupContent cu = new CreateUserPopupContent(shsystem, this);
        createUser = new PopupView(cu);
        cu.setPopupView(createUser); // jotta saadaan instanssi tuosta PopupViewistä content luokkaan
        userSelectLayout.addComponent(createUser);
        
        // Delete user button
        // Olisi varmaan voinut tehdä fiksumminkin mutta...
        RemoveUserPopupContent pc = new RemoveUserPopupContent(shsystem, this);
        removeUser = new PopupView(pc);
        pc.setPopupView(removeUser);
        userSelectLayout.addComponent(removeUser);
        removeUser.addPopupVisibilityListener(new PopupVisibilityListener() {
        	@Override
			public void popupVisibilityChange(PopupVisibilityEvent event) {
				pc.setValues((String)userSelect.getItemCaption(userSelect.getValue())); //Päivitetään popupViewi
			}
		});
        
        // userSelect lista
        userSelect = new ComboBox("User");
        userSelect.setFilteringMode(FilteringMode.CONTAINS);
        userSelect.setTextInputAllowed(false);
        
        //Kun vaihdetaan comboBoxin arvoa, niin päivitetään näkymä vastaamaan käyttäjän tietoja
        userSelect.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show( getSelectedUsername() + " selected"); //Show the name of the selected user
				updateContent();
				
				for (int i = 0; i < houses.length; i++){
					houses[i].updateCheckBoxesFromServer();
				}
			}
		});

        // Käyttäjien haku
        updateUserList();
        leftLayout.addComponent(userSelect);
        
        
        // ----- Username and Password texfields ------ //
        
        usernameField = new TextField("Username");
        leftLayout.addComponent(usernameField);
        
        passwordField = new PasswordField("Pasword");
        leftLayout.addComponent(passwordField);
        
        
        // ----- Save changes button ----- //
        
        saveChanges = new Button("Save Changes", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (userSelect.getValue() == null){
					Notification.show("Select a user first!");
					return;
				}
				boolean needsUpdate = false;
				
				// Käyttäjänimen ja / tai salasanan muutos
				try {
					if (usernameField.getValue().length() < 3 || usernameField.getValue().length() > 24){
						Notification.show("Username must be 3-24 characters long \nNo changes made!");
						return;
					}
					
					if (passwordField.getValue() != null && passwordField.getValue() != ""){
						if ( !shsystem.passwordValid(passwordField.getValue()) ){
							Notification.show("Password must be 8-24 characters long. \nNo changes made!");
							return;
						}
						shsystem.changePasswordAdmin((String)userSelect.getItemCaption(userSelect.getValue()), passwordField.getValue());
					}
					if (!userSelect.getItemCaption(userSelect.getValue()).toString().equals(usernameField.getValue())){
						if( shsystem.usernameValid(usernameField.getValue()) ){
							shsystem.changeUsername((String)userSelect.getItemCaption(userSelect.getValue()), usernameField.getValue());
							needsUpdate = true;
						} else {
							Notification.show("Username must be 3-24 characters long. No changes made!");
							return;
						}
					}
					
					
				} catch (RemoteException e) {e.printStackTrace();}
				
				// Laitetaan userViewValue hashtableen chackboxien avaimet ja arvot
				Hashtable<String, Boolean> userViewValues = new Hashtable<>();
				for (int i = 0; i < houses.length; i++){
					ArrayList<HiddenValueCheckBox> cbList = houses[i].getCheckBoxes();
					for (int j = 0; j < cbList.size(); j++){
							userViewValues.put(cbList.get(j).getHiddenValue(), cbList.get(j).getValue());
					} // for j
				} // for i
				
				try {
					
					if(!shsystem.setUserView( getSelectedUserID(), userViewValues)){
						Notification.show("Couldn't save userview!");
						return;
					}
					
				} catch (RemoteException e) {e.printStackTrace();} catch (ElementNullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (needsUpdate){
					updateUserList();
				}
				Notification.show("Changes saved");
				
			}// Click listener
		});
        leftLayout.addComponent(saveChanges);
		
	}
	
	// ********** KESKIOSA ********** //
	
	private void initMiddle(){
		
        // ---------- Keskimmäinen layout --------- //
        
        middleLayout = new VerticalLayout();
        addComponent(middleLayout);
        middleLayout.setMargin(true);
        middleLayout.setSpacing(true);
        
        // ------- Labels ------- //
        
        userViewSelectLabel = new Label("<font size=\"4\">Edit view</font>");
        userViewSelectLabel.setContentMode(ContentMode.HTML);
        middleLayout.addComponent(userViewSelectLabel);
        middleLayout.setExpandRatio(userViewSelectLabel, 1f);
          
        
        // ----- Oikeuksien valinta älykotiin ----- //
        
        try {
        	Hashtable<String, String> housesNames = shsystem.getHouseNames();
			houses = new RoomListComponent[housesNames.size()];
			
			int i = 0;
			
			for (String key : housesNames.keySet()){
				houses[i] = new RoomListComponent(key, housesNames.get(key), shsystem, this); //'key' is the ID of the house
				middleLayout.addComponent(houses[i]);
				middleLayout.setComponentAlignment(houses[i], Alignment.TOP_LEFT);
				i++;
			}
			
			/*
			for (int i = 0; i < housesNames.size(); i++){
				houses[i] = new RoomListComponent(housesNames.get(i), shsystem);
				middleLayout.addComponent(houses[i]);
				middleLayout.setComponentAlignment(houses[i], Alignment.TOP_LEFT);
			}*/
		} catch (RemoteException e) {e.printStackTrace();}
        
	}
	
	
	// ********** OIKEA PUOLI ********** //
	
	private void initRight(){
        // ---------- Oikea puoli ---------- //
        
        rightLayout = new VerticalLayout();
        rightLayout.setSpacing(true);
        addComponent(rightLayout);
        
		// ----- Logout button ----- //
		
		logoutButton = new Button("Logout",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
                //TODO Uloskirjaus
            }
        });
        rightLayout.addComponent(logoutButton);
        rightLayout.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
        
        // -------- Manage house items state button ------ //
      
        manageHouses = new Button("Manage houses", 
        		new Button.ClickListener() {
						@Override
			public void buttonClick(ClickEvent event) {
				ui.getNavigator().addView(ui.ADMINHOUSEVIEW, new AdminHouseView(ui, shsystem));
				ui.getNavigator().navigateTo(ui.ADMINHOUSEVIEW);			
			}
		});
        rightLayout.addComponent(manageHouses);
                
	}
	
	// When user is changed, change the usernameField and UserViewSelectLabel to represent the right user
	private void updateContent(){
		if (userSelect.getValue() == null){
			usernameField.setValue("");
			passwordField.setValue("");
			userViewSelectLabel.setValue("<font size=\"4\">Edit View</font>");
		}
		else{
			usernameField.setValue((String)userSelect.getItemCaption(userSelect.getValue()));
			userViewSelectLabel.setValue("<font size=\"4\">Edit " + userSelect.getItemCaption( userSelect.getValue().toString() ) + "'s view" +  "</font>");
			passwordField.setValue("");
		}
	}
	
	/**
	 * Updates the list of users from server
	 */
	public void updateUserList(){
		userSelect.removeAllItems();
        try {
			userList = shsystem.getUsers();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
     
       /*//CHANGE
        * I used this idea to bind userIDs to the Combobox
        * https://vaadin.com/docs/-/part/framework/components/components-selection.html#components.selection.adding
        * - Pilvi
        */
       for(String userID : userList.keySet()){
    	   userSelect.addItem(userID);
    	   userSelect.setItemCaption(userID, userList.get(userID));
    	   
    	   /* Emxample of getting the values
    	   String id = (String) userSelect.getValue();
    	   userSelect.getItemCaption(id);
    	   
    	   Compact:
    	   userSelect.getItemCaption( (String) userSelect.getValue() );
    	   */
       }
	}
	/**
	 * 
	 * @return String username, Currently selected users username
	 */
	public String getSelectedUsername(){
		//getValue returns userID
		//getItemCaption gives the username
		if (userSelect.getValue() == null){
			return null;
		}
		return userSelect.getItemCaption( userSelect.getValue().toString() );
	}
	/**
	 * 
	 * @return String userID, Currently selected users userID
	 */
	public String getSelectedUserID(){
		if (userSelect.getValue() == null){
			return null;
		}
		return userSelect.getValue().toString();
	}
	
	/**
	 * Just so after new user is created, it can be selected rightaway
	 */
	protected void setSelectedUser(String userID){
		userSelect.setValue(userID);
		updateContent();
		for (RoomListComponent rlc : houses){
			rlc.updateCheckBoxesFromServer();
		}
	}
	/**
	 * 
	 * @return ComboBox userSelect
	 */
	public ComboBox getComboBox(){
		return userSelect;
	}
	/**
	 * This method is run when user enters the view
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

} // AdminView

/*
 * Sisältä mikä näytetään kun painetaan Create usernappia
 * -> Tekstikenttä mihin syötetään uuden käyttäjän nimi
 * 
 * Ajatus olisi se, että ensin luodaan uusi käyttäjä anoastaan nimellä, ja
 * myöhemmin sen muita ominasuuksia voidaan muokata käyttäjien muakkausnäkymässä
 */
/**
 * This class is used to build the popup views contet, when the 'Create user' button is pressed in AdminView
 */
class CreateUserPopupContent implements PopupView.Content{
	
	private HorizontalLayout hl;
	private TextField tf;
	private Button ok;
	private PopupView pv;
	
	/**
	 * 
	 * @param sh, SmartHSystem for RMI calls
	 * @param av, AdminView where this is created in
	 */
	public CreateUserPopupContent(SmartHSystem sh, AdminView av) {
		hl = new HorizontalLayout();
		hl.setMargin(true);
		tf = new TextField("Create new user");
		tf.setInputPrompt("Username");
		hl.addComponent(tf);
		ok = new Button("Create user", new Button.ClickListener() {
			
			@Override // Luodaan uusi käyttäjä
			public void buttonClick(ClickEvent event) {
				if (tf.getValue().length() < 3 || tf.getValue().length() > 25){
					Notification.show("Username is not valid!");
				}
				else{
				try {
						sh.newUser(tf.getValue(), "password"); // Oletus salsana: password
						Notification.show("User " + tf.getValue() + " created");
						pv.setPopupVisible(false);
						av.updateUserList();
						av.setSelectedUser(sh.getUserID(tf.getValue()));
					} catch (RemoteException e) {e.printStackTrace();}
				}
			}
		});
		hl.addComponent(ok);
		hl.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
	}
	/**
	 * @return The HTML caption where this view is invoked from
	 */
	@Override
	public String getMinimizedValueAsHTML() {
		return "Create new user";
	}
	/**
	 * @return Component component, Component which represents the view
	 */
	@Override
	public Component getPopupComponent() {
		return hl;
	}
	/**
	 * To set the Corresponding popupview
	 * @param pv
	 */
	public void setPopupView(PopupView pv){
		this.pv = pv;
	}
	
}

/**
 * This class is used to build the popup views contet, when the 'Remove user' button is pressed in AdminView
 */
class RemoveUserPopupContent implements PopupView.Content{
	
	private VerticalLayout vlayout;
	private HorizontalLayout hlayout;
	private Label ays;
	private Button yes;
	private Button no;
	
	private String user;
	private PopupView pv;
	
	public RemoveUserPopupContent(SmartHSystem sh, AdminView av){
		vlayout = new VerticalLayout();
		vlayout.setMargin(true);
		ays = new Label();
		vlayout.addComponent(ays);
		hlayout = new HorizontalLayout();
		hlayout.setSpacing(true);
		vlayout.addComponent(hlayout);
		yes = new Button("Yes", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (user == null){
					Notification.show("Select a user first!");
				}
				else{
					try {
						sh.deleteUser(av.getSelectedUsername());
						Notification.show(user + " Deleted");
						av.updateUserList();
					} catch (RemoteException e) {e.printStackTrace();}
				}
				pv.setPopupVisible(false);
			}
		});
		
		no = new Button("No", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				pv.setPopupVisible(false);
			}
		});
		
		hlayout.addComponent(yes);
		hlayout.addComponent(no);
	}

	@Override
	public String getMinimizedValueAsHTML() {
		return "Remove user";
	}

	@Override
	public Component getPopupComponent() {
		return vlayout;
	}
	
	public void setPopupView(PopupView pv){
		this.pv = pv;
	}
	
	public void setValues(String u){
		user = u;
		ays.setValue("Delete " + user + "?");
	}
}
