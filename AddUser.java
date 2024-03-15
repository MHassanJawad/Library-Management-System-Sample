import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class AddUser implements ActionListener {

    JFrame userFrame = new JFrame();
    JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
    JPanel userPanel = new JPanel(new BorderLayout());
    JTextField userName = new JTextField();
    JTextField userID = new JTextField();
    JTextField contactNum = new JTextField();
    JButton submit = new JButton();

    JLabel usernameLabel = new JLabel("Username:");
    JLabel userIDLabel = new JLabel("Qalam ID:");
    JLabel contactNumLabel = new JLabel("Contact Number:");

    AddUser() {
        submit.addActionListener(this);

        usernameLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        userIDLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        contactNumLabel.setFont(new Font("Consolas", Font.BOLD, 16));

        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(1080, 720);
        userFrame.getContentPane().setBackground(Color.orange);
        userFrame.setTitle("Add User");

        setDefaultText(userName, "Enter Username");
        setDefaultText(userID, "e.g: 1234567891234");
        setDefaultText(contactNum, "e.g: 03373428889");

        userName.setPreferredSize(new Dimension(400, 30));
        userName.setFont(new Font("Consolas", Font.PLAIN, 16));
        userName.setForeground(new Color(0x808080)); // Set default text color to gray
        userName.setBackground(Color.lightGray);
        userName.setCaretColor(Color.black);

        userID.setPreferredSize(new Dimension(400, 30));
        userID.setFont(new Font("Consolas", Font.PLAIN, 16));
        userID.setForeground(new Color(0x808080));
        userID.setBackground(Color.lightGray);
        userID.setCaretColor(Color.black);

        contactNum.setPreferredSize(new Dimension(400, 30));
        contactNum.setFont(new Font("Consolas", Font.PLAIN, 16));
        contactNum.setForeground(new Color(0x808080));
        contactNum.setBackground(Color.lightGray);
        contactNum.setCaretColor(Color.black);

        userPanel.setPreferredSize(new Dimension(400, 220));

        JPanel textFieldsPanel = new JPanel(new GridLayout(4, 2, -100, 20));
        textFieldsPanel.add(usernameLabel);
        textFieldsPanel.add(userName);
        textFieldsPanel.add(userIDLabel);
        textFieldsPanel.add(userID);
        textFieldsPanel.add(contactNumLabel);
        textFieldsPanel.add(contactNum);
        textFieldsPanel.add(new JLabel());

        submit.setText("Submit");
        submit.setFont(new Font("Consolas", Font.PLAIN, 18));
        submit.setFocusable(false);

        userPanel.add(textFieldsPanel, BorderLayout.NORTH);
        userPanel.add(submit, BorderLayout.SOUTH);

        mainPanel.add(userPanel);

        // Read existing data from userInfo.txt into userList array
        String fileName = "userInfo.txt";
        ArrayList<User> userList = readUserDataFromFile(fileName);

        userFrame.add(mainPanel, BorderLayout.CENTER);
        userFrame.setVisible(true);
        userFrame.pack();
    }

    private ArrayList<User> readUserDataFromFile(String fileName) {
        ArrayList<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String name = line.trim();
                String id = reader.readLine();
                String contactNum = reader.readLine();

                // Check for null values
                if (id == null || contactNum == null) {
                    break; // Exit the loop if either ID or contact number is null
                }

                userList.add(new User(name, id.trim(), contactNum.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private void setDefaultText(JTextField textField, String defaultText) {
        textField.setText(defaultText);
        textField.setForeground(new Color(0x808080)); // Set default text color to gray
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(defaultText)) {
                    textField.setText("");
                    textField.setForeground(new Color(0x000000));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                    textField.setForeground(new Color(0x808080));
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String username = userName.getText();
            String cnic = userID.getText();
            String contactnum = contactNum.getText();

            // Example: Check if the username is valid
            if (!checkUserName(username)) {
                displayErrorMessage("Invalid Username");
                return; // Do not proceed further
            }

            if (!checkID(cnic)) { //need to check if cnic already exists or not
                displayErrorMessage("Invalid ID");
                return;
            }

            if (!checkContactNum(contactnum)) {
                displayErrorMessage("Invalid Contact Number");
                return;
            }

            // Add new data to userList
            ArrayList<User> userList = new ArrayList<User>();
            userList.addAll(readUserDataFromFile("userInfo.txt")); // Add existing data
            userList.add(new User(username, cnic, contactnum)); // Add new data

            // Write the entire userList to the file (overwrite)
            writeUserDataToFile("userInfo.txt", userList);

            displaySuccessMessage("Data Entered Successfully");
        }
    }

    private void writeUserDataToFile(String fileName, ArrayList<User> userList) {
        try (FileWriter fileWriter = new FileWriter(fileName, false); // Overwrite the file
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (User user : userList) {
                bufferedWriter.write(user.getName());
                bufferedWriter.newLine();
                bufferedWriter.write(user.getID());
                bufferedWriter.newLine();
                bufferedWriter.write(user.getContactNum());
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserName(String name) {
        String trimmedName = name.replaceAll("\\s", "");    //remove all spaces from the text
        return trimmedName.matches("[a-zA-Z]+");        //check if text contains only alphabets
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(userFrame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccessMessage(String successMessage) {
        JOptionPane.showMessageDialog(userFrame, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean checkID(String id) {
        if (id.length() != 6) {
            return false;
        }
        return id.matches("\\d+") && !checkIfIDExists(id);
    }

    public boolean checkContactNum(String num) {
        if (num.length() != 11) {
            return false;
        }
        return num.matches("\\d+");
    }

    private boolean checkIfIDExists(String idToCheck) {
        ArrayList<User> userList = readUserDataFromFile("userInfo.txt");
        for (User user : userList) {
            if (user.getID().equals(idToCheck)) {
                displayErrorMessage("ID already exists");
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        AddUser addUser = new AddUser();
    }
}
