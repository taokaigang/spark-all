package com.spark.udf;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class Demo01 {
    public static void main(String[] args) {
        // 日志处理
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN);
        Logger.getLogger("org.spark_project").setLevel(Level.WARN);

        //1. 获取到spark context
        //1. 获取到入口
        SparkSession session = SparkSession
                .builder()
                .appName("Demo01SparkSQL")
//                .config("spark.some.config.option", "some-value")
                .master("local[*]")
                .getOrCreate();

//        session.udf.register("UDAFavg",UDAFavg)

//        session.udf().register()

    }
}
