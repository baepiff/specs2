package org.specs2
package specification

import execute.AsResult
import control.Functions._

/**
 * This trait defines methods for creating Examples.
 *
 * You can subclass it and implement the `newExample(e: Example)` method to provide alternative behavior.
 *
 * The new factory can be passed to a Specification by overriding the `ExamplesFactory.exampleFactory` method
 */
trait ExampleFactory {
  /** @return an Example, using a function taking the example description as an input */
  def newExample[T : AsResult](s: String, function: String => T): Example = newExample(s, function(s))
  /** @return an Example, using anything that can be translated to a Result, e.g. a Boolean */
	def newExample[T : AsResult](s: String, t: =>T): Example = newExample(Example(s, t))
  /** @return an Example, using anything that can be translated to a Result, e.g. a Boolean */
  def newExample[T : AsResult](s: FormattedString, t: =>T): Example = newExample(Example(s, t))
  /** @return an Example, using anything that can be translated to a Result, e.g. a Boolean */
	def newExample(e: Example): Example
}

/**
 * Default implementation for the example factory trait just creating an Example object
 */
class DefaultExampleFactory extends ExampleFactory {
  def newExample(e: Example): Example = e
}

/**
 * Decorated creation where the body of the example can be surrounded with a given context if necessary
 * @see mutable.BeforeExample
 */
class DecoratedExampleFactory(factory: =>ExampleFactory, function: Context) extends ExampleFactory {
  override def newExample(e: Example) = factory.newExample(e.copy(body = () => function(e.body())))
}
