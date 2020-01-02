package com;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * ClassName:PreparedCase
 * Package:com
 * Description:
 *
 * @Date:2020/1/1 20:34
 * @Author:DangWei
 */

public class PreparedCase {
    private static final String URL="jdbc:mysql://localhost/course";
    private static final String User="root";
    private static final String Password="";
   private static DataSource getDataSource(){
       MysqlDataSource ds=new MysqlDataSource();
       ds.setURL(URL);
       ds.setUser(User);
       ds.setPassword(Password);
       return ds;
   }
public static void createTable(){
       DataSource ds=getDataSource();
       Connection connection=null;
       PreparedStatement stmt=null;
    try {
        connection=ds.getConnection();
        String str="create table student(name varchar(20),id int,age int)";
        stmt=connection.prepareStatement(str);
        stmt.execute();
    } catch (SQLException e) {
        e.printStackTrace();
    }finally{
        try {
            if(stmt!=null){
                stmt.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
private static class Student{
        private String name;
        private int id;
        private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student(String name, int id, int age){
            this.name=name;
            this.age=age;
            this.id=id;
        }

    }
   public static void insertTable(Student st){
       DataSource ds=getDataSource();
       Connection connection=null;
       PreparedStatement stmt=null;
       try {
           connection=ds.getConnection();
           String str="insert into student values(?,?,?)";
           stmt=connection.prepareStatement(str);
           stmt.setString(1,st.name);
           stmt.setInt(2,st.id);
           stmt.setInt(3,st.age);
           stmt.execute();
       } catch (SQLException e) {
           e.printStackTrace();
       }finally {
           try {
               if(stmt!=null){
                   stmt.close();
               }
               if(connection!=null){
                   connection.close();
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
   public static void selectTable(){
       DataSource ds=getDataSource();
       Connection connection=null;
       PreparedStatement stmt=null;
       ResultSet res=null;
       try {
           connection=ds.getConnection();
           String str="select * from student";
           stmt=connection.prepareStatement(str);
           res=stmt.executeQuery();
           while(res.next()){
               String s=res.getString("name");
               Integer id=res.getInt("id");
               Integer age=res.getInt("age");
               System.out.println("name:"+s+"  id:"+id+"  age:"+age);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }finally {
           try {
               if(res!=null){
                   res.close();
               }
               if(stmt!=null){
                   stmt.close();
               }
               if(connection!=null){
                   connection.close();
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
    public static void main(String[] args) {
        //createTable();
        Student st1=new Student("李四",2,22);
        Student st2=new Student("王五",3,18);
        insertTable(st1);
        insertTable(st2);
        selectTable();
    }
}
