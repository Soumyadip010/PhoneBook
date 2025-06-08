package UI;

import DAO.ContactService;
import model.Contact;


import java.awt.*;     // it also contains a List class for UI
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;  // For data storage

public class ContactApp extends Frame implements ActionListener{
    private List<Contact> contacts;   //This List is used for storage purpose
    private ContactService cService;
    private TextField searchField;
    private java.awt.List contactList; //This List is AWT UI COMPONENT
    private Panel buttonPanel,p1,p2;
    Button addC,editC,deleteC,showC,sButton;
    Label statusBar;

    public ContactApp() {
        super("PhoneBook");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
        //Frame description
        Dimension scr=Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int)scr.getWidth();
        int height=(int)scr.getHeight();
        setBounds((width/2)-(500/2),(height/2)-(600/2),500,600);
        setResizable(false);
        setLayout(new BorderLayout());  // Layout for the frame itself

        //ContactList -an object of the AWT list component.
        contactList =new java.awt.List();
        contactList.setBackground(Color.LIGHT_GRAY); // Soft background
        contactList.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size to 16
        updateContactList();
        add(contactList,BorderLayout.CENTER);

        //BUTTON PANEL
        buttonPanel=new Panel();
        buttonPanel.setBackground(Color.DARK_GRAY); // Dark background for contrast
        buttonPanel.setSize(500,100);
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel,BorderLayout.SOUTH);

        //STATUS BAR PANEL
        p1=new Panel();
        p1.setLayout(new GridLayout(1,3));
        p1.setBackground(Color.DARK_GRAY);  // Dark background for contrast
        p1.setForeground(Color.WHITE);
        p1.setFont(new Font("Arial", Font.PLAIN, 12));
        add(p1,BorderLayout.NORTH);
        statusBar = new Label("Contacts");
        statusBar.setForeground(Color.WHITE);
        p1.add(statusBar);

        p2=new Panel();
        p2.setLayout(new BorderLayout());
        p1.add(p2);
        sButton=new Button("Search");
        sButton.setForeground(Color.BLACK);
        p2.add(sButton,BorderLayout.EAST);

        searchField=new TextField("search by name/number");
        searchField.setForeground(new Color(110, 105, 105, 255).brighter());
        p1.add(searchField);

        addC=new Button("Add Contact");
        editC=new Button("Edit Contact");
        deleteC=new Button("Delete Contact");
        showC=new Button("Show Contact");

        buttonPanel.add(addC);
        buttonPanel.add(deleteC);
        buttonPanel.add(editC);
        buttonPanel.add(showC);

        sButton.setBackground(new Color(255, 253, 208));
        addC.setBackground(new Color(255, 253, 208));
        deleteC.setBackground(new Color(255, 253, 208));
        editC.setBackground(new Color(255, 253, 208));
        showC.setBackground(new Color(255, 253, 208));

        addC.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { addC.setBackground(new Color(193, 211, 18)); }
            public void mouseExited(MouseEvent e) { addC.setBackground(new Color(255, 253, 208)); }
        });

        deleteC.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { deleteC.setBackground(new Color(193, 211, 18)); }
            public void mouseExited(MouseEvent e) { deleteC.setBackground(new Color(255, 253, 208)); }
        });

        editC.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { editC.setBackground(new Color(193, 211, 18)); }
            public void mouseExited(MouseEvent e) { editC.setBackground(new Color(255, 253, 208)); }
        });

        showC.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { showC.setBackground(new Color(193, 211, 18)); }
            public void mouseExited(MouseEvent e) { showC.setBackground(new Color(255, 253, 208)); }
        });

        sButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { sButton.setBackground(new Color(193, 211, 18)); }
            public void mouseExited(MouseEvent e) { sButton.setBackground(new Color(255, 253, 208)); }
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(searchField.getText().equals("search by name/number")) {
                    searchField.setText("");
                }
                searchField.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(searchField.getText().isEmpty()) {
                    searchField.setForeground(new Color(110, 105, 105, 255).brighter());
                    searchField.setText("search by name/number");
                    searchField.transferFocus();
                }
            }
        });

        addC.addActionListener(this);
        editC.addActionListener(this);
        deleteC.addActionListener(this);
        showC.addActionListener(this);
        sButton.addActionListener(this);

        setVisible(true);
    }

    public void updateContactList(){
        contactList.removeAll();                // At first removes the previous so that frame always dispalys the latest List.
        cService=new ContactService();
        contacts=cService.getAllContacts();
        for (Contact contact: contacts){
            contactList.add(contact.getName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==addC){
            new AddContactFrame(this);
            statusBar.setText("Adding new contact");
        }
        if(ae.getSource()==deleteC){
            if(contactList.getSelectedIndex()!=-1){  // to ensure the selected item on the contactList to be deleted
                BigDecimal id=contacts.get(contactList.getSelectedIndex()).getId();
                new DeleteContactFrame(this,id);
                statusBar.setText("Deleting contact");
            }

        }
        if(ae.getSource()==editC){
            if(contactList.getSelectedIndex()!=-1){  // to ensure the selected item on the contactList to be edited
                BigDecimal id=contacts.get(contactList.getSelectedIndex()).getId();
                new EditContactFrame(this,id);
                statusBar.setText("Editing contact");
            }
        }
        if (ae.getSource()==showC){
            if(contactList.getSelectedIndex()!=-1) {  // to ensure the selected item on the contactList to be edited
                BigDecimal id = contacts.get(contactList.getSelectedIndex()).getId();
                new ShowContactFrame(this, id);
            }
        }

        String name=searchField.getText().toLowerCase();
        if (ae.getSource()==sButton && name.equals("search by name/number")){
            updateContactList();
        }
        else if(ae.getSource()==sButton){
            cService=new ContactService();
            contacts=cService.search(name);
            contactList.removeAll();
            if(contacts.isEmpty()){
                contactList.add("No Matching contacts Found");
            }
            for(Contact contact:contacts){
                contactList.add(contact.getName());
            }
        }

    }
}
