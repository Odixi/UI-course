package view;

import com.vaadin.ui.CheckBox;

public class HiddenValueCheckBox extends CheckBox {

	private String hiddenValue;
	
	//Constructors
	public HiddenValueCheckBox(String hiddenValue){
		super();
		this.hiddenValue = hiddenValue;
	}
	
	public HiddenValueCheckBox(String hiddenValue, String caption){
		super(caption);
		this.hiddenValue = hiddenValue;
	}

	public HiddenValueCheckBox(String hiddenValue, String caption, boolean initialState){
		super(caption, initialState);
		this.hiddenValue = hiddenValue;
	}
	
	//------ hiddenValue: Getters & setters ----------
	
	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}
	
	
}
