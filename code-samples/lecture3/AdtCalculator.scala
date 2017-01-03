package com.tikal.work3


abstract sealed class Exp

case class Number(n: Int) extends Exp
case class Sum(exp1: Exp, exp2: Exp) extends Exp
case class Product(exp1: Exp, exp2: Exp) extends Exp


object AdtCalculator {
  
  def main(args : Array[String]) : Unit = {
    
    val input = "5 3 2 + *"
    val exp : Exp = read(input)
    println(print(exp)) ;
    //println("calculated value=" + calculate(exp)) ;
    
  }
  
  def read(input  :String) : Exp = {
    Product(Number(5), Sum(Number(3), Number(2)) ) 
    // TODO - read input and produce a correct expression
  }
  
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
  
  
  
}
