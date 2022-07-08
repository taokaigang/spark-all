package com.spark.operator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;

public class Join {
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


        JavaRDD<Integer> leftRDD = sc.parallelize(Arrays.asList(1,3,5,7,9),1);
        JavaRDD<Integer> rightRDD = sc.parallelize(Arrays.asList(2,4,6,8,10),1);

        // union
        JavaRDD<Integer> union = leftRDD.union(rightRDD);


        union.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

        sc.stop();

    }
}
