package com.spark.operator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    public static void main(String[] args) {
        // 日志处理
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN);
        Logger.getLogger("org.spark_project").setLevel(Level.WARN);

        //1. 获取到spark context
        SparkConf conf = new SparkConf();
        conf.setAppName(Transformation.class.getSimpleName());
        conf.setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> list = new ArrayList<>();
        list.add("11 14 155");
        list.add("12 15 159");
        list.add("13 16 136");
        list.add("13 13 150");
        list.add("13 13 158");
        list.add("13 13 157");
        list.add("13 13 156");
        list.add("13 13 154");
        list.add("13 13 153");
        list.add("13 13 152");

        JavaRDD<String> javaRDD = sc.parallelize(list, 1);

        // 做映射的时候分区
//        javaRDD.mapPartitionsToPair()
        // 重分区 宽依赖，多用于增加分区
//        javaRDD.repartition()
        // 重分区 默认是窄依赖，也可以是宽依赖
//        javaRDD.coalesce()

        // 在我们的rdd的转换算子中有一个特性，叫做延迟/懒加载。你的转换算子不通过action算子调用的话，他是不会运行的
//        javaRDD.mapPartitionsWithIndex()

    }
}
