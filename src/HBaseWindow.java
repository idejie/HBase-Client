
import HBase.Operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by idejie on 16/12/16.
 */
public class HBaseWindow {

    CardLayout cardLayout;
    JPanel content;
    JPanel panelB;

    public HBaseWindow(String name, String password) {
        JFrame Table=new JFrame("HBase Client");
        Table.setSize(400,400);
        Table.setLocation(300,200);
        cardLayout =new CardLayout(5,5);

        panelB =new JPanel();

        JButton exp3=new JButton("Exp3");
        JButton exp4=new JButton("Exp4");
        JButton exp5=new JButton("Exp5");
        JButton exp6=new JButton("Exp6");

        panelB.add(exp3);
        panelB.add(exp4);
        panelB.add(exp5);
        panelB.add(exp6);

        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();
        JPanel panel5=new JPanel();
        JPanel panel6=new JPanel();

        panel3.setLayout(new GridLayout(6,1));
//        panel3.add(new Label("exp3"));
        panel4.add(new Label("exp4"));
        panel5.add(new Label("exp5"));
        panel6.add(new Label("epx6"));

        //exp3

        JPanel tabPan=new JPanel();
        tabPan.setLayout(new GridLayout(1,2));
        JLabel tabLab=new JLabel("表名：");
        JTextField tabVal=new JTextField();
        tabPan.add(tabLab);
        tabPan.add(tabVal);
        panel3.add(tabPan);


        JPanel famPan=new JPanel();
        famPan.setLayout(new GridLayout(1,2));
        JLabel famLab=new JLabel("列族：");
        JTextField famVal=new JTextField();
        famPan.add(famLab);
        famPan.add(famVal);
        panel3.add(famPan);

        JPanel rowPan=new JPanel();
        rowPan.setLayout(new GridLayout(1,2));
        JLabel rowLab=new JLabel("rowKey：");
        JTextField rowVal=new JTextField();
        rowPan.add(rowLab);
        rowPan.add(rowVal);
        panel3.add(rowPan);


        JPanel colPan=new JPanel();
        colPan.setLayout(new GridLayout(1,2));
        JLabel colLab=new JLabel("列名：");
        JTextField colVal=new JTextField();
        colPan.add(colLab);
        colPan.add(colVal);
        panel3.add(colPan);

        JPanel valPan=new JPanel();
        valPan.setLayout(new GridLayout(1,2));
        JLabel valLab=new JLabel("值：");
        JTextField val=new JTextField();
        valPan.add(valLab);
        valPan.add(val);
        panel3.add(valPan);

        JButton inBut =new JButton("插入");
        panel3.add(inBut);
        inBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tab=tabVal.getText();
                String family=famVal.getText();
                String rowKey=rowVal.getText();
                String col=colVal.getText();
                String value=val.getText();
                Operator.insertData(tab,family,rowKey,col,value);
                System.out.println("已成功插入"+tab+","+family+","+rowKey+","+col+","+value);
            }
        });

        content=new JPanel(cardLayout);
        content.add(panel3,"exp3");
        content.add(panel4,"exp4");
        content.add(panel5,"exp5");
        content.add(panel6,"exp6");

        exp3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"exp3");
            }
        });
        exp4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"exp4");
            }
        });
        exp5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"exp5");
            }
        });
        exp6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(content,"exp3");
            }
        });
        Table.getContentPane().add(content);
        Table.getContentPane().add(panelB,BorderLayout.SOUTH);
        Table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Table.setVisible(true);
    }


}
