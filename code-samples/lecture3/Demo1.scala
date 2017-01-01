package com.tikal.work

object Demo1 {
  
  def main(args: Array[String]): Unit = {
    def f1() : (String, Double) = {
      ("03-01-2016", 37.2)
    }
    
    val x : (String, Double, Int) = ("01-01-2016", 7.3, 10) ;
    val y = f1();
    
    //println(y);
    val arl : java.util.ArrayList[String] = new java.util.ArrayList();
    arl.add("qq") ;
    
    val (a, b, c) = x ;
    
    println(x._1)
    println(x._2)
    
    
    val myMap2  = Map(   
                ("a" -> 1),
                
                ("b", 2)
                
    )
    println(myMap2.getClass.getName);

    for ((a,b) <- myMap2) {
      
    }
  }
}