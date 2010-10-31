package org.specs2
package control

/** 
 * This trait provides simple a way to print out any object to the console:
 * 
 *  "this string".pp must_== "this string"
 *  
 *  will print 'this string' and pass it to the rest of the expectation
 *
 */
private[specs2] 
trait Debug {
  
  implicit def debug[T](t: =>T) = new { 
    def pp: T = {
      Console.println(t)
      t 
    } 
  } 
}
private[specs2] 
object Debug extends Debug