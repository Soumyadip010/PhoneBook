package UI;

import DAO.ContactService;
import model.Contact;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;

public class EditContactFrame extends Frame implements ActionListener {
    private TextField nameField,numField,emailField,addrField;
    private ContactApp parent;
    private Panel p1,p2;
    private Button yes,no;
    private ContactService cService;
    Contact contact;

    public EditContactFrame(ContactApp parent, BigDecimal id){
        super("Edit Contact");
        this.parent=parent;
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                dispose();
                try {
                    parent.statusBar.setText("Contact Not Edited...");
                    Thread.sleep(1000);
                    parent.statusBar.setText("Contacts");
                }
                catch (Exception e){
                    System.err.println(e);
                }
            }
        });

        //Frame Description
        setSize(400,200);
        setLayout(new BorderLayout());

        cService=new ContactService();
        p1=new Panel();
        p2=new Panel();
        add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.SOUTH);
        p1.setBackground(Color.LIGHT_GRAY);
        p2.setBackground(Color.DARK_GRAY);
        p2.setForeground(Color.WHITE);

        contact=cService.getContactById(id);
        contact.setId(id);
        nameField=new TextField(contact.getName());
        numField=new TextField(contact.getNumber().toString());
        emailField =new TextField(contact.getEmail());
        addrField=new TextField(contact.getAddress());

        p1.setLayout(new GridLayout(4,2));
        p1.add(new Label("NAME: "));               //row 1 col 1
        p1.add(nameField);                              //row 1 col 2

        p1.add(new Label("PHONE: "));              //row 2 col 1
        p1.add(numField);                               //row 2 col 2

        p1.add(new Label("EMAIL: "));              //row 3 col 1
        p1.add(emailField);                             //row 3 col 2

        p1.add(new Label("ADDRESS: "));             //row 4 col 1
        p1.add(addrField);                               //row 4 col 2

        yes=new Button("yes");
        no=new Button("No");
        p2.setLayout(new FlowLayout());

        yes.setBackground(new Color(255, 253, 208));
        yes.setForeground(Color.BLACK);
        no.setBackground(new Color(255, 253, 208));
        no.setForeground(Color.BLACK);

        yes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                yes.setBackground(new Color(193, 211, 18));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                yes.setBackground(new Color(255, 253, 208));
            }
        });
        no.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                no.setBackground(new Color(193, 211, 18));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                no.setBackground(new Color(255, 253, 208));
            }
        });

        p2.add(new Label("Edit Contact: "));
        p2.add(yes);
        p2.add(no);

        yes.addActionListener(this);
        no.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==no){
            dispose();
            try {
                parent.statusBar.setText("Contact Not Edited...");
                Thread.sleep(1000);
                parent.statusBar.setText("Contacts");
            }
            catch (Exception e){
                System.err.println(e);
            }
        }

        if(ae.getSource()==yes){
            try {
                contact.setName(nameField.getText());
                contact.setNumber(new BigDecimal(numField.getText()));
                contact.setAddress(addrField.getText());
                contact.setEmail(emailField.getText());
                if(cService.updateContact(contact)){
                    parent.updateContactList();
                    dispose();
                    parent.statusBar.setText("Contact Edited...");
                    Thread.sleep(1000);
                    parent.statusBar.setText("Contacts");
                }
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }

    }
}
