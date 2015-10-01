package commbank.util

import org.specs2.Specification

object ConversionsSpec extends Specification {
  def is =
    s2"""
       Conversion from algebird to scalaz
         Monoids  $a2sMonoid

       Conversion from scalaz to algebird
         Monoids  $s2aMonoid

     """


  def a2sMonoid = {
    import com.twitter.algebird.Max
    import scalaz.Scalaz._
    import com.twitter.algebird.Operators._
    import AlgebirdToScalaz._

    // This demonstrates conversion because algebird has a Max
    // monoid but scalaz does not
    Seq(
      (Max(10)  + Max(30))  must_== Max(30),
      (Max(10) |+| Max(30)) must_== Max(30)
    )
  }

  def s2aMonoid = {
    import scalaz.{Scalaz, Tags}
    import Scalaz._
    import com.twitter.algebird.Operators._

    import ScalazToAlgebird._

    val t = Tags.Disjunction(true)
    val f = Tags.Disjunction(false)


    // This demonstrates conversion because scalaz has
    // disjunction tag monoids but algebird does not
    Seq(
      (t |+| f) must_== t,
      (t  +  f) must_== t
    )
  }


}
