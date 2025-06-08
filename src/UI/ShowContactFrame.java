package UI;

import DAO.ContactService;
import model.Contact;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

public class ShowContactFrame extends Frame{
    private ContactApp parent;
    private ContactService cService;
    Contact contact;

    public ShowContactFrame(ContactApp parent, BigDecimal id){
        super("Contact Details");
        this.parent=parent;
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                dispose();
            }
        });
        //Frame Description
        setSize(400,200);
        setLayout(new GridLayout(4,2));
        setBackground(Color.DARK_GRAY.brighter());
        setForeground(Color.WHITE);
        setFont(new Font("Arial" ,Font.BOLD,14));

        cService=new ContactService();

        contact=cService.getContactById(id);

        add(new Label("NAME: "));
        Label name=new Label(contact.getName());
        name.setBackground(Color.WHITE);
        name.setForeground(Color.BLACK);
        add(name);

        add(new Label("PHONE: "));
        Label phone=new Label(contact.getNumber().toString());
        phone.setBackground(Color.WHITE);
        phone.setForeground(Color.BLACK);
        add(phone);

        add(new Label("EMAIL: "));
        Label email=new Label(contact.getEmail());
        email.setBackground(Color.WHITE);
        email.setForeground(Color.BLACK);
        add(email);

        add(new Label("ADDRESS"));
        Label addr=new Label(contact.getAddress());
        addr.setBackground(Color.WHITE);
        addr.setForeground(Color.BLACK);
        add(addr);

        setVisible(true);
    }
}
