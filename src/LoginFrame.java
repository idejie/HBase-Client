import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by idejie on 16/12/16.
 */
public class LoginFrame extends JFrame {
    public LoginFrame(){
        setLocation(300,200);
        setLayout(new GridLayout(4,1));
        setTitle("Nosql");

        JPanel title= new JPanel();
        JLabel titleLab= new JLabel("HBase Client");
        title.add(titleLab);
        add(title);

        JPanel nameForm =new JPanel();
        nameForm.setLayout(new GridLayout(1,2,1,1));
        JLabel nameLab=new JLabel("Name:");
        JTextField nameText=new JTextField();
        nameForm.add(nameLab);
        nameForm.add(nameText);
        add(nameForm);

        JPanel passwordForm=new JPanel();
        passwordForm.setLayout(new GridLayout(1,2,1,1));
        JLabel pwLab=new JLabel("Password:");
        JPasswordField pwText= new JPasswordField();
        passwordForm.add(pwLab);
        passwordForm.add(pwText);
        add(passwordForm);

        JPanel loginForm=new JPanel();
        loginForm.setLayout(new GridLayout(1,1));
        JButton login= new JButton("Login");
        loginForm.add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                System.out.println("name:"+nameText.getText());
                System.out.println("password:"+pwText.getText());
                if (nameText.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please input name","Error",JOptionPane.ERROR_MESSAGE);
                }else if (pwText.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please input password","Error",JOptionPane.ERROR_MESSAGE);
                }else {
                    new HBaseWindow(nameText.getText(),pwText.getText());
//                    setVisible(false);
                    dispose();
                }
            }
        });
        add(loginForm);

        pack();
        setSize(300,200);
        setVisible(true);
    }

}
