
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class Registration extends JDialog{
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfCPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel RegistrerPanel;

    //Setup of the registration form
    public Registration(ActionListener parent){
        super((Frame) parent, "Registrer", true);
        setContentPane(RegistrerPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo((Component) parent);
        setResizable(false);

//Action listener for the register button. When pressed it will check if the username exists and if the passwords match
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
//Action listener for the cancel button. When pressed it will close the registration form
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    //Method for registering a new user
    private void registerUser() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        String cPassword = pfCPassword.getText();

        if (username.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(cPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "User successfully registered", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "User already exists", "Try again", JOptionPane.ERROR_MESSAGE);
        }
        }

    public User user;
    //Method for adding a new user to the database
    private User addUserToDatabase(String username, String password) {
        User user = null; //new User(username, password);

        final String DB_URL = "jdbc:mysql://hc.hyperservers.dk:3306/s1_simon_login";
        final String DB_USER = "u1_7kbmeQqbFN";
        final String DB_PASS = "ICYsmGG.gR=k@z627Tw!CxzX";

        try{
            //connecting to database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            //
            Statement stmt = conn.createStatement();
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO users (username, password) " +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            //Inserting data into database
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.username = username;
                user.password = password;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;

    }

    public static void main(String[] args) {
        Registration nyForm = new Registration(null);
        User user = nyForm.user;
        if (user != null) {
            System.out.println("User registered: " + user.username);
        }
        else {
            System.out.println("User not registered");
        }
    }
}


