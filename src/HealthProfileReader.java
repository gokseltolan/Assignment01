import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

import javax.management.AttributeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pojos.HealthProfile;
import pojos.Person;

public class HealthProfileReader {
	Document doc;
    XPath xpath;

	public void loadXML() throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        doc = builder.parse("people_new.xml");

        //creating xpath object
        getXPathObj();
    }
    public XPath getXPathObj() {

        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }
    
    //3.1
    // getting the ID of the person and giving the weight of that person
    public double getWeightById(Long id) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile/weight/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return Double.parseDouble(node.getNodeValue());
    }
    
    //3.1
    // getting the ID of the person and giving the height of that person
    public double getHeightById(Long id) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile/height/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        return Double.parseDouble(node.getNodeValue());
    }
    
    //3.2
    // here it takes all persons and save them inside the personlist 
    // then i will use this list for 2 reason
    public NodeList getAllPeople() throws XPathExpressionException
    {
    	XPathExpression expr = xpath.compile("/people/person");
        NodeList personsList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return personsList;
    }
    
    //3.2 , 3.4
    //here the method takes people list as parameter and returns the 
    // details of that person until reaching at the end of the list. 
    // health profile details is already listing with printHPById method
    // so it was logical to use it here
    public void printPeopleDetails(NodeList peopleList) throws XPathExpressionException
    {
    	NodeList personsList = peopleList;
        System.out.println("People are as follows: \n");
        for(int i=0 ; i < personsList.getLength() ; i++)
        {	
    		System.out.println("********");
        	NodeList personDetails = personsList.item(i).getChildNodes();
         	//NamedNodeMap attributes =  personsList.item(i).getAttributes();
        	//Node id = attributes.getNamedItem("id");
        	//id.getNodeValue(); //id number in String
        	Long personId = Long.parseLong(personsList.item(i).getAttributes()
        			.getNamedItem("id").getNodeValue());
        	System.out.println("ID : " + personId);
        	
        	for(int j = 0 ; j < personDetails.getLength() ; j++)
            {
            	Node child = personDetails.item(j);
            	if (child.getNodeType() != Node.TEXT_NODE) 
                { 
                	if (child.getNodeName() == "firstname")
                    	System.out.println("First Name: " + child.getFirstChild().getNodeValue());
                    if (child.getNodeName() == "lastname")
                    	System.out.println("Last Name: " +child.getFirstChild().getNodeValue());
                    if (child.getNodeName() == "birthdate")
                    	System.out.println("Birthdate: " +child.getFirstChild().getNodeValue());
                    if (child.getNodeName() == "healthprofile")
                    {
                    	printHPById(personId);
                    }
                }
            }
        }
        System.out.println("---------------------------- ");
        System.out.println("Total Number of People: " + personsList.getLength() +"\n");
    }
    
    // 3.3
    // this method takes id as parameter and returns the 
    // health profile of that specific person
    public void printHPById(Long id) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        NodeList healthProfileDetails = node.getChildNodes();

        for(int i = 0 ; i < healthProfileDetails.getLength() ; i++)
        {
        	Node child = healthProfileDetails.item(i);
        	if (child.getNodeType() != Node.TEXT_NODE) 
            { 
            	if (child.getNodeName() == "lastupdate")
                	System.out.println("Last Update: " + child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "weight")
                	System.out.println("Weight: " +child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "height")
                	System.out.println("Height: " +child.getFirstChild().getNodeValue());
                if (child.getNodeName() == "bmi")
                	System.out.println("BMI: " +child.getFirstChild().getNodeValue());
            }
        }
    }
    
    //3.4
    // this method is taking the weight and condition and as a result it returns the list of person who fits this condition.  
    public NodeList getPersonsByWeight(String weight, String condition) throws XPathExpressionException
    {
       XPathExpression expr = xpath.compile("/people/person/healthprofile[weight " + condition + "'" + weight + "']/parent::*");
       NodeList personsList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);   
       return personsList;
    }
 
    
	/**
	 * The health profile reader gets information from the command line about
	 * weight and height and calculates the BMI of the person based on this
	 * parameters
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {
		HealthProfileReader test = new HealthProfileReader();
        test.loadXML();
        //getting node by book name
        //Person person = test.getPersonByName("Mario", "Balotelli");
        
        //3.1
        // examples of get weight and height for id = 2
        System.out.println("weight"+test.getWeightById(Long.parseLong("2")));
        System.out.println("height"+test.getHeightById(Long.parseLong("2")));
        
        //3.2
        // printing all the people inside the people_new.xml file with details
        test.printPeopleDetails(test.getAllPeople());
        
        //3.3
        // taking the id = 5 as parameter and giving the health profile 
        // information as a return
        String personid = "5";
        System.out.println("Person ID: " + personid);
        test.printHPById(Long.parseLong(personid));
        
        //3.4
        // here it lists the person who fits the condition of 
        // whose weight is >89
        System.out.println("\nPeople having weights > 90");
        test.printPeopleDetails(test.getPersonsByWeight("90", ">"));
	}

}