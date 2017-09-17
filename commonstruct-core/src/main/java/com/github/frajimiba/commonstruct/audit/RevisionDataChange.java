package com.github.frajimiba.commonstruct.audit;

public class RevisionDataChange {

	private String propertyName;
	private Object actualValue;
	private Object previousValue;
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Object getActualValue() {
		return actualValue;
	}
	public void setActualValue(Object actualvalue) {
		this.actualValue = actualvalue;
	}
	public Object getPreviousValue() {
		return previousValue;
	}
	public void setPreviousValue(Object previousValue) {
		this.previousValue = previousValue;
	}
	
}
