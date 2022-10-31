import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JButton btnRegistration;
    private JPanel LoginForm;

//Setup of the login form
    public LoginForm(JFrame parent) {
        super(parent, "Login", true);
        setContentPane(LoginForm);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);

//Action listener for the login button. When pressed it will check if the username and password is correct
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = loginOK(username, password);

                if (user != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Invalid username or password",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                }
            }
        });

//Action listener for the cancel button. When pressed it will close the login form
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        //Action listener for the registration button. When pressed it will open the registration form
        setVisible(true);
        btnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO Lav en metode der åbner registreringsformen

            }
        });
    }


//Method for checking if the username and password is correct
    public User user;
    private User loginOK(String username, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://hc.hyperservers.dk:3306/s1_simon_login";
        final String DB_USER = "u1_7kbmeQqbFN";
        final String DB_PASS = "ICYsmGG.gR=k@z627Tw!CxzX";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.username = rs.getString("username");
                user.password = rs.getString("password");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if (user != null) {
            System.out.println("User logged in: " + user.username);
        } else {
            System.out.println("User cancelled login");
        }
    }
}
