package com.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Demo02Lamda {
    public static void main(String[] args) {
        //1. 获取到入口
        SparkConf conf = new SparkConf();
        conf.setAppName(Demo02Lamda.class.getSimpleName());
        conf.setMaster("local[*]");

        JavaSparkContext context = new JavaSparkContext(conf);

        //2. 加载文件
        context.textFile("/Users/kgt/code/idea/spark-all/spark-core/src/main/resources/wc.txt")
                .flatMap(line -> Arrays.asList(line.split("\\s+")).iterator())
                .mapToPair(word -> new Tuple2<String, Integer>(word, 1))
                .reduceByKey((Integer v1, Integer v2) -> v1 + v2)
                .foreach(tuple2 -> System.out.println(tuple2._1 + "-->" + tuple2._2));
    }
}
