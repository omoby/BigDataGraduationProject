package graduation.scala.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * FileName: UserActiveDegreeAnalyze
  * Author:   hadoop
  * Email:    3165845957@qq.com
  * Date:     19-8-4 下午11:31
  * Description:
  *
  * 用户活跃度分析
  *
  * 我们这次项目课程的升级，也跟spark从入门到精通的升级采取同步，采用scala+eclipse的方式来开发
  *
  * 我个人而言，还是觉得应该用java去开发spark作业，因为hadoop是最重要的大数据引擎，hadoop mapreduce、hbase，全都是java
  * 整个公司的编程语言技术栈越简单越好，降低人员的招聘和培养的成本
  *
  * 但是由于市面上，现在大部分的公司，做spark都是采取一种，spark用scala开发，所以开发spark作业也用scala
  * 课程为了跟市场保持同步，后面就随便采取scala来开发了
  *
  */
object UserActiveDegreeAnalyze {

  case class UserActionLog(logId: Long, userId: Long, actionTime: String, actionType: Long, purchaseMoney: Double)
  case class UserActionLogVO(logId: Long, userId: Long, actionValue: Long)
  case class UserActionLogWithPurchaseMoneyVO(logId: Long, userId: Long, purchaseMoney: Double)

  def main(args: Array[String]) {
    // 如果是按照课程之前的模块，或者整套交互式分析系统的架构，应该先从mysql中提取用户指定的参数（java web系统提供界面供用户选择，然后java web系统将参数写入mysql中）
    // 但是这里已经讲了，之前的环境已经没有了，所以本次升级从简
    // 我们就直接定义一个日期范围，来模拟获取了参数
    val startDate = "2019-08-01";
    val endDate = "2019-09-01";

    // 开始写代码
    // spark 2.0具体开发的细节和讲解，全部在从入门到精通中，这里不多说了，直接写代码
    // 要不然如果没有看过从入门到精通的话，就自己去上网查spark 2.0的入门资料
    val conf  = new SparkConf().setAppName("UserActiveDegreeAnalyze").setMaster("local[2]")
    /*val spark = SparkSession
      .builder()
      .appName("UserActiveDegreeAnalyze")
      .master("local")
      .config("spark.sql.warehouse.dir", "D:\\test\\spark\\mall\\spark-warehouse")
      .getOrCreate()*/
    val  spark = SparkSession.builder().config(conf).getOrCreate()
    val sc = spark.sparkContext
    // 导入spark的隐式转换
    import spark.implicits._
    // 导入spark sql的functions
    import org.apache.spark.sql.functions._

    val  dataPath:String ="/home/hadoop/IdeaProjects/BigDataGraduationProject/log/"
    // 获取两份数据集
    val userBaseInfo = spark.read.json(dataPath+"user_base_info.json")
    val userActionLog = spark.read.json(dataPath+"user_action_log.json")

    // 第一个功能：统计指定时间范围内的访问次数最多的10个用户
    // 说明：数据不会搞的太多，但是一般来说，pm产品经理，都会抽取100个~1000个用户，供他们仔细分析
    //{"logId":5518,"userId":28,"actionTime":"2019-08-05 23:22:39","actionType":1,"purchaseMoney":693.76}
    //{"userId":36,"userName":"userName36","registTime":"2019-08-05 23:22:38"}
    userActionLog
        // 第一步：过滤数据，找到指定时间范围内的数据
        .filter("actionTime >= '" + startDate + "' and actionTime <= '" + endDate + "' and actionType = 0")
        // 第二步：关联对应的用户基本信息数据
        .join(userBaseInfo, userActionLog("userId") === userBaseInfo("userId"))
        // 第三部：进行分组，按照userid和username
        .groupBy(userBaseInfo("userId"), userBaseInfo("username"))
        // 第四步：进行聚合
        .agg(count(userActionLog("logId")).alias("actionCount"))
        // 第五步：进行排序
        .sort($"actionCount".desc)
        // 第六步：抽取指定的条数
        .limit(10)
        // 第七步：展示结果，因为监简化了，所以说不会再写入mysql
        .show()



    // 第二个功能：获取指定时间范围内购买金额最多的10个用户
    // 对金额进行处理的函数讲解
    // feature，技术点的讲解：嵌套函数的使用
    //{"logId":5518,"userId":28,"actionTime":"2019-08-05 23:22:39","actionType":1,"purchaseMoney":693.76}
    //{"userId":36,"userName":"userName36","registTime":"2019-08-05 23:22:38"}

    userActionLog
      // 第一步：过滤数据，找到指定时间范围内的数据
        .filter("actionTime >= '" + startDate + "' and actionTime <= '" + endDate + "' and actionType = 1")
      // 第二步：关联对应的用户基本信息数据
        .join(userBaseInfo, userActionLog("userId") === userBaseInfo("userId"))
      // 第三部：进行分组，按照userid和username
        .groupBy(userBaseInfo("userId"), userBaseInfo("username"))
      // 第四步：根据消费金额进行聚合
        .agg(round(sum(userActionLog("purchaseMoney")),2).alias("totalPurchaseMoney"))
      //第五步：根据消费金额降序排序
        .sort($"totalPurchaseMoney".desc)
      //第六步：抽取前10条数据
        .limit(10)
      //第七步：展示数据
        .show()



    // 第三个功能：统计最近一个周期相对上一个周期访问次数增长最多的10个用户
    // 比如说我们设定一个周期是1个月
    // 我们有1个用户，叫张三，那么张三在9月份这个周期内总共访问了100次，张三在10月份这个周期内总共访问了200次
    // 张三这个用户在最近一个周期相比上一个周期，访问次数增长了100次
    // 每个用户都可以计算出这么一个值
    // 获取在最近两个周期内，访问次数增长最多的10个用户

    // 周期，是可以由用户在web界面上填写的，java web系统会写入mysql，我们可以去获取本次执行的周期
    // 假定1个月，2019-10-01~2019-10-31，上一个周期就是2019-09-01~2019-09-30

        val userActionLogInFirstPeriod = userActionLog.as[UserActionLog]
            .filter("actionTime >= '2016-10-01' and actionTime <= '2016-10-31' and actionType = 0")
            .map{ userActionLogEntry => UserActionLogVO(userActionLogEntry.logId, userActionLogEntry.userId, 1) }

        val userActionLogInSecondPeriod = userActionLog.as[UserActionLog]
            .filter("actionTime >= '2016-01-01' and actionTime <= '2016-09-30' and actionType = 0")
            .map{ userActionLogEntry => UserActionLogVO(userActionLogEntry.logId, userActionLogEntry.userId, -1) }

        val userActionLogDS = userActionLogInFirstPeriod.union(userActionLogInSecondPeriod)

        userActionLogDS
            .join(userBaseInfo, userActionLogDS("userId") === userBaseInfo("userId"))
            .groupBy(userBaseInfo("userId"), userBaseInfo("username"))
            .agg(sum(userActionLogDS("actionValue")).alias("actionIncr"))
            .sort($"actionIncr".desc)
            .limit(10)
            .show()



    //第四个功能：统计最近一个周期相对上一个周期消费金额增长最多的10个用户
        val userActionLogWithPurchaseMoneyInFirstPeriod = userActionLog.as[UserActionLog]
            .filter("actionTime >= '2019-10-01' and actionTime <= '2019-10-31' and actionType = 1")
            .map{ userActionLogEntry =>
              UserActionLogWithPurchaseMoneyVO(userActionLogEntry.logId, userActionLogEntry.userId,
                userActionLogEntry.purchaseMoney) }

        val userActionLogWithPurchaseMoneyInSecondPeriod = userActionLog.as[UserActionLog]
            .filter("actionTime >= '2019-09-01' and actionTime <= '2019-09-30' and actionType = 1")
            .map{ userActionLogEntry =>
              UserActionLogWithPurchaseMoneyVO(userActionLogEntry.logId, userActionLogEntry.userId,
                -userActionLogEntry.purchaseMoney) }

        val userActionLogWithPurchaseMoneyDS = userActionLogWithPurchaseMoneyInFirstPeriod.union(userActionLogWithPurchaseMoneyInSecondPeriod)

        userActionLogWithPurchaseMoneyDS
            .join(userBaseInfo, userActionLogWithPurchaseMoneyDS("userId") === userBaseInfo("userId"))
            .groupBy(userBaseInfo("userId"), userBaseInfo("username"))
            .agg(round(sum(userActionLogWithPurchaseMoneyDS("purchaseMoney")), 2).alias("purchaseMoneyIncr"))
            .sort($"purchaseMoneyIncr".desc)
            .limit(10)
            .show()

    // 第五个功能：统计指定注册时间范围内头7天访问次数最高的10个用户
    // 举例，用户通过web界面指定的注册范围是2016-10-01~2016-10-31

      userActionLog
        .join(userBaseInfo, userActionLog("userId") === userBaseInfo("userId"))
        .filter(userBaseInfo("registTime") >= "2019-10-01"
          && userBaseInfo("registTime") <= "2019-10-31"
          && userActionLog("actionTime") >= userBaseInfo("registTime")
          && userActionLog("actionTime") <= date_add(userBaseInfo("registTime"), 7)
          && userActionLog("actionType") === 0)
        .groupBy(userBaseInfo("userId"), userBaseInfo("username"))
        .agg(count(userActionLog("logId")).alias("actionCount"))
        .sort($"actionCount".desc)
        .limit(10)
        .show()

    // 第六个功能：统计指定注册时间范围内头7天访问消费金额最高的10个用户
    userActionLog
      .join(userBaseInfo, userActionLog("userId") === userBaseInfo("userId"))
      .filter(userBaseInfo("registTime") >= "2016-10-01"
        && userBaseInfo("registTime") <= "2016-10-31"
        && userActionLog("actionTime") >= userBaseInfo("registTime")
        && userActionLog("actionTime") <= date_add(userBaseInfo("registTime"), 7)
        && userActionLog("actionType") === 1)
      .groupBy(userBaseInfo("userId"), userBaseInfo("username"))
      .agg(round(sum(userActionLog("purchaseMoney")),2).alias("purchaseMoneyTotal"))
      .sort($"purchaseMoneyTotal".desc)
      .limit(10)
      .show()

    // 第七个功能：统计指定注册时间范围内头7天访问次数最高的10个用户
    // 举例，用户通过web界面指定的注册范围是2019-10-01~2019-10-31
    //{"logId":5518,"userId":28,"actionTime":"2019-08-05 23:22:39","actionType":1,"purchaseMoney":693.76}
    //{"userId":36,"userName":"userName36","registTime":"2019-08-05 23:22:38"}
    val startDateReg = "2019-10-01"
    val endDateReg = "2019-10-31"
    userBaseInfo.filter(s"registTime >= '$startDateReg' and registTime <= '$endDateReg'")
      .join(userActionLog,userActionLog("userId")===userBaseInfo("userId"))
      .filter(userActionLog("actionTime") >= userBaseInfo("registTime")
        &&  userActionLog("actionTime") <= date_add(userBaseInfo("registTime"),7)
        && userActionLog("actionType") === 1)
      .groupBy(userBaseInfo("userId"),userBaseInfo("username"))
      .agg(count(userActionLog("logId")).alias("countLogId"))
      .sort($"countLogId".desc)
      .limit(10)
      .show()
    sc.stop()
    spark.stop()

  }

}
