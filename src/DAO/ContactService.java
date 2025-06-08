package DAO;

import model.Contact;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;


public class ContactService {
    Connection con;
    PreparedStatement stmt;

    //adding new contact.
    public boolean addContact(Contact contact){
        try
        {
            con= DBConnection.getConnect();
            String query="INSERT INTO PHONEBOOK(NAME, PHONE, EMAIL, ADDRESS) VALUES(?, ?, ?, ?)";
            stmt=con.prepareStatement(query);

            stmt.setString(1,contact.getName());
            stmt.setBigDecimal(2,contact.getNumber());
            stmt.setString(3,contact.getEmail());
            stmt.setString(4,contact.getAddress());
            int iRows=stmt.executeUpdate();

            con.close();
            return iRows>0;

        }
        catch (Exception e)
        {
            System.err.println(e);
            return false;
        }
    }

    //deleting existing contact
    public boolean deleteContact(Contact contact){
        try
        {
            con= DBConnection.getConnect();
            String query="DELETE FROM PHONEBOOK WHERE ID=?";
            stmt=con.prepareStatement(query);

            stmt.setBigDecimal(1,contact.getId());
            int dRows=stmt.executeUpdate();

            con.close();
            return dRows>0;
        }
        catch (Exception e)
        {
            System.err.println(e);
            return false;
        }
    }

    //Updating existing contact
    public boolean updateContact(Contact contact){
        try{
            con= DBConnection.getConnect();
            String query="UPDATE PHONEBOOK SET NAME=?, PHONE=?, ADDRESS=?, EMAIL=? WHERE ID=?";
            stmt=con.prepareStatement(query);

            stmt.setString(1,contact.getName());
            stmt.setBigDecimal(2,contact.getNumber());
            stmt.setString(3,contact.getAddress());
            stmt.setString(4,contact.getEmail());
            stmt.setBigDecimal(5,contact.getId());

            int uRows= stmt.executeUpdate();
            con.close();
            return uRows>0;
        }
        catch (Exception e){
            System.err.println(e);
            return false;
        }
    }

    //if search name is big decimal//
    private boolean isBigDecimal(String name){
        try{
            new BigDecimal(name);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    //SEARCH BY NAME/NUMBER
    public List<Contact> search(String name){
        List<Contact> contacts=new ArrayList<>();
        String query;
        if(!isBigDecimal(name)){
            query = "SELECT * FROM PHONEBOOK WHERE LOWER(NAME) LIKE ? ORDER BY LOWER(NAME)";
        }
        else{
            query = "SELECT * FROM PHONEBOOK WHERE PHONE LIKE ? ORDER BY LOWER(NAME)";
        }
        try {
            con=DBConnection.getConnect();
            stmt=con.prepareStatement(query);
            stmt.setString(1,name+"%");
            ResultSet rs=stmt.executeQuery();
            while(rs.next()) {
                Contact ob=new Contact(
                        rs.getString("NAME"),
                        rs.getBigDecimal("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getString("ADDRESS")
                );
                ob.setId(rs.getBigDecimal("ID"));
                contacts.add(ob);
            }
            con.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
        return contacts;
    }

    //Retrieving contact by ID
    public Contact getContactById(BigDecimal id) {
        String query = "SELECT * FROM PHONEBOOK WHERE id = ?";
        try {
            con = DBConnection.getConnect();
            stmt = con.prepareStatement(query);

            stmt.setBigDecimal(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contact(
                        rs.getString("NAME"),
                        rs.getBigDecimal("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getString("ADDRESS"));   // returning object of Contact class
            }
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no contact found
    }

    // Retrieving all contacts
    public List<Contact> getAllContacts(){
        List<Contact> contacts=new ArrayList<>();
        try{
            String query="SELECT * FROM PHONEBOOK ORDER BY LOWER(NAME)";
            con=DBConnection.getConnect();
            stmt=con.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();

            while(rs.next()){
                Contact ob=new Contact(
                        rs.getString("NAME"),
                        rs.getBigDecimal("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getString("ADDRESS")
                );
                ob.setId(rs.getBigDecimal("ID"));
                contacts.add(ob);
            }
            con.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
        return contacts;
    }

}
