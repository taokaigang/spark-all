package com.spark.operator;

import com.spark.bean.Person;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java bean 去重
 */
public class Distinct {
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

        // 需求，判断两个person只要用户名和性别一样，就认为是一个人，在distinct中就能够去重
        Person p1 = new Person("kg", "man");
        Person p2 = new Person("kg", "man");

        List<Person> plist = new ArrayList<>();
        plist.add(p1);
        plist.add(p2);
        JavaRDD<Person> plistRDD = sc.parallelize(plist, 1);
        JavaRDD<Person> distinct = plistRDD.distinct();

        distinct.foreach(new VoidFunction<Person>() {
            @Override
            public void call(Person person) throws Exception {
                System.out.println(person.toString());
            }
        });

        sc.stop();

    }
}
