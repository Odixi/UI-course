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
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;

public class AdminView extends VerticalLayout implements View{

	public AdminView(SmartUI ui){
		
		super();
		setMargin(true);
        
		// ----- Logout button ----- //
		
		Button logoutButton = new Button("Logout",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
                //TODO Uloskirjaus
            }
        });
        addComponent(logoutButton);
        setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
        
        
        // ----- User Select ----- //
        
        // User select, remove user, create new usert -rivi
        HorizontalLayout userSelectLayout = new HorizontalLayout();
        addComponent(userSelectLayout);
        
        // userSelect comboboxi
        ComboBox userSelect = new ComboBox("User");
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
        userSelectLayout.addComponent(userSelect);
    
        PContent pc = new PContent();
        PopupView popup = new PopupView(pc);
        userSelectLayout.addComponent(popup);
        popup.addPopupVisibilityListener(new PopupVisibilityListener() {
        	@Override
			public void popupVisibilityChange(PopupVisibilityEvent event) {
				pc.setValues((String)userSelect.getValue() ,popup);
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}

/*
 * Popupin sisältö, kun kysytään käyttäjältä, onko varma, että haluaa poistaa
 * valitun käyttäjän
 */
class PContent implements PopupView.Content{
	
	private VerticalLayout vlayout;
	private HorizontalLayout hlayout;
	private Label ays;
	private Button yes;
	private Button no;
	
	private String user;
	private PopupView pv;
	
	public PContent(){
		
		vlayout = new VerticalLayout();
		ays = new Label();
		vlayout.addComponent(ays);
		hlayout = new HorizontalLayout();
		vlayout.addComponent(hlayout);
		yes = new Button("Yes", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				//TODO Poistetaan käyttäjä
				Notification.show(user + " Deleted");
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
	
	public void setValues(String u, PopupView popv){
		pv = popv;
		user = u;
		ays.setValue("Delete " + user + "?");
	}
	
	
}
