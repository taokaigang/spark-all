package com.spark.streaming;

import org.apache.spark.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.*;
import scala.Tuple2;

import java.util.Arrays;

/**
 * 打包后参数可以运行
 ./bin/spark-submit \
 --class com.spark.streaming.Demo01NetworkWordCount \
 /Users/kgt/app/spark-streaming-1.0-SNAPSHOT.jar \
 100
 */
public class Demo01NetworkWordCount {
    public static void main(String[] args) throws InterruptedException {

        // 参数过滤
//        if(args == null || args.length != 2){
//            System.out.println("Useage : <hostname> <port>");
//            System.exit(-1);
//        }
//
//        List<String> list = new ArrayList<>();
//        for (String arg : args) {
//            list.add(arg);
//        }

        // Create a local StreamingContext with two working thread and batch interval of 1 second
        SparkConf conf = new SparkConf().setAppName("NetworkWordCount").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        // Create a DStream that will connect to hostname:port, like localhost:9999
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);

        // Split each line into words
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")).iterator());

        // Count each word in each batch
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey((i1, i2) -> i1 + i2);

        // Print the first ten elements of each RDD generated in this DStream to the console
        wordCounts.print();

        //4. 为了执行的流式计算，必须要调用start来启动
        jssc.start();              // Start the computation

        //5. 为了不至于start启动程序结束，必须要调用awaitTermination方法等待程序业务完成之后调用stop方法结束程序，或者异常
        jssc.awaitTermination();   // Wait for the computation to terminate

    }
}
