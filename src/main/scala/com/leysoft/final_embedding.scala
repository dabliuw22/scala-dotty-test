package com.leysoft

/**
 * no representamos la expresión por un AST, ADT, sino que la expresamos 
 * por su valor o una expresión que computa ese valor. 
 */


/**
 * Represents the class of all algebraic expression programs with integer 
 * literals, addition and negation for any semantic domain -> (Repr).
 * TypeClass is a Syntax
 */
trait ExpresionSym[Repr]: 
  def literal(x: Int): Repr
  def negative(e: Repr): Repr
  def addition(a: Repr, b: Repr): Repr

object ExpresionSym:
  inline def apply[Repr](using sym: ExpresionSym[Repr]): ExpresionSym[Repr] = sym

  // TypeClass instance is a Semantic
  given ExpresionSym[Int]: 
    def literal(x: Int): Int = x
    def negative(e: Int): Int = - e
    def addition(a: Int, b: Int): Int = a + b

  given ExpresionSym[String]: 
    def literal(x: Int): String = x.toString
    def negative(e: String): String = s"(-$e)"
    def addition(a: String, b: String): String = s"($a + $b)"

trait MultSym[Repr]:
  def mult(a: Repr, b: Repr): Repr

object MultSym:
  inline def apply[Repr](using sym: MultSym[Repr]): MultSym[Repr] = sym

  given MultSym[Int]:
    def mult(a: Int, b: Int): Int = a * b

  given MultSym[String]:
    def mult(a: String, b: String): String = s"($a * $b)"

trait DivSym[Repr]:
  def division(a: Repr, b: Repr): Repr

object DivSym:
  inline def apply[Repr](using sym: DivSym[Repr]): DivSym[Repr] = sym

  given DivSym[Int]:
    def division(a: Int, b: Int): Int = a / b

  given DivSym[String]:
    def division(a: String, b: String): String = s"($a / $b)"

object syntax:
  inline def literal[Repr](x: Int)(using sym: ExpresionSym[Repr]) =  sym.literal(x)
  inline def negative[Repr](e: Repr)(using sym: ExpresionSym[Repr]) = sym.negative(e)
  inline def addition[Repr](a: Repr, b: Repr)(using sym: ExpresionSym[Repr]) = sym.addition(a, b)
  inline def mult[Repr](a: Repr, b: Repr)(using sym: MultSym[Repr]) = sym.mult(a, b)
  inline def division[Repr: DivSym](a: Repr, b: Repr) = DivSym[Repr].division(a, b)

import syntax._

import scala.util.Try

/**
 * ExpresionSym is the denotational semantics over the semantic domain Repr, the meaning of
 * an expression is computed from the meaning of the components, regardless Repr.
 */
type P[Repr] = ExpresionSym[Repr] ?=> Repr
def exp[Repr]: P[Repr] = addition(literal(22), negative(addition(literal(1), literal(1))))

// Extensibility
type Q[Repr] = MultSym[Repr] ?=> P[Repr]
def mul[Repr](a: Repr, b: Repr): Q[Repr] = mult(a, b)

type R[Repr] = DivSym[Repr] ?=> Q[Repr]
def div[Repr](a: Repr, b: Repr): R[Repr] = division(a, b)
//

private def eval(exp: Int): Int = exp

private def view(exp: String): String = exp

object final_embedding extends App {
  println(eval(exp[Int]))
  println(view(exp[String]))
  println(eval(mul(literal[Int](1), negative(literal[Int](2)))))
  println(view(mul(literal[String](1), addition[String](literal(2), negative(literal(4))))))
  Try { eval(div(mul(literal[Int](1), negative(literal[Int](2))), literal[Int](0))) }
  println(view(div(mul(literal[String](1), negative(literal[String](2))), literal[String](2))))
}
