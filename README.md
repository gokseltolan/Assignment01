# Assignment01
In Assignment01 project i have created all the necessary files. First of all in scr/ i have HealthProfileReader which executes fallowing functions;
1-Use xpath to implement methods like getWeight and getHeight (
getWeight(personID) returns weight of a given person, the same for getHeight
)
2-Make a function that prints all people in the list with detail
3-A function that accepts id as parameter and prints the HealthProfile of the person with that id
4-A function which accepts a weight and an operator (=, > , <) as parameters and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.).

HealthProfileReader Java executes all of these in an order and gives the result to the screen. HealthProfileReader Java file takes information from people_new.xml which has 150 person in itself.

Then i have created build.xml which we need to execute first to have generated javas( Healthprofile, Objectfactory, 	PeopleType, PersonType). This built.xml needs .xsd file which names people.xsd. I have also created this file according to people_new.xml file. You should execute build.xml with ANT. After generating the necessary files u can execute JAXBMarshaller.java file with ANT. It will create people.xml file with 3 new people. But the structure will stay same. After executing JAXBMarshaller.java you can execute JAXBUnMarshaller.java with ANT again. In this java file, it reads information of 3 people from people.xml and write it down to the screen. As a last, i have created HealthProfileJson.java which creates people.json file 	which as the information of 3 people in itself. But for HealthProfileJson.java file you should execute it in a normal way not with ANT. 

And to be able to execute them automatically i need to add one method to build.xml which is;

<target name="execute.evaluation" depends="compile, execute.HealthProfileReader, execute.JAXBMarshaller, execute.JAXBUnMarshaller, execute.HealthProfileJson">
			<echo message="Executing evaluation" />
		</target>

Important Notes1: In my Exclipse i always have error at the first time when i am trying to execute HealthProfileReader, Build, Marshaller, Unmarshaller and Json files. But when i try it as a second time it does not give error. My codes are working correct but i could not understand why it does not work in the first time. 

Important Notes2: I had problems with the library of HealthProfileJson.java, i need to add these lib files manually
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

then it works. 

Important Notes3: I have checked it a lot, even with my friends but i could not understand that my execute.evaluation method does not work in build.xml. Actually it works successfully but I cannot see the results on my screen in Eclipse. But when u execute them manually you can see the results clearly. 

