package com.spark.operator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

public class Cogroup {
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


        //2. 准备
        //stu表：id，name，gender，age
        //score表：stuid,course, score
        List<String> stuList = new ArrayList<>();
        stuList.add("1 刘诗诗 女 18");
        stuList.add("2 欧阳娜娜 女 55");
        stuList.add("3 大幂幂 女 33");
        stuList.add("4 李冰冰 女 31");

        List<String> scoreList = new ArrayList<>();
        scoreList.add("1 语文 59");
        scoreList.add("3 数学 0");
        scoreList.add("2 英语 60");
        scoreList.add("5 体育 99");

        //3. 加载数据
        JavaRDD<String> stuListRDD = sc.parallelize(stuList,1);
        JavaRDD<String> scoreListRDD = sc.parallelize(scoreList,1);

        // 分离 第一列数据作为key 后面的数据作为value
//        stuListRDD.map()





    }
}
