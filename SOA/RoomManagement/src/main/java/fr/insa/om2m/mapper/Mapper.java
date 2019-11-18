package fr.insa.om2m.mapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Mapper implements MapperInterface {

	private static final String context = "org.eclipse.om2m.commons.resource";

	private JAXBContext ctx;
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;

	public Mapper() {
		try {
			ctx = JAXBContext.newInstance(context);
			unmarshaller = ctx.createUnmarshaller();
			marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException e) {
			throw new IllegalArgumentException(
					"Provided context is not correct for JAXBContext", e);
		}
	}

	@Override
	public String marshal(Object obj) {
		StringWriter sw = new StringWriter();
		String payload = null;
		try {
			marshaller.marshal(obj, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		payload = sw.toString();
		return payload;
	}

	@Override
	public Object unmarshal(String representation) {
		Object result = null;
		InputStream repInputStream = new ByteArrayInputStream(representation.getBytes());
		try {
			result = unmarshaller.unmarshal(repInputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

}
