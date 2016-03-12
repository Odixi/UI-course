package view;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
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

public class AdminView extends HorizontalLayout implements View{
	
	Button logoutButton;
	VerticalLayout leftLayout;
	VerticalLayout rightLayout;
	HorizontalLayout userSelectLayout;
	ComboBox userSelect;
	PopupView removeUser;
	PopupView createUser;
	TextField usernameField;
	PasswordField passwordField;
	Button saveChanges;

	public AdminView(SmartUI ui){
		
		super();
		setMargin(true);
        setSpacing(true);
        setSizeFull();
		// ----- Vasen puoli sivusta ----- //
        
        leftLayout = new VerticalLayout();
        leftLayout.setSpacing(true);
		addComponent(leftLayout); //Vasen puoli sivusta
        
        // ----- User Select / Remove / Create ----- //
        
        // User remove / create rivi
        userSelectLayout = new HorizontalLayout();
        userSelectLayout.setSpacing(true);
        leftLayout.addComponent(userSelectLayout);
        
        // Käyttäjä lisäys nappi
        CreateUserPopupContent cu = new CreateUserPopupContent();
        createUser = new PopupView(cu);
        cu.setPopupView(createUser); // jotta saadaan instanssi tuosta PopupViewistä content luokkaan
        userSelectLayout.addComponent(createUser);
        
        // Käyttäjän poistamis nappi
        // Olisi varmaan voinut tehdä fiksumminkin mutta...
        RemoveUserPopupContent pc = new RemoveUserPopupContent();
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
        
        //Kun vaihdetaan comboBoxin arvoa, niin...
        userSelect.addValueChangeListener(new ValueChangeListener() {		
			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show("" + userSelect.getValue());
				//TODO näkymän päivitys!
			}
		});
        //Väliakaiset testiarvot käyttäjille
        String[] kayt = {"Ville", "Pilvi", "Jenni", "Elmo"};
        userSelect.addItems(kayt);
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
        
        
        // ---------- Oikea puoli sivusta ---------- //
        
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
        
        // ----- Oikeuksien valinta älykotiin ----- //
        
        generateHouseStructure();
        
        
        
	} // Konstruktor
	
	
	// Tehdään lista taloista / houneista / esineistä
	private void generateHouseStructure(){
		// TODO taas väliakaiset testit, korjataan, kun mallit valmistuu
	
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}

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
	
	public CreateUserPopupContent() {
		hl = new HorizontalLayout();
		tf = new TextField("Create new user");
		tf.setInputPrompt("Username");
		hl.addComponent(tf);
		ok = new Button("Ok", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Uuden käyttäjän luonti / ehdot nimelle
				Notification.show("User " + tf.getValue() + " created");
				pv.setPopupVisible(false);
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
	
	public RemoveUserPopupContent(){
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
					pv.setPopupVisible(false);
				}
				else{
				//TODO Poistetaan käyttäjä 'String user'
				Notification.show(user + " Deleted");
				pv.setPopupVisible(false);
				}
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
