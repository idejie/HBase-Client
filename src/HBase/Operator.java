package HBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            try {
                admin.getNamespaceDescriptor("user201400301196");
            } catch (IOException e) {
                e.printStackTrace();
                admin.createNamespace(NamespaceDescriptor.create("user201400301196").build());
            }

            System.out.printf("已建立连接！");
            if (admin.tableExists(TableName.valueOf(table))){
                System.out.println(table+"已存在！");
            }else{
                HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(table));
                tableDescriptor.addFamily(new HColumnDescriptor(family));
                admin.createTable(tableDescriptor);
                System.out.println(table+"创建成功！");
            }
            admin.close();
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
                if (!admin.isTableEnabled(TableName.valueOf(table))) admin.enableTable(TableName.valueOf(table));
            }
            admin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * modify table
     * @param table 表名
     * @param removeFam 删除列族
     * @param putFam 添加列族
     * @param column 修改的列
     */
    public static void modifyTable(String table,String removeFam,String putFam,String column){
        try {
            Connection connection = ConnectionFactory.createConnection(configuration);
            Admin admin =connection.getAdmin();
            if(admin.isTableEnabled(TableName.valueOf(table))) admin.disableTable(TableName.valueOf(table));
            HTableDescriptor newTd=admin.getTableDescriptor(TableName.valueOf(table));
            if (!removeFam.equals("")){
                newTd.removeFamily(removeFam.getBytes());
            }
            if (!putFam.equals("null")){
                HColumnDescriptor newCd=new HColumnDescriptor(putFam.getBytes());
                newCd.setMaxVersions(10);//!!!???
                newCd.setKeepDeletedCells(true);
                newTd.addFamily(newCd);
            }
            admin.modifyTable(TableName.valueOf(table),newTd);
            if (!admin.isTableEnabled(TableName.valueOf(table))) admin.enableTable(TableName.valueOf(table));
            admin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * put data
     * @param table 表名
     * @param family 列族
     * @param rowKey rowKey
     * @param column 列
     * @param value 值
     */
    public static void putData(String table,String family,String rowKey,String column,String value ){
        try {
            Connection connection =ConnectionFactory.createConnection(configuration);
            Table tableName =connection.getTable(TableName.valueOf(table));
            System.out.printf("已建立连接！");
            //列值
            Put put = new Put(rowKey.getBytes());
            put.add(family.getBytes(),column.getBytes(),value.getBytes());
            put.setDurability(Durability. SYNC_WAL );
            tableName.put(put);
            System.out.println("已经在表["+table+"]的行"+"["+rowKey+"]插入列["+column+"]值："+value);
            tableName.close();
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

    /**
     * query table
     * @param table 表名
     */
    public static void queryTable(String table){
        Connection connection= null;
        try {
            connection = ConnectionFactory.createConnection(configuration);
            System.out.println("已经建立连接");
            Table tableName =connection.getTable(TableName.valueOf(table));
            ResultScanner scanner =tableName.getScanner(new Scan());
            for (Result r:scanner
                 ) {
                System.out.println("获得rowkey"+new String(r.getRow()));
                for (KeyValue v :
                        r.raw()) {
                    System.out.println("列："+new String(v.getFamily())+":"+new String(v.getQualifier())+"值为："+new String(v.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  Set<String> getDistinctCol(String table,String colFamilyName, String colName) {
        Set<String> set = new HashSet<String>();
        Connection connection= null;
        try {
            connection = ConnectionFactory.createConnection(configuration);
            System.out.println("已经建立连接");
            Table tableName =connection.getTable(TableName.valueOf(table));
            ResultScanner scanner =tableName.getScanner(new Scan());
            for (Result r:scanner
                    ) {
                System.out.println("获得rowkey"+new String(r.getRow()));
                for (KeyValue v :
                        r.raw()) {
                    System.out.println("列："+new String(v.getFamily())+":"+new String(v.getQualifier())+"值为："+new String(v.getValue()));

                    if (colName.equals(new String(v.getQualifier()))||new String(v.getQualifier()).equals("lesson2"))
                        set.add(new String(v.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * Top k 的问题(这里实现平均成绩top10)
     * @param n
     */
    public static void topRank(int n){

    }
}
