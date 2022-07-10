package com.spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

public class Demo01SparkSQL {
    public static void main(String[] args) {
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

        //2. 读取json
        Dataset<Row> df = spark.read().json("/Users/kgt/code/idea/spark-all/spark-sql/src/main/resources/people.json");

        //3. 打印元信息
//        df.show();
        df.printSchema();

        df.select(col("name"), col("age"), col("height").plus(10)).show();

        df.groupBy("age").count().show();

        df.filter(col("age").gt(21)).show();

        // sql
        df.createOrReplaceTempView("people");

        spark.sql("select\n" +
                "count(age)\n" +
                "from\n" +
                "people\n" +
                "group By\n" +
                "age").show();

        //

        df.groupBy("age")
                .agg(collect_list(concat_ws("-",col("name")).as("ctws"))).show();

        spark.stop();

    }
}
