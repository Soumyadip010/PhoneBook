package UI;

import DAO.ContactService;
import model.Contact;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;

public class AddContactFrame extends Frame implements ActionListener{
    private TextField nameField, numField, emailField, addrField;
    private ContactService cService;
    private Panel p1,p2;
    private ContactApp parent;
    private Button addButton, cancelButton;

    public AddContactFrame(ContactApp parent) {
        super("Add Contact");
        this.parent=parent;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                try {
                    parent.statusBar.setText("Contact Not Added...");
                    Thread.sleep(1000);
                    parent.statusBar.setText("Contacts");
                }
                catch (Exception e){
                    System.err.println(e);
                }
            }
        });
        cService = new ContactService();
        setLayout(new BorderLayout());
        p1=new Panel();
        p2=new Panel();

        add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.SOUTH);

        setSize(400, 200);
        p1.setLayout(new GridLayout(4, 2));
        p1.setBackground(Color.LIGHT_GRAY);

        nameField=new TextField();
        numField=new TextField();
        emailField =new TextField();
        addrField=new TextField();

        p1.setLayout(new GridLayout(4,2));
        p1.add(new Label("NAME: "));               //row 1 col 1
        p1.add(nameField);                              //row 1 col 2

        p1.add(new Label("PHONE: "));              //row 2 col 1
        p1.add(numField);                               //row 2 col 2

        p1.add(new Label("EMAIL: "));              //row 3 col 1
        p1.add(emailField);                             //row 3 col 2

        p1.add(new Label("ADDRESS: "));             //row 4 col 1
        p1.add(addrField);                               //row 4 col 2


        addButton=new Button("yes");
        cancelButton=new Button("no");
        addButton.setBackground(new Color(255, 253, 208));
        addButton.setForeground(Color.BLACK);
        cancelButton.setBackground(new Color(255, 253, 208));
        cancelButton.setForeground(Color.BLACK);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(new Color(193, 211, 18));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(new Color(255, 253, 208));
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(193, 211, 18));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(new Color(255, 253, 208));
            }
        });

        p2.setLayout(new FlowLayout());
        p2.add(new Label("Add Contact:"));
        p2.setBackground(Color.DARK_GRAY);
        p2.setForeground(Color.WHITE);
        p2.add(addButton);                      //Row 5 Column 1
        p2.add(cancelButton);                   //Row 5 Column 2

        addButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){

        if (ae.getSource()==addButton){
            String name = nameField.getText();
            String phone = numField.getText();
            String email = emailField.getText();
            String address = addrField.getText();

            if (!name.isEmpty() && !phone.isEmpty()) {
                try {
                    Contact contact = new Contact(name, new BigDecimal(phone), email, address);
                    if (cService.addContact(contact)) {
                        parent.updateContactList();
                        dispose();
                        parent.statusBar.setText("Contact Added...");
                        Thread.sleep(1000);
                        parent.statusBar.setText("Contacts");
                    }
                }
                catch (Exception e){
                    System.err.println(e);
                }
            }
        }

        if(ae.getSource()==cancelButton){
            dispose();
            try {
                parent.statusBar.setText("Contact Not Added...");
                Thread.sleep(1000);
                parent.statusBar.setText("Contacts");
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
}

