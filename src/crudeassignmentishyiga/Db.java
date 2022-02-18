package crudeassignmentishyiga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Db {
      Connection connection;
      Statement statement;
    public Db(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/ishyigadata", "root",null);
            statement = connection.createStatement();
            System.out.print("connection Succed");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("fail to connect to database");
            ex.printStackTrace();
        }
        //return null;
    }
    public Statement getConn(){
        return statement;
    }
    public boolean dConn(){
        try {
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("fail to close");
            return false;
        }
    }
    public boolean executeSet(String query) {
        try {
            System.out.println(query);
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            //  ex.printStackTrace(System.out);

            return false;
        }
    }
    public boolean insertData(Data data){
        String query = "INSERT INTO data(sex, department, favouriteLanguage, fullName)values(\""+data.getSex()+"\",\""+data.getDepartment()+"\",\""+data.favouriteLanguage+"\",\""+data.fullName+"\");";
        if(executeSet(query)){
            System.out.println("insert data success");
            return true;

        }
        else {
            System.out.println("fail to insert" + query);
            return false;
        }

    }
    public boolean deleteData(String dataId){
        String query = "DELETE FROM data where dataId="+dataId+";";
        return executeSet(query);
    }
    public boolean updateData(Data data){
        String query = "UPDATE data SET sex=\""+data.getSex()+"\",fullName=\""+data.getFullName()+"\",department=\""+data.getDepartment()+"\",favouriteLanguage=\""+data.getFavouriteLanguage()+"\" WHERE dataId="+data.getDataId();
        return executeSet(query);
    }
    public ResultSet executeGet(String query) {

        ResultSet output = null;
        try {
            output = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.print("fail to select data");
//            ex.printStackTrace(System.out);
        }

        return output;
    }
    public ArrayList<Data> getData(){
        String query = "SELECT * FROM data;";
        ResultSet resultSet = executeGet(query);

        ArrayList<Data> results = new ArrayList<>();

        while (true){
            try {
                if (!resultSet.next()) break;
                Data data = new Data();
                data.setDataId(resultSet.getString("dataId"));
                data.setDepartment(resultSet.getString("department"));
                data.setFavouriteLanguage(resultSet.getString("favouriteLanguage"));
                data.setFullName(resultSet.getString("fullName"));
                data.setSex(resultSet.getString("sex"));
                results.add(data);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }

        }
        return results;
    }
}
