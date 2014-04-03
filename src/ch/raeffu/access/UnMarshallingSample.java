package ch.raeffu.access;

import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import ch.raeffu.generated.PersonType;
import ch.raeffu.generated.PersonsType;
import ch.raeffu.tools.XMLValidation;

public final class UnMarshallingSample {
	
	private UnMarshallingSample(){}
	
	private final static String PACKAGE = "ch.raeffu.generated";
	private final static String XSD_FILE = "schema/persons.xsd";
	private final static String XML_FILE = "personsCreated.xml";

	public static void main(String[] l){
		
		// First of all: Validate the  XML file against it's schema
		if (!XMLValidation.validateXMLSchema(XSD_FILE, XML_FILE)) {
			System.out.println("THE XML file " + XML_FILE+ " is not valide agains " + XSD_FILE);
			System.exit(0);
		}
		else
			System.out.println("THE XML file " + XML_FILE+ " is valide agains " + XSD_FILE);

		try {
			// Get the context
			JAXBContext context = JAXBContext.newInstance(PACKAGE);
			
			// Get an unmarshaller object from the context
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Define a source (=our XML file)
			Source source = new StreamSource(new java.io.FileInputStream(XML_FILE));
			
			// UnMarshall the XML content and get access to the root element
			JAXBElement<PersonsType> root = unmarshaller.unmarshal(source,PersonsType.class);
			
			// Our root element can deliver a persons object (of type PersonsType)
			PersonsType pesons = root.getValue();
			
			// The persons object delivers a list of PersonType objects
			List<PersonType> personsList = pesons.getPerson();
			// These are real java objects and you can work with them
			
			// Display them
			for(PersonType p: personsList) System.out.println(p.getFirstName());
		}
		catch (JAXBException e) {e.printStackTrace();}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
}
