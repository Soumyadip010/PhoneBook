package UI;

import DAO.ContactService;
import model.Contact;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;

public class DeleteContactFrame extends Frame implements ActionListener {
    private ContactApp parent;
    private Panel p1,p2;
    private Button yes,no;
    private Label name,name1,phone,phone1,email,email1,addr,addr1;
    private ContactService cService;
    Contact contact;

    public DeleteContactFrame(ContactApp parent, BigDecimal id){
        super("Delete Contact");
        this.parent=parent;
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                dispose();
                try {
                    parent.statusBar.setText("Contact Not Deleted...");
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

        contact=cService.getContactById(id);
        contact.setId(id);
        p1.setLayout(new GridLayout(4,2));
        p1.setBackground(Color.LIGHT_GRAY);

        p1.add(new Label("NAME: "));                        //row 1 col 1
        p1.add(new Label(contact.getName()));                    //row 1 col 2
        p1.add(new Label("PHONE: "));                       //row 2 col 1
        p1.add(new Label(contact.getNumber().toString()));       //row 2 col 2
        p1.add(new Label("EMAIL: "));                       //row 3 col 1
        p1.add(new Label(contact.getEmail()));                   //row 3 col 2
        p1.add(new Label("ADDRESS"));                       //row 4 col 1
        p1.add(new Label(contact.getAddress()));                 //row 4 col 2

        yes=new Button("yes");
        no=new Button("No");
        p2.setLayout(new FlowLayout());
        p2.setBackground(Color.DARK_GRAY);
        p2.setForeground(Color.WHITE);
        p2.add(new Label("Delete Contact: "));
        p2.add(yes);
        p2.add(no);

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

        yes.addActionListener(this);
        no.addActionListener(this);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==no){
            dispose();
            try {
                parent.statusBar.setText("Contact Not Deleted...");
                Thread.sleep(1000);
                parent.statusBar.setText("Contacts");
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
        if(ae.getSource()==yes){
            if(cService.deleteContact(contact)){
                parent.updateContactList();
                dispose();
                try {
                    parent.statusBar.setText("Contact Deleted...");
                    Thread.sleep(1000);
                    parent.statusBar.setText("Contacts");
                }
                catch (Exception e){
                    System.err.println(e);
                }
            }
        }
    }
}
