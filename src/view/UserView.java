package view;
 
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
 
 
public class UserView extends VerticalLayout implements View{
 
    public UserView(SmartUI ui){
        setHeight(ui.getCurrent().getPage().getBrowserWindowHeight()*0.6f, Unit.PIXELS);
       
        //Luodaan pohja leiskaan tulevat vaakaleiskat
        HorizontalLayout topics = new HorizontalLayout();
        topics.setHeight("20%");
        topics.setWidth("60%");
       
        HorizontalLayout lights =new HorizontalLayout();
        lights.setHeight("35%");
        lights.setWidth("60%");
       
        HorizontalLayout rooms = new HorizontalLayout();
        rooms.setHeight("35%");
        rooms.setWidth("60%");
       
       
       
        /*//NÃ¤mÃ¤ vaan testiÃ¤ varten. Voi poistaa.
        Button button = new Button("Go to LoginView",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ui.getNavigator().navigateTo(ui.LOGINVIEW);
            }
        });*/
       
        //Huone "otsikot"
        topics.addComponent(new Label ("Rooms"));
        topics.addComponent(new Label ("Room 1"));
        topics.addComponent(new Label ("Room 2"));
        topics.addComponent(new Label ("Room 3"));
        
        
        //Lights valinnat
        lights.addComponent(new Label ("Lights"));
        CheckBox room_1 = new CheckBox("On", true);
        lights.addComponent(room_1);
        CheckBox room_2 = new CheckBox("On", true);
        lights.addComponent(room_2);
        CheckBox room_3 = new CheckBox("On", true);
        lights.addComponent(room_3);
 
        //Rooms valinnat
        Button room1 = new Button("More", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
               
            }
        });
        Button room2 = new Button("More", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
               
            }
        });
        Button room3 = new Button("More", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
               
            }
        });
        rooms.addComponent(new Label(""));
        rooms.addComponent(room1);
        rooms.addComponent(room2);
        rooms.addComponent(room3);
       
       
       
        //Lopuksi lisätään nää kaikki oikeassa järjestyksessä layouttiin
        //addComponent(button);
        addComponent(topics);
        addComponent(lights);
        addComponent(rooms);
    }
       
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        Notification.show("userview");
       
    }
   
 
}
