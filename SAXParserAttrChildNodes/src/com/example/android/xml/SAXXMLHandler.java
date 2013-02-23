package com.example.android.xml;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.example.android.beans.Address;
import com.example.android.beans.Employee;
 
public class SAXXMLHandler extends DefaultHandler {
 
    private List<Employee> employees;
    private String tempVal;
    // to maintain context
    private Employee employee;
    private Address address;
 
    public SAXXMLHandler() {
        employees = new ArrayList<Employee>();
    }
 
    public List<Employee> getEmployees() {
        return employees;
    }
 
    // Event Handlers
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // reset
        tempVal = "";
        if (qName.equalsIgnoreCase("employee")) {
            // create a new instance of employee
            employee = new Employee();
            employee.setId(Integer.parseInt(attributes.getValue("id")));
        } else if (qName.equalsIgnoreCase("address")) {
            // create a new instance of address
            address = new Address();
        }
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("employee")) {
            // add it to the list
            employees.add(employee);
        } else if (qName.equalsIgnoreCase("name")) {
            employee.setName(tempVal);
        } else if (qName.equalsIgnoreCase("department")) {
            employee.setDepartment(tempVal);
        } else if (qName.equalsIgnoreCase("type")) {
            employee.setType(tempVal);
        } else if (qName.equalsIgnoreCase("email")) {
            employee.setEmail(tempVal);
        } else if (qName.equalsIgnoreCase("line1")) {
            address.setLine(tempVal);
        } else if (qName.equalsIgnoreCase("city")) {
            address.setCity(tempVal);
        } else if (qName.equalsIgnoreCase("state")) {
            address.setState(tempVal);
        } else if (qName.equalsIgnoreCase("zipcode")) {
            address.setZipcode(Long.parseLong(tempVal));
        } else if (qName.equalsIgnoreCase("address")) {
            employee.setAddress(address);
        }
    }
}