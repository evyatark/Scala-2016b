package com.tikal.workshop1


abstract sealed class Exp

case class Number(n: Int) extends Exp
case class Sum(exp1: Exp, exp2: Exp) extends Exp
case class Product(exp1: Exp, exp2: Exp) extends Exp

object CaseClassPatternMatchingExample {
  
  
//  def print(e: Exp): String = e match {
//    case Number(x)       => 
//    case Sum(e1, e2)     => 
//    case Product(e1, e2) => 
//  }
//
//  def calculate(e: Exp): Int = e match {
//    case 
//    case 
//    case 
//  }
  
  
  def main(args : Array[String]) : Unit = {
    //val input = "4 2 + 3 *"
    //val x : Exp = read(input)
    val x = Product(Sum(Number(4), Number(2)), Number(3)) ;
    println(print(x)) ; 
    //println("calculated value=" + calculate(x)) ;
    
  }
}