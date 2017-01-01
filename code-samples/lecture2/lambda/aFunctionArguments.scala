package com.tikal.workshop

object Hello {
  
  def functionWithOneArg(x : Int) : Int = {
    x + 1
  }
  
  def functionWithTwoArgs(x : Int, y : Int) = {
    x + y + 1
  }
  
  def highOrderFunction(x : Int, f : (Int) => Int )={ //, 
                       //f2 : (Int, Int) => Int   ) = {
    x + f(x);// + f2(3, 4);
  }
  
  
  def main(args : Array[String]) : Unit = {
    
    val a = highOrderFunction(1, functionWithOneArg) ;  // 1 + functionWithOneArg(1)
    //val b = highOrderFunction(1, functionWithTwoArgs) ;  // compilation error
    val c = highOrderFunction (1, a => a+1 ) ;  // 1 + 1*1
    println(highOrderFunction(1, _ + 1));  // f is x =>  x + 1
  }

}