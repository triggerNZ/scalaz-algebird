# scalaz-algebird

Conversions between the (isomporphic) category theory typeclasses of scalaz and algebird.

## Links

- https://github.com/scalaz/scalaz
- https://github.com/twitter/algebird

## Conversions implemented
- Monoid
- Functor
- Applicative
- Monad

## Usage example

Assuming we have an algebird monoid and we want to convert to scalaz:

```scala
import commbank.util._
import AlgebirdToScalaz._

import scalaz.{Monoid => SMonoid, Scalaz}, Scalaz._
import com.twitter.algebird.{Monoid => AMonoid, Max}

import com.twitter.algebird.Operators._

Max(10) |+| Max(30)

```

Vice-versa we use the `ScalazToAlgebird` object instead.

### Pull requests welcome!!!

