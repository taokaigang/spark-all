package com.spark.operator;

import com.spark.bean.Person;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.sources.In;
import scala.Tuple2;
import scala.Tuple3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Join2 {
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

        //4. 连接
        //4.1 内连接
        JavaRDD<Tuple2<Integer, Tuple3<String, String, Integer>>> map1 = stuListRDD.map(line ->
                {
                    String[] fields = line.split("\\s+");
                    Integer id = Integer.valueOf(fields[0]);
                    String name = fields[1];
                    String gender = fields[2];
                    Integer age = Integer.valueOf(fields[3]);
                    Tuple3<String, String, Integer> tuple3 = new Tuple3<>(name, gender, age);
                    Tuple2<Integer, Tuple3<String, String, Integer>> result = new Tuple2<>(id, tuple3);
                    return result;
                }
        );

        JavaRDD<Tuple2<Integer, Tuple2<String, Double>>> map2 = scoreListRDD.map(line -> {
            String[] fields = line.split("\\s+");
            Integer stuid = Integer.valueOf(fields[0]);
            String course = fields[1];
            Double score = Double.valueOf(fields[2]);
            Tuple2<String, Double> tuple2 = new Tuple2<>(course, score);
            Tuple2<Integer, Tuple2<String, Double>> tuple2Tuple2 = new Tuple2<>(stuid, tuple2);
            return tuple2Tuple2;
        });



        JavaPairRDD<Integer, Tuple3<String, String, Integer>> integerTuple3JavaPairRDD = map1.mapToPair(tp -> {
            return tp;
        });
        JavaPairRDD<Integer, Tuple2<String, Double>> integerTuple2JavaPairRDD = map2.mapToPair(tp -> {
            return tp;
        });

        JavaPairRDD<Integer, Tuple2<Tuple3<String, String, Integer>, Tuple2<String, Double>>> join = integerTuple3JavaPairRDD.join(integerTuple2JavaPairRDD);

        join.foreach(new VoidFunction<Tuple2<Integer, Tuple2<Tuple3<String, String, Integer>, Tuple2<String, Double>>>>() {
            @Override
            public void call(Tuple2<Integer, Tuple2<Tuple3<String, String, Integer>, Tuple2<String, Double>>> integerTuple2Tuple2) throws Exception {
                System.out.println(integerTuple2Tuple2);
            }
        });

        sc.stop();

    }
}
