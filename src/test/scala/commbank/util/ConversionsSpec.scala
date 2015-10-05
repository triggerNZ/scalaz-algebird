package commbank.util

import org.specs2.Specification

object ConversionsSpec extends Specification {
  def is =
    s2"""
       Conversion from algebird to scalaz
         Monoids  $a2sMonoid
         Functors $a2sFunctor

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

  def a2sFunctor = {
    import com.twitter.algebird.{Functor => AFunctor}
    import scalaz.{Functor => SFunctor}
    import AlgebirdToScalaz._

    val listAFunctor = new AFunctor[List] {
      override def map[T, U](m: List[T])(fn: (T) => U): List[U] = m.map(fn)
    }
    val listSFunctor = implicitly[SFunctor[List]]

    listSFunctor(List(1, 2, 3))(_ * 2) must_=== List(2, 4, 6)
  }
}
