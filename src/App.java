import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class App {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/Hangman";
        String username = "root";
        String password="root@1234";
        try(Connection connection=DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to database");
            System.out.println(connection);
        }catch(SQLException e){
            System.err.println("Connection failed");
        }
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Hangman().setVisible(true);
            }
        });
    }
}
