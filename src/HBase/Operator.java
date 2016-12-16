package HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by idejie on 16/12/16.
 */
public class Operator {
    public static Configuration configuration;
    static {
        configuration= HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort","2181");
        configuration.set("hbase.zookeeper.quorum","211.87.227.194,211.87.227.195,211.87.227.196");
        configuration.set("hbase.master","211.87.227.194:60000");
        configuration.set("hbase.cluster.distrubuted","true");
//        System.out.println(configuration);
    }




    /**
     * create table
     *
     * @param table 表名
     * @param family 列族
     */
    public static void createTable(String table,String family){
        System.out.println("创建表--"+table+"--"+family+"！");
        try {
            Connection connection= ConnectionFactory.createConnection(configuration);
            Admin admin=connection.getAdmin();
            System.out.printf("已建立连接！");
            if (admin.tableExists(TableName.valueOf(table))){
                System.out.println(table+"已存在！");
            }else{
                HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(table));
                tableDescriptor.addFamily(new HColumnDescriptor(family));
                admin.createTable(tableDescriptor);
                System.out.println(table+"创建成功！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * drop table
     *
     * @param table 表名
     */
    public static void dropTable(String table){

        try {
            Connection connection = ConnectionFactory.createConnection(configuration);
            Admin admin =connection.getAdmin();
            System.out.printf("已建立连接！");
            if (!admin.tableExists(TableName.valueOf(table))){
                System.out.println(table+"不存在");
            }else{
                if(admin.isTableEnabled(TableName.valueOf(table))) admin.disableTable(TableName.valueOf(table));
                admin.deleteTable(TableName.valueOf(table));
                System.out.printf("已删除"+table+"!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * insert data
     * @param table 表名
     * @param family 列族
     * @param rowKey rowKey
     * @param column 列
     * @param value 值
     */
    public static void insertData(String table,String family,String rowKey,String column,String value ){
        try {
            Connection connection =ConnectionFactory.createConnection(configuration);
            Table tableName =connection.getTable(TableName.valueOf(table));
            System.out.printf("已建立连接！");
            //列值
            Put put = new Put(rowKey.getBytes());
            put.addColumn(family.getBytes(),column.getBytes(),value.getBytes());
            tableName.put(put);
            System.out.println("已经在表["+table+"]的行"+"["+rowKey+"]插入列["+column+"]值："+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete data
     * @param table 表名
     * @param rowkey 行
     */
    public static void deleteData(String table,String rowkey){
        try {
            Connection connection=ConnectionFactory.createConnection(configuration);
            System.out.println("已经建立连接");
            Table tableName =connection.getTable(TableName.valueOf(table));
            List<Delete> list=new ArrayList<Delete>();
            Delete delete=new Delete(rowkey.getBytes());
            list.add(delete);
            tableName.delete(list);
            System.out.println("已经在表["+table+"]删除行："+rowkey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
