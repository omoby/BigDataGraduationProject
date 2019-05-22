package graduation.scala.spark

/**
  * FileName: SecondarySort
  * Author:   hadoop
  * Email:    3165845957@qq.com
  * Date:     19-4-29 下午12:41
  * Description:
  *
  */
class SecondarySortByKey(val clickCount:Int,
                         val orderCount:Int,
                         val payCount:Int) extends Ordered[SecondarySortByKey] with Serializable {
  override def compare(that: SecondarySortByKey): Int =  {
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
