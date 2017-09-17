package com.github.frajimiba.commonstruct.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public final class JAXBUtil {
	
	public static <T> String marshall(Class<T> type, T element, Map<String, Object> properties) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(type);
		StringWriter sw = new StringWriter();
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		if (properties!=null && properties.size()>0){
			Iterator<Entry<String, Object>> it = properties.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Object> entry = it.next();
				marshaller.setProperty(entry.getKey(), entry.getValue());
			}
		}
		
		marshaller.marshal(element, sw);
		return sw.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<T> type, String xml,  Map<String, Object> properties) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(type);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
		if (properties!=null && properties.size()>0){
			Iterator<Entry<String, Object>> it = properties.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Object> entry = it.next();
				unmarshaller.setProperty(entry.getKey(), entry.getValue());
			}
		}
		StringReader reader = new StringReader(xml);
		return (T) unmarshaller.unmarshal(reader);
	}
}
