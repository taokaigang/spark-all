package com.spark.test;


import com.spark.bean.Emp;
import com.spark.util.DruidUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DruidTest {
    public static void main(String[] args) throws SQLException {
        //给dept表添加一条记录
        //1.获取连接
        Connection conn = DruidUtils.getConnection();
        //2.定义sql
        String sql = "select * from emp";
        //获取执行者对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        Emp emp;
        List<Emp> emps=new ArrayList<>();
        //遍历ResultSet结果集  存入List
        while (rs.next()){
            emp=new Emp();
            emp.setEmpno(rs.getInt("empno"));
            emp.setEname(rs.getString("ename"));
            emp.setJob(rs.getString("job"));
            emp.setMgr(rs.getInt("mgr"));
            emp.setHiredate(rs.getDate("hiredate"));
            emp.setSalary(rs.getDouble("sal"));
            emp.setComm(rs.getDouble("comm"));
            emp.setDeptno(rs.getInt("deptno"));
            emps.add(emp);
        }

        //输出list查看
//        for (Emp emp1 : emps) {
//            System.out.println(emp1);
//        }

//        DruidUtils.close(conn,pstmt,rs);

        // 日志处理
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN);
        Logger.getLogger("org.spark_project").setLevel(Level.WARN);

        //1. 获取到入口
        SparkSession session = SparkSession
                .builder()
                .appName("Demo01SparkSQL")
//                .config("spark.some.config.option", "some-value")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df = session.createDataFrame(emps,Emp.class);
        df.show();

        df.createOrReplaceTempView("emp");

        session.sql("select\n" +
                "*\n" +
                "from\n" +
                "emp\n" +
                "where\n" +
                "mgr = 0").show();



    }
}
