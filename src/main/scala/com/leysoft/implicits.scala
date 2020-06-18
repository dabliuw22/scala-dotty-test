package com.leysoft

import scala.annotation.Annotation
 
trait Ord[T]:
  def compare(a: T, b: T): Int

given intOrd as Ord[Int]: 
  def compare(a: Int, b: Int): Int = if a < b then -1 else 1

given Ord[String]:
  def compare(a: String, b: String): Int = if a.length < b.length then -1 else 1

// given listOrd[T](using: Ord[T])  as Ord[List[T]]:
given listOrd [T: Ord] as Ord[List[T]]:
  def compare(a: List[T], b: List[T]): Int = if a.size < b.size then -1 else 1

// def compare[T](a: T, b: T)(using ord: Ord[T]): Int = ord.compare(a, b) // with implicit
def compare[T](a: T, b: T): Ord[T] ?=> Int = (using ord: Ord[T]) => ord.compare(a, b) // With Context Functions

object implicits extends App: 
  println(compare(5, 3))