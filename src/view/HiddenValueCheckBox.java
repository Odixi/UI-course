package view;

import com.vaadin.ui.CheckBox;
/**
 * 
 * Just adds a new hiddenValue to CheckBox component.
 *
 * There actually is another way to store hidden data inside components, but we'll stick with this.
 */
public class HiddenValueCheckBox extends CheckBox {

	private String hiddenValue;
	
	/**
	 * 
	 * @param hiddenValue
	 */
	public HiddenValueCheckBox(String hiddenValue){
		super();
		this.hiddenValue = hiddenValue;
	}
	/**
	 * 
	 * @param hiddenValue
	 * @param caption
	 */
	public HiddenValueCheckBox(String hiddenValue, String caption){
		super(caption);
		this.hiddenValue = hiddenValue;
	}
	/**
	 * 
	 * @param hiddenValue
	 * @param caption
	 * @param initialState
	 */
	public HiddenValueCheckBox(String hiddenValue, String caption, boolean initialState){
		super(caption, initialState);
		this.hiddenValue = hiddenValue;
	}
	
	//------ hiddenValue: Getters & setters ----------
	
	/**
	 * 
	 * @return hiddenValue
	 */
	public String getHiddenValue() {
		return hiddenValue;
	}
	/**
	 * 
0	 * @param hiddenValue
	 */
	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}
	
	
}
