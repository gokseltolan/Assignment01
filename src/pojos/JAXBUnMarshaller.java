package pojos;

import pojos.generated.*;
import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;
public class JAXBUnMarshaller {
	public void unMarshall(File xmlDocument) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("pojos.generated");

			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(
					"people.xsd"));
			unMarshaller.setSchema(schema);
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller
					.unmarshal(xmlDocument);

			// we are creating the people value from PeopleType
			PeopleType people = peopleElement.getValue();

			// here all the people inside the people.xml file getting listed
			// inside personList, then with the for loop 
			// we can take all of them one by one and print out their informations.
			List<PersonType> personList = people.getPerson();
			for (int i = 0; i < personList.size(); i++) {

				PersonType person = (PersonType) personList.get(i);
				
				System.out.println("ID: "+ person.getId().toString());
				System.out.println("Name: "+ person.getFirstname());
				System.out.println("Surname: "+ person.getLastname());
			    System.out.println("Birthday: "+ person.getBirthdate());
				
				HealthprofileType Healthprofile = person.getHealthprofile();
				
				System.out.println("Lastupdate: " + Healthprofile.getLastupdate());	
				System.out.println("Weight: " + Healthprofile.getWeight());
				System.out.println("Height: " + Healthprofile.getHeight());
				System.out.println("Bmi: " + Healthprofile.getBmi());
				System.out.println("------------------------------------");
			
			}
		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] argv) {
		File xmlDocument = new File("people.xml");
		JAXBUnMarshaller jaxbUnmarshaller = new JAXBUnMarshaller();

		jaxbUnmarshaller.unMarshall(xmlDocument);

	}

	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}

	}
}
