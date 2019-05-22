package graduation.java.test;

import graduation.java.jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName: JDBCHelperTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-27 上午10:18
 * Description:JDBC辅助组件测试类
 */
public class JDBCHelperTest {
    public  static void main(String[] args){
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        //测试普通的增删改语句
        //testExecuteUpdate(jdbcHelper);

        //测试查询语句
        testExecuteQuery(jdbcHelper);

        // 测试批量执行SQL语句
        //testExecuteBatch(jdbcHelper);



    }

    public static void testExecuteUpdate(JDBCHelper jdbcHelper){
        String sql = "insert into test_user(name,age) values(?,?)";
        Object[] obj = new Object[]{"Merry",29};
        int rtn = jdbcHelper.executeUpdate(sql,obj);
        System.out.println("result: " + rtn);

    }

    public static void testExecuteQuery(JDBCHelper jdbcHelper){
        final Map<String,Object> testUser = new HashMap<String,Object>();
        //设计一个内部接口QueryCallback
        // 那么在执行查询语句的时候，我们就可以封装和指定自己的查询结果的处理逻辑
        // 封装在一个内部接口的匿名内部类对象中，传入JDBCHelper的方法
        // 在方法内部，可以回调我们定义的逻辑，处理查询结果
        // 并将查询结果，放入外部的变量中
        //String sql  = "select  name ,age from test_user where id=?";
        String sql  = "select  name ,age from test_user where 1=1";
        Object[] obj = new Object[]{};

        jdbcHelper.executeQuery(sql, obj, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    // 匿名内部类的使用，有一个很重要的知识点
                    // 如果要访问外部类中的一些成员，比如方法内的局部变量
                    // 那么，必须将局部变量，声明为final类型，才可以访问
                    // 否则是访问不了的
                    System.out.println("name: "+ name + " age: "+ age);
                    testUser.put("name",name);
                    testUser.put("age",age);
                }
            }
        });
        System.out.println(testUser.get("name")+":"+testUser.get("age"));
    }

    public static void testExecuteBatch(JDBCHelper jdbcHelper){
        String sql = "insert into test_user(name,age) values(?,?)";
        List<Object[]> paramsList = new ArrayList<Object[]>();
        paramsList.add(new Object[]{"Mark",12});
        paramsList.add(new Object[]{"Bill",25});
        jdbcHelper.executeBatch(sql,paramsList);
    }




}
