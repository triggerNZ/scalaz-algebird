package commbank.util

import com.twitter.algebird.{
  Functor => AFunctor,
  Applicative => AApplicative,
  Monad => AMonad,
  Monoid => AMonoid
}

import scalaz.{
  Functor => SFunctor,
  Applicative => SApplicative,
  Monad => SMonad,
  Monoid => SMonoid
}

object AlgebirdToScalaz {
  implicit def algebirdFunctorToScalazFunctor[F[_]](implicit af: AFunctor[F]) = new SFunctor[F] {
    override def map[A, B](fa: F[A])(f: (A) => B): F[B] = af.map(fa)(f)
  }

  implicit def algebirdApplicativeToScalazApplicative[M[_]](implicit aa: AApplicative[M]) = new SApplicative[M] {
    override def point[A](a: => A): M[A] = aa.apply(a)
    override def ap[A, B](fa: => M[A])(f: => M[(A) => B]): M[B] = {
      //is this the most efficient???
      val joined: M[(A, A => B)] = aa.join(fa, f)
      aa.map(joined) { case (a, f) => f(a) }
    }
  }

  implicit def algebirdMonadToScalazMonad[M[_]](implicit am: AMonad[M]): SMonad[M] = new SMonad[M] {
    def bind[A, B](fa: M[A])(f: (A) => M[B]) = am.flatMap(fa)(f)
    def point[A](a: => A) = am.apply(a)
  }

  implicit def algebirdMonoidToScalazMonoid[M](implicit am: AMonoid[M]): SMonoid[M] = new SMonoid[M] {
    override def zero: M = am.zero
    override def append(f1: M, f2: => M): M = am.plus(f1, f2)
  }
}

object ScalazToAlgebird {
  implicit def scalazFunctorToAlgebirdFunctor[F[_]](implicit sf: SFunctor[F]) = new AFunctor[F] {
    override def map[T, U](m: F[T])(fn: (T) => U): F[U] = sf.map(m)(fn)
  }

  implicit def scalazApplicativeToAlebirdApplicative[M[_]](implicit sa: SApplicative[M]) = new AApplicative[M] {
    override def apply[T](v: T): M[T] = sa.point(v)

    override def join[T, U](mt: M[T], mu: M[U]): M[(T, U)] =
      sa.apply2(mt, mu) { (t, u) => (t, u)}

    override def map[T, U](m: M[T])(fn: (T) => U): M[U] = sa.map(m)(fn)
  }

  implicit def scalazMonadToAlgebirdMonad[M[_]](implicit sm: SMonad[M]) = new AMonad[M] {
    override def flatMap[T, U](m: M[T])(fn: (T) => M[U]): M[U] = sm.bind(m)(fn)
    override def apply[T](v: T): M[T] = sm.point(v)
  }


  implicit def scalazMonoidToAlgebirdMonoid[M](implicit sm: SMonoid[M]): AMonoid[M] = new AMonoid[M] {
    def zero = sm.zero
    def plus(l: M, r: M) = sm.append(l, r)
  }

}

