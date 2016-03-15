package view;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.vaadin.data.Container.ItemSetChangeEvent;

import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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

import server.SmartHSystem;

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
	
	//Middle
	private RoomListComponent[] houses;
	private ArrayList<String> users;
	

	public AdminView(SmartUI ui, SmartHSystem shsystem ){
		
		super();
		this.ui = ui;
		this.shsystem = shsystem;
		setMargin(true);
        setSpacing(true);
        setSizeFull();
        
        //Vasemman puolen rakennus
        initLeft();
        
        //Keskiosan rakennus
        initMiddle();
        
        //ja oikea puoli
        initRight();
        
        setExpandRatio(rightLayout, 2);
        setExpandRatio(leftLayout, 1);
        setExpandRatio(middleLayout, 1);
        
	} // Konstruktor
	
	// Vasemman puolen generointi
	private void initLeft(){
		
		// ----- Vasen puoli ----- //
        
        leftLayout = new VerticalLayout();
        leftLayout.setSpacing(true);
		addComponent(leftLayout);
		
		// ---- Label ---- //
		Label editUserLabel = new Label("<font size=\"6\">Edit user</font>");
		editUserLabel.setContentMode(ContentMode.HTML);
		leftLayout.addComponent(editUserLabel);
        
        // ----- User Select / Remove / Create ----- //
        
        // User remove / create rivi
        userSelectLayout = new HorizontalLayout();
        userSelectLayout.setSpacing(true);
        leftLayout.addComponent(userSelectLayout);
        
        // Käyttäjä lisäys nappi
        CreateUserPopupContent cu = new CreateUserPopupContent(shsystem, this);
        createUser = new PopupView(cu);
        cu.setPopupView(createUser); // jotta saadaan instanssi tuosta PopupViewistä content luokkaan
        userSelectLayout.addComponent(createUser);
        
        // Käyttäjän poistamis nappi
        // Olisi varmaan voinut tehdä fiksumminkin mutta...
        RemoveUserPopupContent pc = new RemoveUserPopupContent(shsystem, this);
        removeUser = new PopupView(pc);
        pc.setPopupView(removeUser);
        userSelectLayout.addComponent(removeUser);
        removeUser.addPopupVisibilityListener(new PopupVisibilityListener() {
        	@Override
			public void popupVisibilityChange(PopupVisibilityEvent event) {
				pc.setValues((String)userSelect.getValue()); //Päivitetään popupViewi
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
				Notification.show(userSelect.getValue() + " selected");
				updateContent();
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
				// TODO Tallennetaan tiedot
				
			}
		});
        leftLayout.addComponent(saveChanges);
		
	}
	// Keskiosan generointi
	private void initMiddle(){
		
        // ---------- Keskimmäinen layout --------- //
        
        middleLayout = new VerticalLayout();
        addComponent(middleLayout);
        
        // ----- Oikeuksien valinta älykotiin ----- //
        
        try {
			ArrayList<String> housesNames = shsystem.getHouseNames();
			houses = new RoomListComponent[housesNames.size()];
			
			for (int i = 0; i < housesNames.size(); i++){
				houses[i] = new RoomListComponent(housesNames.get(i), shsystem);
				middleLayout.addComponent(houses[i]);
			}
		} catch (RemoteException e) {e.printStackTrace();}
        
	}
	
	// Oikean puolen generointi
	public void initRight(){
        // ---------- Oikea puoli ---------- //
        
        rightLayout = new VerticalLayout();
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
        
	}
	
	// Hakee serveriltä valitun käyttäjän tiedot ja päivättää ne
	private void updateContent(){
		//TODO
		if (userSelect.getValue() == null){
			usernameField.setValue("");
		}
		else{
			usernameField.setValue((String)userSelect.getValue());
		}
	}
	
	// Haetaan käyttäjät serveriltä
	public void updateUserList(){
		userSelect.removeAllItems();
        try {
			users = shsystem.getUsernames();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
        userSelect.addItems(users);
	}
	
	// Palauttaa valitun käyttäjän
	public String getSelectedUser(){
		return (String)userSelect.getValue();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

} // AdminView

/*
 * Sisältä mikä näytetäänkun painetaan Create usernappia
 * -> Tekstikenttä mihin syötetään uuden käyttäjän nimi
 * 
 * Ajatus olisi se, että ensin luodaan uusi käyttäjä anoastaan nimellä, ja
 * myöhemmin sen muita ominasuuksia voidaan muokata käyttäjien muakkausnäkymässä
 */
class CreateUserPopupContent implements PopupView.Content{
	
	private HorizontalLayout hl;
	private TextField tf;
	private Button ok;
	private PopupView pv;
	
	public CreateUserPopupContent(SmartHSystem sh, AdminView av) {
		hl = new HorizontalLayout();
		tf = new TextField("Create new user");
		tf.setInputPrompt("Username");
		hl.addComponent(tf);
		ok = new Button("Ok", new Button.ClickListener() {
			
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
					} catch (RemoteException e) {e.printStackTrace();}
				}
			}
		});
		hl.addComponent(ok);
	}

	@Override
	public String getMinimizedValueAsHTML() {
		return "Create new user";
	}

	@Override
	public Component getPopupComponent() {
		return hl;
	}
	public void setPopupView(PopupView pv){
		this.pv = pv;
	}
	
}

/*
 * Sisältö mikä näytetäänkun painetaan Remove ures nappia
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
		ays = new Label();
		vlayout.addComponent(ays);
		hlayout = new HorizontalLayout();
		vlayout.addComponent(hlayout);
		yes = new Button("Yes", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (user == null){
					Notification.show("Select a user first!");
				}
				else{
					try {
						sh.deleteUser(av.getSelectedUser());
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
