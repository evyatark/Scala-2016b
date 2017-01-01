package com.tikal.workshop

object PatternMatchExamples {
  def main(args : Array[String]) : Unit = {

    var list = 2 :: 3 :: Nil
    list map  toYesOrNo  // list.map(toYesOrNo)
    list = 1 :: list ;  // list.::(1)
    
    println(list)
    
    //print(length(list))
  }
  
  def toYesOrNo(choice: Int): String = {
    choice match {
      case 1 => "yes"
      case 0 => "no"
      case _ => "error"
    }
  }

  def toYesOrNo2(choice: Int): String = {
    choice match {
      case 1 | 2 | 3 => { println("side effect") ; "yes" }
      case 0         => "no"
      case _         => "error"
    }
  }

  def f(x: Any): String = {
    x match {
      case _: Double => "a double"
      case j:Int => s"integer $j"
      case i: Int  if (i < 2)  => "integer less than 2: " + i
      case s: String => "I want to say " + s
      case _ : Boolean => "b"
      case _ => "qqq"
    }
  }

  
  
  
  
  
  
  def fact(n: Int): Int = {
    n match {
      case 0 => 1
      case x if (x < 0) => -1
      case x => x * fact(x - 1)
    }
  }

  
  
  
  
  
  
  
  
  
  
  
  
  
  def length[A](list: List[A]): Int = {
    
    list match {
      case Nil       => 0
      case a :: y =>         1 + length(y)
    }
  }
}







  // copied from https://kerflyn.wordpress.com/2011/02/14/playing-with-scalas-pattern-matching/

