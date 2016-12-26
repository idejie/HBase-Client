
import HBase.Operator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jruby.RubyProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

        panel3.setLayout(new GridLayout(7,1));
        panel4.setLayout(new GridLayout(7,1));
        panel3.add(new Label("exp3"));
        panel4.add(new Label("exp4"));


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
                Operator.putData(tab,family,rowKey,col,value);
                System.out.println("已成功插入"+tab+","+family+","+rowKey+","+col+","+value);
            }
        });


        //exp4
        JPanel tabPan2=new JPanel();
        tabPan2.setLayout(new GridLayout(1,2));
        JLabel tabLab2=new JLabel("表名：");
        JTextField tabVal2=new JTextField();
        tabPan2.add(tabLab2);
        tabPan2.add(tabVal2);



        JPanel famPan2=new JPanel();
        famPan2.setLayout(new GridLayout(1,2));
        JLabel famLab2=new JLabel("列族：");
        JTextField famVal2=new JTextField();
        famPan2.add(famLab2);
        famPan2.add(famVal2);

        JPanel rowPan2=new JPanel();
        rowPan2.setLayout(new GridLayout(1,2));
        JLabel rowLab2=new JLabel("rowKey：");
        JTextField rowVal2=new JTextField();
        rowPan2.add(rowLab2);
        rowPan2.add(rowVal2);



        JPanel colPan2=new JPanel();
        colPan2.setLayout(new GridLayout(1,2));
        JLabel colLab2=new JLabel("列名：");
        JTextField colVal2=new JTextField();
        colPan2.add(colLab2);
        colPan2.add(colVal2);


        JPanel valPan2=new JPanel();
        valPan2.setLayout(new GridLayout(1,2));
        JLabel valLab2=new JLabel("值：");
        JTextField val2=new JTextField();
        valPan2.add(valLab2);
        valPan2.add(val2);
        panel4.add(tabPan2);
        panel4.add(famPan2);
        panel4.add(rowPan2);
        panel4.add(colPan2);
        panel4.add(valPan2);
        JPanel upPan= new JPanel();
        upPan.setLayout(new GridLayout(1,2));
        JButton upBut=new JButton("更新");
        upBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tab=tabVal2.getText();
                String family=famVal2.getText();
                String rowKey=rowVal2.getText();
                String col=colVal2.getText();
                String value=val2.getText();
                Operator.putData(tab,family,rowKey,col,value);
                System.out.println("已成功插入"+tab+","+family+","+rowKey+","+col+","+value);
            }
        });
        JButton xlcBut=new JButton("导入");
        xlcBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showDialog(new JLabel(),"选择");
                File file =fileChooser.getSelectedFile();
                String tab=tabVal2.getText();
                String family=famVal2.getText();
                String rowKey=rowVal2.getText();
//                String col=colVal2.getText();
//                String value=val2.getText();
                Map<String,List<Map<String,String> > > result =getData(file.getAbsolutePath(),"sheet1");
                List<Map<String,String>> list=result.get(rowKey);
                int length=list.get(0).size();
                List<Put>putList=new ArrayList<Put>();
                String []col=new String[length];
                String[]values=new String [length];
                Set<String>get=list.get(0).keySet();
                int c=0;
                for (String test :
                        get) {
                    col[c] = test;
                    values[c]=list.get(0).get(test);
                    c++;
                }
                for (int i = 0; i < length; i++) {
                    Put put=new Put(Bytes.toBytes("0"));
                    put.add(Bytes.toBytes(family),Bytes.toBytes(col[i]),Bytes.toBytes(values[i]));
                    putList.add(put);
                }
                try {
                    Connection connection= ConnectionFactory.createConnection(Operator.configuration);
                    System.out.println("xls connected");
                    org.apache.hadoop.hbase.client.Table table=connection.getTable(TableName.valueOf(tab));
                    table.put(putList);
                    System.out.println("xls put");
                    table.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        upPan.add(upBut);
        upPan.add(xlcBut);
        panel4.add(upPan);

        //exp5
        GridBagLayout layout=new GridBagLayout();
        panel5.setLayout(layout);
//        panel5.add(new Label("exp5"));
        JPanel numPan=new JPanel();
        numPan.setLayout(new GridLayout(1,2));
        JLabel numLab=new JLabel("学号:");
        JTextField numVal=new JTextField();
        numPan.add(numLab);
        numPan.add(numVal);
        JButton queryBut=new JButton("查询");
        queryBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operator.queryTable("user201400301196:"+numVal.getText());
            }
        });
        JTextArea lesson=new JTextArea();
        GridBagConstraints s= new GridBagConstraints();
        s.fill=GridBagConstraints.BOTH;
        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(numPan,s);
        layout.setConstraints(queryBut,s);
        s.weightx = 1;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=1;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间

        layout.setConstraints(lesson,s);
        panel5.add(numPan);
        panel5.add(queryBut);
        panel5.add(lesson);


        panel6.add(new Label("exp6"));
        JButton queryLesson=new JButton("查询所有课程名");
        queryLesson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<String> set=Operator.getDistinctCol("user201400301196:student","info","lesson1");
                System.out.println("hh");
                for (String str:
                    set ) {
                    System.out.println(str);
                }
            }
        });
        JButton queryTop10=new JButton("查询平均Top10");
        panel6.add(queryLesson);
        panel6.add(queryTop10);
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
                cardLayout.show(content,"exp6");
            }
        });
        Table.getContentPane().add(content);
        Table.getContentPane().add(panelB,BorderLayout.SOUTH);
        Table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Table.setVisible(true);
    }

    private Map<String,List<Map<String,String>>> getData(String path, String sheet1) {
        Map<String,List<Map<String,String> > > result=new HashMap<String,List<Map<String,String>>>();
        List<Map<String,String>>resultCells;
        Map<String,String>resultCell;
        try {
            FileInputStream file=new FileInputStream(path);
            POIFSFileSystem fileSystem=new POIFSFileSystem(file);
            Workbook workbook =new HSSFWorkbook(fileSystem);
            Sheet sheet=workbook.getSheet(sheet1);
            int rowCount=sheet.getPhysicalNumberOfRows();
            int colCount=sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i < rowCount; i++) {
                org.apache.poi.ss.usermodel.Row row =sheet.getRow(i);

                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                resultCells=new ArrayList<Map<String,String>>();
                resultCell=new HashMap<String,String>();
                String rowKey=row.getCell(0).toString();
                for (int j = 1; j < colCount; j++) {
                    Cell cell=row.getCell(j);
                    if (cell!=null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String colName = sheet.getRow(0).getCell(j).toString();
                        String cellVal = cell.toString();
                        resultCell.put(colName, cellVal);
                    }
                }
                resultCells.add(resultCell);
                result.put(rowKey,resultCells);
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
