package pojos;
import java.io.File;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import pojos.generated.*;


public class HealthProfileJson {  	
	
	public static PeopleType people = new PeopleType();

	// here we are creating the factory value whose type is ObjectFactory
	public static void initializeDB() {
		pojos.generated.ObjectFactory factory = new pojos.generated.ObjectFactory();
		
		// now i am creating the first person which has PersonType by using factory
		// i am adding the informations manually for each attributes of the person
		PersonType person1 = factory.createPersonType();
		person1.setId(BigInteger.valueOf(1));
		person1.setFirstname("Martin");
		person1.setLastname("Agaranda");	
		person1.setBirthdate("1973-06-28T15:19:44.000+2:00");
		// health profile value is created by HealthprofileType
		HealthprofileType healthprofile1 = factory.createHealthprofileType();
		healthprofile1.setLastupdate("2014-05-08T21:16:51.000+2:00");
		healthprofile1.setWeight(89.5F);
		healthprofile1.setHeight(180.8F);
		healthprofile1.setBmi(22.63467632F);
		person1.setHealthprofile(healthprofile1);
		
		// same things with person1
		PersonType person2 = factory.createPersonType();
		person2.setId(BigInteger.valueOf(2));
		person2.setFirstname("Agatha");
		person2.setLastname("Brabara");	
		person2.setBirthdate("1976-06-25T15:30:14.000+2:00");
		HealthprofileType healthprofile2 = factory.createHealthprofileType();
		healthprofile2.setLastupdate("2015-09-09T01:51:53.000+2:00");
		healthprofile2.setWeight(34.5F);
		healthprofile2.setHeight(120.8F);
		healthprofile2.setBmi(22.72440348F);
		person2.setHealthprofile(healthprofile2);
		
		// same things with person1	
		PersonType person3 = factory.createPersonType();
		person3.setId(BigInteger.valueOf(3));
		person3.setFirstname("Goksel");
		person3.setLastname("TOlan");	
		person3.setBirthdate("1973-01-04T01:33:45.000+2:00");
		HealthprofileType healthprofile3 = factory.createHealthprofileType();
		healthprofile3.setLastupdate("2014-10-18T17:53:31.000+2:00");
		healthprofile3.setWeight(93.9F);
		healthprofile3.setHeight(190.8F);
		healthprofile3.setBmi(24.63536855F);
		person3.setHealthprofile(healthprofile3);
		
		
		// i am adding the person1-2-3 to the people_new.json file
		people.getPerson().add(person1);
		people.getPerson().add(person2);
		people.getPerson().add(person3);
		
	}	

	public static void main(String[] args) throws Exception {
		
		initializeDB();
		
		// Jackson Object Mapper 
		ObjectMapper mapper = new ObjectMapper();
		
		// Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        
		// configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("people.json"), people);
    }
}
