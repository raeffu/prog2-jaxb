package ch.raeffu.access;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ch.raeffu.generated.ObjectFactory;
import ch.raeffu.generated.PersonType;
import ch.raeffu.generated.PersonsType;

public class MarshallingSample {
	
	private final static String PACKAGE = "ch.raeffu.generated";
	private final static String XML_FILE = "personsCreated.xml";

	public static void main(String[] l){
		
		// First we need a factory object to get basic access!
		ObjectFactory objectFactory = new ObjectFactory();
		
		// To get a PersonsType object do:
		PersonsType persons = objectFactory.createPersonsType();
		
		// To get a PersonType object do:
		PersonType person = objectFactory.createPersonType();
		
		// Now we set up a person object!
		person.setFirstName("Adam"); // thats all we can do for now
		
		// Add the person object to persons
		// we have to add the person to the list
		persons.getPerson().add(person);
		
		// repeat the operation several times
		person = objectFactory.createPersonType();
		person.setFirstName("Eva"); 
		persons.getPerson().add(person);
		
		// Now we want to marshal the content of persons to an XML file
		try {
			// We need a JAXBElement of our type (this represents our datas to be written)
			JAXBElement<PersonsType> dataContent = objectFactory.createPersons(persons);
			// We need a JAXBContex which knows our package (generated code)
			JAXBContext context = JAXBContext.newInstance(PACKAGE);
			//The output XML file
			File file = new File(XML_FILE);
			// Use the context to receive a marshaller object
			Marshaller m = context.createMarshaller();
			// set property if you want a pretty printed XML file
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(dataContent, file); // Create the file
			m.marshal(dataContent, System.out); // Print it on console too
		}
		catch (JAXBException jbe) {jbe.printStackTrace();}
	}
}
