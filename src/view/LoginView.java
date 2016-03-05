package view;

import com.google.gwt.cell.client.ButtonCellBase.DefaultAppearance.Style;
import com.google.gwt.user.client.ui.ClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class LoginView extends VerticalLayout implements View{

	public LoginView(SmartUI ui){
		super();
		
		// ---------- Navigaatio nappulat ---------- //
		
        Button userButton = new Button("Go to UserView",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.USERVIEW);
            }
        });
        Button adminButton = new Button("Go to AdminView",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.ADMINVIEW);
            }
        });
        addComponent(userButton);
        setComponentAlignment(userButton, Alignment.MIDDLE_CENTER);
        addComponent(adminButton);
        setComponentAlignment(adminButton, Alignment.MIDDLE_CENTER);
        
        
     // ---------- Navigaatio nappulat /END---------- //
		
        
     // ----- Login as admin button ----- //   
		Button toAdminLogin = new Button("Login as admin",
				new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ui.getNavigator().navigateTo(ui.ADMINLOGINVIEW);
			}
		});
		addComponent(toAdminLogin);
		setComponentAlignment(toAdminLogin, Alignment.TOP_LEFT);
        
        
     // ----- Käyttäjän valinta ----- //   
        ComboBox userSelect = new ComboBox("Select user");
        userSelect.setInputPrompt("No user selected");
//        userSelect.setWidth(10.0f, Unit.PERCENTAGE);
        userSelect.setFilteringMode(FilteringMode.CONTAINS);
        userSelect.setImmediate(true);
        userSelect.setNullSelectionAllowed(false);
        String[] kayt = {"Ville", "Pilvi", "Jenni", "Elmo"}; //Testiksi
        userSelect.addItems(kayt);
        addComponent(userSelect);
        setComponentAlignment(userSelect, Alignment.MIDDLE_CENTER);
        
      // ----- Salasana ----- //  
        PasswordField passwordField = new PasswordField("Password");
        addComponent(passwordField);
        setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);
        
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
}
