import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    //定义成员变量
    private static DataSource ds;

    static {
        //配置文件
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("D:/JAVA2/untitled/Student Management/src/druid.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取连接池对象
        try {
            ds = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //释放资源
    public static void close(Connection conn){

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //获取连接池方法
    public static DataSource getDatasource(){
        return ds;
    }
}
