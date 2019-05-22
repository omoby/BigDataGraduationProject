package graduation.scala.test

import graduation.java.conf.ConfigurationManager
import graduation.java.constant.Constants
import  graduation.java.test.MockData
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext

/**
  * FileName: UserVisitSessionAnalyizSpark
  * Author:   hadoop
  * Email:    3165845957@qq.com
  * Date:     19-2-28 下午7:13
  * Description:
  *
  */
object UserVisitSessionAnalyizSpark {


  def main(args:Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)
    val  conf = new SparkConf().setAppName(Constants.SPARK_APP_NAME_SESSION).setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    mockData(sc,sqlContext)
    sc.stop()
  }

  def getContext(sc:SparkContext) ={
    val local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL)
    if (local){
      new SQLContext(sc)
    } else{
      new HiveContext(sc)
    }
  }

  def mockData(sc:SparkContext,sqlContext:SQLContext): Unit ={
    val  local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL)
    if (local){
      MockData.mock(sc,sqlContext)
    }
  }

}
