package view;
 
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import server.SmartHSystem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;
import com.vaadin.ui.Slider;
import com.vaadin.server.FontAwesome;
 
public class UserView extends VerticalLayout implements View{
	
	//Attributes
	private SmartHSystem shsystem;
	private HouseTabSheet htb;
 
    public UserView(SmartUI ui, SmartHSystem shsystem){
    	
    	super();
		
		//For RMI calls
		this.shsystem = shsystem;
		
		setMargin(true);
		setSpacing(true);
       
        //Luodaan pohja leiskaan tulevat vaakaleiskat
		HorizontalLayout navigation = new HorizontalLayout();
        navigation.setWidth("100%");
 
        
     // ----- Kodin valinta----- //   
        ComboBox houseSelect = new ComboBox();
        houseSelect.setInputPrompt("Change home");
        houseSelect.setFilteringMode(FilteringMode.CONTAINS);
        houseSelect.setTextInputAllowed(false);
        houseSelect.setNullSelectionAllowed(false);
        
        Hashtable<String, String> homes = new Hashtable<String, String>();  
        
		try {
			homes = shsystem.getHouseNames();
		} catch (RemoteException e) {e.printStackTrace();}

		
		for (String houseID : homes.keySet()){
			houseSelect.addItem(houseID);
			houseSelect.setItemCaption(houseID, homes.get(houseID));
			houseSelect.select(houseID); // Koska haluan, että joku arvo on valmiiksi valittu, 
		}
       
       houseSelect.addValueChangeListener(new ValueChangeListener() {	
    	   @Override
			public void valueChange(ValueChangeEvent event) {
    		removeComponent(htb);
    		htb = new HouseTabSheet(shsystem, ui, (String)houseSelect.getValue()); 
			addComponent(htb);
			
		}
       });
       

        Button logOut= new Button("LogOut",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
            }
        });
        logOut.setIcon(FontAwesome.SIGN_OUT);
        navigation.addComponent(houseSelect);
        navigation.setComponentAlignment(houseSelect, Alignment.MIDDLE_LEFT);
        navigation.addComponent(logOut);
        navigation.setComponentAlignment(logOut, Alignment.MIDDLE_RIGHT);
        
        
        addComponent(navigation);
		htb = new HouseTabSheet(shsystem, ui, (String)houseSelect.getValue());
		addComponent(htb);
    }
       
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        Notification.show("userview");
       
    }
   
 
}
