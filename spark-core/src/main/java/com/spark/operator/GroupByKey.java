package com.spark.operator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple5;

import java.util.ArrayList;
import java.util.List;

public class GroupByKey {
    public static void main(String[] args) {
        // 日志处理
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN);
        Logger.getLogger("org.spark_project").setLevel(Level.WARN);

        //1. 获取到spark context
        SparkConf conf = new SparkConf();
        conf.setAppName(GroupByKey.class.getSimpleName());
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

        JavaPairRDD javaPairRDD = javaRDD.mapToPair(
                new PairFunction<String, Object, Object>() {
                    @Override
                    public Tuple2<Object, Object> call(String s) throws Exception {
                        String[] fields = s.split(",");
                        Tuple5<String, String, String, String, String> tuple5 = new Tuple5<>(fields[0], fields[1], fields[2], fields[3], fields[4]);
                        Tuple2 tuple2 = new Tuple2(fields[4], tuple5);
                        return tuple2;
                    }
                }
        );

        JavaPairRDD result = javaPairRDD.groupByKey();

        result.foreach(new VoidFunction() {
            @Override
            public void call(Object o) throws Exception {
                System.out.println(o.toString());
            }
        });

        sc.stop();

    }
}
