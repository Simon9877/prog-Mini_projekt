import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoggedInForm extends JFrame{
    private JPanel LoggedinPanel;
    private JLabel Username;

//Setup of the logged in form
    public LoggedInForm() {
        super("Logged in");
        setContentPane(LoggedinPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

//checks if the database has any users registered.
        boolean hasRegisteredUsers = connectToDatabase();
        if (hasRegisteredUsers) {
            //Show login
            LoginForm loginForm = new LoginForm(this);
            User user = loginForm.user;

            if (user != null) {
                Username.setText(user.username);
                setLocationRelativeTo(null);
                setVisible(true);
            }
            else {
                dispose();
            }
        }
    }
//Connects to the database and checks if there are any users registered in the database and returns true or false
    private boolean connectToDatabase() {
        boolean hasRegistredUsers = false;


        final String DB_URL = "jdbc:mysql://hc.hyperservers.dk:3306/s1_simon_login";
        final String DB_USER = "u1_7kbmeQqbFN";
        final String DB_PASS = "ICYsmGG.gR=k@z627Tw!CxzX";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255) UNIQUE, password VARCHAR(255) NOT NULL)");

            //Check if there are any users in the database
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasRegistredUsers;
    }

    public static void main(String[] args) {
        LoggedInForm nyForm = new LoggedInForm();
    }

}
