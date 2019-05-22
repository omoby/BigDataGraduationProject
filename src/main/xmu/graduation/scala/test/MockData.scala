package graduation.scala.test

import java.lang.String
import java.util
import java.util.UUID

import graduation.java.constant.Constants
import graduation.java.util.{DateUtils, StringUtils}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.types.{DataTypes, StructField}
import org.apache.spark.sql.{Row, RowFactory, SQLContext}

import scala.collection.JavaConverters._
import scala.util.Random
/**
  * FileName: MockData
  * Author:   hadoop
  * Email:    3165845957@qq.com
  * Date:     19-2-27 下午9:27
  * Description:
  *
  */
object MockData{
  def mock(sc:SparkContext, sqlContext:SQLContext ){
    val  rows = new util.ArrayList[Row]()
    val  seacrchKeywords =  Array("火锅", "蛋糕", "重庆辣子鸡", "重庆小面", "呷哺呷哺", "新辣道鱼火锅", "国贸大厦", "太古商场", "日本料理", "温泉")
    val date = DateUtils.getTodayDate
    val actions = Array("search","click","order","pay")
    val random = new Random()
    for(i <- 0 until 100){
      val userid = random.nextInt(100).asInstanceOf[Object]
      for(j <- 0 until  10){
        val sessionid = UUID.randomUUID().toString().replace("-","")
        val  baseActionTime = date +" "+random.nextInt(23)
        for(i <- 0 until  random.nextInt(100)){
          val pageid = random.nextInt(10).asInstanceOf[Object]
          val  actionTime = baseActionTime +":"+ StringUtils.fulfuill(random.nextInt(59).toString)+":"+StringUtils.fulfuill(random.nextInt(59).toString)
          var searchKeyword:String = null
          var  clickCategoryId:Object = null
          var clickProductId:Object = null
          var orderCategoryIds:String = null
          var orderProductIds:String = null
          var payCategoryIds:String = null
          var payProductIds:String = null
          val action = actions(random.nextInt(4))
          if ("search".equals(action)){
            searchKeyword = seacrchKeywords(random.nextInt(10))
          } else if ("click".equals(action)){
            clickCategoryId = random.nextInt(100).toLong.asInstanceOf[Object]
            clickProductId = random.nextInt(100).toLong.asInstanceOf[Object]
          } else if ("order".equals(action)){
            orderCategoryIds = random.nextInt(100).toString
            orderProductIds = random.nextInt(100).toString
          } else if("pay".equals(action)){
            payCategoryIds = random.nextInt(100).toString
            payProductIds = random.nextInt(100).toString
          }
          val row = RowFactory.create(date,userid,sessionid,pageid,actionTime,searchKeyword,clickCategoryId,clickProductId,orderCategoryIds,orderProductIds,payCategoryIds,payProductIds)
          rows.add(row)


        }

      }
    }
    var rowsRDD = sc.parallelize(rows.asScala.toSeq)

    /**
      * 自定义StructField
      */

    val  schema = DataTypes.createStructType(util.Arrays.asList(
      DataTypes.createStructField("date",DataTypes.StringType,true),
      DataTypes.createStructField("user_id",DataTypes.LongType,true),
      DataTypes.createStructField("session_id",DataTypes.StringType,true),
      DataTypes.createStructField("page_id",DataTypes.LongType,true),
      DataTypes.createStructField("action_time",DataTypes.StringType,true),
      DataTypes.createStructField("search_keyword",DataTypes.StringType,true),
      DataTypes.createStructField("click_category_id",DataTypes.LongType,true),
      DataTypes.createStructField("click_product_id",DataTypes.LongType,true),
      DataTypes.createStructField("order_category_ids",DataTypes.StringType,true),
      DataTypes.createStructField("order_product_ids",DataTypes.StringType,true),
      DataTypes.createStructField("pay_category_ids",DataTypes.StringType,true),
      DataTypes.createStructField("pay_product_ids",DataTypes.StringType,true)
    ))
    val  df = sqlContext.createDataFrame(rowsRDD,schema)
    df.registerTempTable("user_vist_action");
    df.printSchema()
    df.select("date").show(10)
    /*for(row <- df.take(1)){
      println(row)
    }*/
    //println(df.take(1))

    /**
      * 构造user_info表中的数据
      */
    rows.clear()
    val sexes = Array("male","female")
    for (i <- 0 until(100)){
      val  userid  = i.asInstanceOf[Object]
      val username = "user"+i
      val name  = "name" + i
      val  age  = random.nextInt(60).asInstanceOf[Object]
      val  professional = "professional"+random.nextInt(100)
      val city = "city"+random.nextInt(100)
      val sex = sexes(random.nextInt(2))
      val row = RowFactory.create(userid,username,name,age,professional,city,sex)
      rows.add(row)
    }
    rowsRDD = sc.parallelize(rows.asScala.toSeq)
    val schema2 = DataTypes.createStructType(util.Arrays.asList(
      DataTypes.createStructField("user_id",DataTypes.LongType,true),
      DataTypes.createStructField("username",DataTypes.StringType,true),
      DataTypes.createStructField("name",DataTypes.StringType,true),
      DataTypes.createStructField("age",DataTypes.IntegerType,true),
      DataTypes.createStructField("professional",DataTypes.StringType,true),
      DataTypes.createStructField("city",DataTypes.StringType,true),
      DataTypes.createStructField("sex",DataTypes.StringType,true)
    ))
    val df2 = sqlContext.createDataFrame(rowsRDD,schema2)

    /*for(row <- df2.take(1)){
      println(row)
    }*/
    //println(df2.take(1))
    df2.registerTempTable("user_info")
    df2.show(10)
    df2.printSchema()


  }


}
