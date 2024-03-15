import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class GUI implements ActionListener{

    JFrame frame = new JFrame();


    JButton buttonAddUser = new JButton();
    JButton buttonSearchBook = new JButton();
    JButton buttonReturnBook = new JButton();
    JButton buttonAddBook = new JButton();
    JButton buttonBorrow = new JButton();
    JButton buttonCheckBorrowedBooks = new JButton();

    GUI(){
        ImageIcon originalImageIcon = new ImageIcon("library.png");
        Image scaledImage = originalImageIcon.getImage().getScaledInstance(
                1080, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        ImageIcon background = new ImageIcon("image.png");
        Image scaledBG = background.getImage().getScaledInstance(1080, 720, Image.SCALE_SMOOTH);
        ImageIcon scaledBackground = new ImageIcon(scaledBG);

        JLabel label2 = new JLabel();
        label2.setIcon(scaledBackground);

        JLabel label = new JLabel();
        label.setIcon(scaledImageIcon);

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1080, 720);
        frame.setTitle("Library Management System");

        JPanel panel2 = new JPanel();   //this panel will hold the main buttons;
        panel2.setPreferredSize(new java.awt.Dimension(980, 400));
        panel2.setBackground(Color.white);

        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new java.awt.Dimension(1080, 200));
        panel1.setBackground(Color.white);

        
        buttonAddUser.addActionListener(this);
        //buttonAddUser.addActionListener(e -> System.out.println("Hello!"));
        buttonAddUser.setBounds(10,10,170,180);
        buttonAddUser.setText("Add User");
        buttonAddUser.setFont(new Font("Comic Sans",Font.BOLD, 20));
        buttonAddUser.setFocusable(false);
        buttonAddUser.setBackground(Color.lightGray);
        buttonAddUser.setHorizontalAlignment(JButton.CENTER);
        //buttonAddUser.setVerticalAlignment(JButton.BOTTOM);

        
        buttonSearchBook.addActionListener(this);
        buttonSearchBook.setBounds(205, 10, 170, 180);
        buttonSearchBook.setText("Search Book");
        buttonSearchBook.setFont(new Font("Comic Sans",Font.BOLD, 20));
        buttonSearchBook.setFocusable(false);
        buttonSearchBook.setBackground(Color.lightGray);
        buttonSearchBook.setHorizontalAlignment(JButton.CENTER);
        //buttonSearchBook.setVerticalAlignment(JButton.BOTTOM);

        buttonReturnBook.addActionListener(this);        
        buttonReturnBook.setBounds(400, 10, 170, 180);
        buttonReturnBook.setText("Return Book");
        buttonReturnBook.setFont(new Font("Comic Sans",Font.BOLD, 20));
        buttonReturnBook.setFocusable(false);
        buttonReturnBook.setBackground(Color.lightGray);
        buttonReturnBook.setHorizontalAlignment(JButton.CENTER);
        //buttonReturnBook.setVerticalAlignment(JButton.BOTTOM);

        
        buttonAddBook.addActionListener(this);
        buttonAddBook.setBounds(400, 200, 170, 180);
        buttonAddBook.setText("Add Book");
        buttonAddBook.setFont(new Font("Comic Sans",Font.BOLD, 20));
        buttonAddBook.setFocusable(false);
        buttonAddBook.setBackground(Color.lightGray);
        buttonAddBook.setHorizontalAlignment(JButton.CENTER);
        //buttonAddBook.setVerticalAlignment(JButton.BOTTOM);

        buttonBorrow.addActionListener(this);
        buttonBorrow.setBounds(10, 200, 170, 180);
        buttonBorrow.setText("Issue Book");
        buttonBorrow.setFont(new Font("Comic Sans",Font.BOLD, 20));
        buttonBorrow.setFocusable(false);
        buttonBorrow.setBackground(Color.lightGray);
        buttonBorrow.setHorizontalAlignment(JButton.CENTER);
        //buttonBorrow.setVerticalAlignment(JButton.BOTTOM);

    
        buttonCheckBorrowedBooks.addActionListener(this);
        buttonCheckBorrowedBooks.setBounds(205, 200, 170, 180);
        buttonCheckBorrowedBooks.setText("Issued Books");
        buttonCheckBorrowedBooks.setFont(new Font("Comic Sans",Font.BOLD, 20));
        buttonCheckBorrowedBooks.setFocusable(false);
        buttonCheckBorrowedBooks.setBackground(Color.lightGray);
        buttonCheckBorrowedBooks.setHorizontalAlignment(JButton.CENTER);
        //buttonCheckBorrowedBooks.setVerticalAlignment(JButton.BOTTOM);
        



        panel2.setLayout(null);
        panel2.add(buttonAddUser);
        panel2.add(buttonSearchBook);
        panel2.add(buttonReturnBook);
        panel2.add(buttonAddBook);
        panel2.add(buttonBorrow);
        panel2.add(buttonCheckBorrowedBooks);
        // Add label to the center of panel1
        panel1.add(label);
        

        frame.setLayout(null); // Use null layout for explicit positioning

        // Set bounds for panel1, panel2, and label2
        panel1.setBounds(0, 0, 1080, 200);
        panel2.setBounds(250, 250, 580, 400);
        panel2.setBackground(Color.DARK_GRAY);

        label2.setBounds(0, 0, 1080, 720); //background image

        // Add panels and label2 directly to the content pane
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(label2);



        frame.setVisible(true);

    }


    public static void main(String[] args) {        //MAIN FUNCTION
        GUI gui = new GUI();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttonAddUser){ 
            //frame.dispose();
            AddUser userWindow = new AddUser();
            
        }else if(e.getSource() == buttonAddBook){
            frame.dispose();
            AddBookGUI addBook = new AddBookGUI();
        }
    }

    
}
