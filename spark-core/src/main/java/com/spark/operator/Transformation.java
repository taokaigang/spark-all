package com.spark.operator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;

/**
 * 转换算子
 */
public class Transformation {
    public static void main(String[] args) {
        /**
         * 第一代：RDD(spark core)
         * 第二代：DataFrame
         * 第三代：DataSet
         */

        // 日志处理
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN);
        Logger.getLogger("org.spark_project").setLevel(Level.WARN);

        //1. 获取到spark context
        SparkConf conf = new SparkConf();
        conf.setAppName(Transformation.class.getSimpleName());
        conf.setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);
//        List<String> list = new ArrayList<>();
//        list.add("11 14 155");
//        list.add("12 15 159");
//        list.add("13 16 136");
//        list.add("13 13 150");
//        list.add("13 13 158");
//        list.add("13 13 158");
//        list.add("13 13 156");
//        list.add("13 13 154");
//        list.add("13 13 153");
//        list.add("13 13 152");

        JavaRDD<Integer> listRDD = sc.parallelize(Arrays.asList(1,2,3,4));
        JavaRDD<Integer> map = listRDD.map(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer s) throws Exception {
                return s * s;
            }
        });
        map.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

        sc.stop();

    }
}
