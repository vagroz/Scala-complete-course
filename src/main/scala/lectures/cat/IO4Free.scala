package lectures.cat

import java.util.Scanner

import cats._
import cats.free.Free
import scala.reflect.runtime.universe._

/**
  * IO очень просто реализовать с помощью Free
  * Ваше задача
  * * * * Дополнить доменную модель
  * * * * Реализовать интерпертатор
  *
  * Так, что бы IOIsFree печатало в консоль реультат запуска program
  */
sealed trait IO4Free[+R]
case class Voidd(op: () => Unit) extends IO4Free[Unit]
case class SomeType[+T](op: () => T) extends IO4Free[T]

object IO4Free {

  import cats.free.Free._

  val interpreter = new (IO4Free ~> Id) {
    override def apply[A](fa: IO4Free[A]): Id[A] = {
      fa match {
        case Voidd(op) =>
          op()
        case SomeType(op) =>
          op()
      }
    }
  }

  implicit def toIO[A: TypeTag](f: () => A): Free[IO4Free, A] = {

    val io: IO4Free[A] = f match {
      case f if typeOf[A] =:= typeOf[Unit] =>
        Voidd(f.asInstanceOf[Function0[Unit]]).asInstanceOf[IO4Free[A]]
      case f: Function0[A] =>
        SomeType[A](f)
    }
    toFree[A](io)
  }

  implicit def toFree[A](iof: IO4Free[A]): Free[IO4Free, A] = liftF(iof)

  def computationBasedOnInput(i: Int): Free[IO4Free, String] = for {
    v <- () => 10 * i
    r <- () => " and the result is "
  } yield r + v
}

object IOIsFree extends App {

  import IO4Free._

  val program = for {
    _ <- () => println("Start computation")
    v <- () => new Scanner(System.in).nextInt()
    calcResult <- computationBasedOnInput(v)
  } yield calcResult

  print(program.foldMap(interpreter))
}