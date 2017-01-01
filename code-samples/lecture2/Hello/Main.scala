package com.tikal.workshop

import scala.collection.immutable.NumericRange
import scala.collection.mutable.Stack

object Main {
  def main(args : Array[String]) : Unit = {
    
//    var i : Int = 0 ;
//    while (i < args.length) {
//      val m : Message = new HelloMessage(args(i)) ;
//      m.display();
//      i = i + 1;
//    }
    
    val r : Range = 1 to 4 ;
    val r1 =  NumericRange(1, 4, 1) ;
    for (i <- (1 to 4)) {
      val m : Message = new HelloMessage(args(i-1));
      m.display();
    }
  }
}