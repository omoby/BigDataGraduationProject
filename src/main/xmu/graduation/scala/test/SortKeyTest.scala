package graduation.scala.test

import graduation.scala.spark.SortKey
import org.apache.spark.{SparkConf, SparkContext}

/**
  * FileName: SortKeyTest
  * Author:   hadoop
  * Email:    3165845957@qq.com
  * Date:     19-3-23 下午8:35
  * Description:
  * scala二次排序测试后类
  *
  */
object SortKeyTest {
  def main(args: Array[String]): Unit = {
    val conf  = new SparkConf().setAppName("SortKeyTest")
      .setMaster("local")
      val sc = new SparkContext(conf)
    val arr = Array(Tuple2(new SortKey(10,30,23),"1"),
      Tuple2(new SortKey(34,30,20),"2"),
      Tuple2(new SortKey(10,33,23),"3")
      )
    val rdd = sc.parallelize(arr,1)
    val sortedRDD = rdd.sortByKey(false)
    for (tuple <- sortedRDD.collect()){
      println(tuple._2)
    }
  }

}
