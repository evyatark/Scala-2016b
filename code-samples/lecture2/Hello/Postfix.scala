package com.tikal.workshop

import scala.collection.mutable.Stack

object Postfix {
  def main(args: Array[String]): Unit = {
    val input = args(0).split(" ") ;

    //5 2 3 * +

    val s : Stack[Int] = new Stack()
    
    for (x <- input) {
      println(x);
    }
  }
}