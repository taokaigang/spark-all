package com.spark.operator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class ReduceByKey {
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
        list.add("1,杨过,1,22,1904-bd-bj");
        list.add("2,郭靖,1,19,1904-bd-bj");
        list.add("3,令狐冲,0,27,1904-bd-sz");
        list.add("4,韦小宝,1,27,1904-bd-bj");
        list.add("5,胡斐,2,17,1904-bd-hz");
        list.add("6,张无忌,0,28,1904-bd-hz");

        JavaRDD<String> javaRDD = sc.parallelize(list, 1);
        JavaPairRDD<String, String> tStuListRDD = javaRDD.mapToPair(new PairFunction<String, String, String>() {
            @Override
            public Tuple2<String, String> call(String s) throws Exception {
                String[] fields = s.split(",");
                Tuple2<String, String> tuple2 = new Tuple2<>(fields[4], fields[1]);
                return tuple2;
            }
        });

        JavaPairRDD<String, String> stringStringJavaPairRDD = tStuListRDD.reduceByKey(new Function2<String, String, String>() {
            @Override
            public String call(String s, String s2) throws Exception {
                return s + "_" + s2;
            }
        }).sortByKey(true, 1);

        stringStringJavaPairRDD.foreach(new VoidFunction<Tuple2<String, String>>() {
            @Override
            public void call(Tuple2<String, String> stringStringTuple2) throws Exception {
                System.out.println(stringStringTuple2._1+" "+stringStringTuple2._2);
            }
        });

    }
}
