package com.github.frajimiba.commonstruct.audit.envers;

import javax.persistence.MappedSuperclass;
import org.hibernate.envers.RevisionTimestamp;

import com.github.frajimiba.commonstruct.audit.RevisionEntity;

@MappedSuperclass
public abstract class EnversRevisionEntity<PK extends Number> extends RevisionEntity<PK> {

	private static final long serialVersionUID = 2524593846389694660L;
	
	@RevisionTimestamp
	private long timestamp;

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp
	 *            the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}