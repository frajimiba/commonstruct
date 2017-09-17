package com.github.frajimiba.commonstruct.audit;

import java.util.ArrayList;
import java.util.List;

public class RevisionChange {
	
	private String entityName;
	private List<RevisionDataChange> dataChanges = new ArrayList<RevisionDataChange>();

	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entity) {
		this.entityName = entity;
	}
	public List<RevisionDataChange> getDataChanges() {
		return dataChanges;
	}
	public void setDataChanges(List<RevisionDataChange> dataChanges) {
		this.dataChanges = dataChanges;
	}
	
	
}
