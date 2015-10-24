package pojos;

import pojos.generated.*;

import javax.xml.bind.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class JAXBMarshaller {
	public void generateXMLDocument(File xmlDocument) {
		try {

			// here we are creating the factory value whose type is ObjectFactory
			JAXBContext jaxbContext = JAXBContext.newInstance("pojos.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			pojos.generated.ObjectFactory factory = new pojos.generated.ObjectFactory();

			// people and person1 are created
			PeopleType people = factory.createPeopleType();
			PersonType person1 = factory.createPersonType();
			
			// i am adding the informations manually for each attributes of the person
			person1.setId(BigInteger.valueOf(1));
			person1.setFirstname("Martin");
			person1.setLastname("Agaranda");	
			person1.setBirthdate("1973-06-28T15:19:44.000+2:00");
			// health profile value is created by HealthprofileType
			HealthprofileType healthprofile1 = factory.createHealthprofileType();
			healthprofile1.setLastupdate("2014-05-08T21:16:51.000+2:00");
			healthprofile1.setWeight(89.5F);
			healthprofile1.setHeight(1.88F);
			healthprofile1.setBmi(22.63467632F);
			person1.setHealthprofile(healthprofile1);
			
			// same things with person1			
			PersonType person2 = factory.createPersonType();
			person2.setId(BigInteger.valueOf(2));
			person2.setFirstname("Agatha");
			person2.setLastname("Barbara");	
			person2.setBirthdate("1976-06-25T15:30:14.000+2:00");
			HealthprofileType healthprofile2 = factory.createHealthprofileType();
			healthprofile2.setLastupdate("2015-09-09T01:51:53.000+2:00");
			healthprofile2.setWeight(72.5F);
			healthprofile2.setHeight(1.78F);
			healthprofile2.setBmi(22.72440348F);
			person2.setHealthprofile(healthprofile2);
			
			// same things with person1
			PersonType person3 = factory.createPersonType();
			person3.setId(BigInteger.valueOf(3));
			person3.setFirstname("Goksel");
			person3.setLastname("Tolan");	
			person3.setBirthdate("1973-01-04T01:33:45.000+2:00");
			HealthprofileType healthprofile3 = factory.createHealthprofileType();
			healthprofile3.setLastupdate("2014-10-18T17:53:31.000+2:00");
			healthprofile3.setWeight(93.9F);
			healthprofile3.setHeight(1.96F);
			healthprofile3.setBmi(24.63536855F);
			person3.setHealthprofile(healthprofile3);
			
			
			// i am adding the person1-2-3 to the people.XML file
			people.getPerson().add(person1);
			people.getPerson().add(person2);
			people.getPerson().add(person3);
			
						
						
			JAXBElement<PeopleType> peopleElement = factory
					.createPeople(people);
			marshaller.marshal(peopleElement,
					new FileOutputStream(xmlDocument));

		} catch (IOException e) {
			System.out.println(e.toString());

		} catch (JAXBException e) {
			System.out.println(e.toString());

		}

	}

	// creates People.xml file
	public static void main(String[] argv) {
		String xmlDocument = "people.xml";
		JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();
		jaxbMarshaller.generateXMLDocument(new File(xmlDocument));
	}
}
