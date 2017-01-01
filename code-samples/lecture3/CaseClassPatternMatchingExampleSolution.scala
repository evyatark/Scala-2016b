package com.tikal.workshop


abstract sealed class Exp

case class Number(n: Int) extends Exp
case class Sum(exp1: Exp, exp2: Exp) extends Exp
case class Product(exp1: Exp, exp2: Exp) extends Exp

object CaseClassPatternMatchingExample {
  
  
  def print(e: Exp): String = e match {
    case Number(x)       => x.toString
    case Sum(e1, e2)     => "(+ " + print(e1) + " " + print(e2) + ")"
    case Product(e1, e2) => "(* " + print(e1) + " " + print(e2) + ")"
  }

  def calculate(e: Exp): Int = e match {
    case Number(x)       => x
    case Sum(e1, e2)     => calculate(e1) + calculate(e2)
    case Product(e1, e2) => calculate(e1) * calculate(e2)
  }
  
  
  def main(args : Array[String]) : Unit = {
    
    val x = Product(Sum(Number(4), Number(2)), Number(3)) ;
    println(print(x)) ;
    println("calculated value=" + calculate(x)) ;
    
  }
}