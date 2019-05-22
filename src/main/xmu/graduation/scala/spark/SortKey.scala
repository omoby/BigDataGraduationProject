package graduation.scala.spark

/**
  * FileName: SortKey
  * Author:   hadoop
  * Email:    3165845957@qq.com
  * Date:     19-3-23 下午8:29
  * Description:
  * Scala实现品类的二次排序
  */
class SortKey(val clickCount:Int,
              val orderCount:Int,
              val payCount:Int) extends Ordered[SortKey] with Serializable {

   def compare(that: SortKey): Int = {
    if (clickCount - that.clickCount != 0){
      clickCount - that.clickCount
    } else if(orderCount - that.orderCount != 0){
      orderCount - that.orderCount
    } else if (payCount - that.payCount != 0){
      payCount - that.payCount
    } else{
      0
    }
  }

}
