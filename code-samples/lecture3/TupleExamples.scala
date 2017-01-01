package com.tikal.workshop

object TupleExamples {
  def main(args: Array[String]): Unit = {

    val myData : Tuple3[Int, Double, String] = (7051, 31.4, "01-06-2016");

    val station1 = myData._1;
    val temperature1 = myData _2;
    val date1 = myData._3;

    // this is cool:
    val (_, temperature, date) = myData;

    println(s"$date1 : $temperature1");
    println(s"$date : $temperature");
    println(myData);

/*    
    
    
    
    
    
    
    def moreExamples(): Map[String, Double] = {
      val y = ("03-06-2016", 32.4);
      val x = "02-06-2016" -> 32.4;
      val x2 = "02-06-2016" -> 32.5;
      val myMap = Map("01-06-2016" -> 29.1, x, x2, y);
      myMap
    }

    
    
    // more examples: a map is a collection of key/value pairs, which are actually tuples
    val myKeyValueMap = moreExamples()
    
    
    // loop over the map (the Scala way):
    for ((k, v) <- myKeyValueMap) {
      println(s"key=$k , value=$v");
    }

    // the following way is iterating like Java:
//    for (key <- myKeyValueMap.keys) {
//      println("key=" + key + ", value=" + myKeyValueMap.getOrElse(key, ""));
//    }
     
 */
  }
}