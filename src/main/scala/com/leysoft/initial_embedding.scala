package com.leysoft

/** 
 * Represents the AST (abstract syntax tree) of the object language.
 * Is the language of arithmetric expressions with integers, 
 * addition and negation.
 */
enum Expression:
  case Literal(x: Int)
  case Negative(e: Expression)
  case Addition(a: Expression, b: Expression)

import Expression._

/**
 * Standard interpreter: Evaluator proceeding by case analysis. 
 */
private def eval(e: Expression): Int = e match
  case Literal(x) => x
  case Negative(e) => - eval(e)
  case Addition(a, b) => eval(a) + eval(b)

/**
 * Non-Standard interpreter
 */
private def view(e: Expression): String = e match 
  case Literal(x) => x.toString
  case Negative(e) => s"(-${view(e)})"
  case Addition(a, b) => s"(${view(a)} + ${view(b)})"

object initial_embedding extends App {
  val exp = Addition(Literal(22), Negative(Addition(Literal(1), Literal(1))))
  println(eval(exp))
  println(view(exp))
}