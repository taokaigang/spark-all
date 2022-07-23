package com.spark;


import com.spark.bean.Score;
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

public class Demo02 {
    public static void main(String[] args) throws SQLException {
        //        /*private static*/ final org.slf4j.Logger log = LoggerFactory.getLogger(Demo01SparkSQL.class);
        // 日志处理
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN);
        Logger.getLogger("org.spark_project").setLevel(Level.WARN);

        //1. 获取到入口
        SparkSession spark = SparkSession
                .builder()
                .appName("Demo01SparkSQL")
//                .config("spark.some.config.option", "some-value")
                .master("local[*]")
                .getOrCreate();

        //1.获取连接
        Connection conn = DruidUtils.getConnection();
        //2.定义sql
        String sql = "select * from score";
        //获取执行者对象
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        Score score;
        List<Score> scores = new ArrayList<>();
        //遍历ResultSet结果集  存入List
        while (rs.next()){
            score = new Score();
            score.setUid(rs.getString("uid"));
            score.setSubject_id(rs.getString("subject_id"));
            score.setScore(rs.getString("score"));
            scores.add(score);
        }

//        for (int i = 0; i < scores.size(); i++) {
//            System.out.println(scores.get(0));
//        }

        Dataset<Row> df = spark.createDataFrame(scores, Score.class);
//        df.show();

        df.createOrReplaceTempView("score");

        spark.sql("select\n" +
                "uid\n" +
                "from(\n" +
                "    select\n" +
                "    uid,\n" +
                "    if(score > avg_score,0,1) flag\n" +
                "    from (\n" +
                "        select\n" +
                "        uid,\n" +
                "        score,\n" +
                "        avg(score) over(partition by subject_id) avg_score\n" +
                "        from\n" +
                "        score\n" +
                "    )t1\n" +
                ")t2\n" +
                "group by uid\n" +
                "having sum(flag) = 0").show();


    }
}
