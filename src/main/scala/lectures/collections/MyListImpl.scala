package lectures.collections

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Представим, что по какой-то причине Вам понадобилась своя обертка над списком целых чисел List[Int]
  *
  * Вы приняли решение, что будет достаточно реализовать 4 метода:
  * * * * * def flatMap(f: (Int => MyList)) -  реализуете на основе соответствующего метода из List
  * * * * * метод map(f: (Int) => Int) - с помощью только что полученного метода flatMap класса MyList
  * * * * * filter(???) - через метод flatMap класса MyList
  * * * * * foldLeft(acc: Int)(???) - через декомпозицию на head и tail
  *
  * Для того, чтобы выполнить задание:
  * * * * * раскомментируйте код
  * * * * * замените знаки вопроса на сигнатуры и тела методов
  * * * * * не используйте var и мутабильные коллекции
  *
  */
object MyListImpl extends App {

  case class MyList[T, M[T]](data: Seq[T]) {

    def flatMap(f: (T => MyList[T, Seq])): MyList[T, Seq] = {
      val newData = data.flatMap(elem => f(elem).data)
      MyList(newData)
    }

    def map(f: (T => T)): MyList[T, Seq] =
      flatMap(inp => MyList(List(f(inp))))

    def foldLeft(acc: T)(op: (T,T) => T): T = data match{
      case Nil => acc
      case head :: tail => MyList(tail).foldLeft(op(acc,head))(op)
    }

    def filter(f: (T) => Boolean) =
      flatMap{ inp => MyList(
          if (f(inp)) List(inp)
          else List.empty[T]
        )
      }
  }

  require(MyList(List(1, 2, 3, 4, 5, 6)).map(_ * 2).data == List(2, 4, 6, 8, 10, 12))
  require(MyList(List(1, 2, 3, 4, 5, 6)).filter(_ % 2 == 0).data == List(2, 4, 6))
  require(MyList(List(1, 2, 3, 4, 5, 6)).foldLeft(0)((x,y) => x + y) == 21)
  require(MyList(Nil).foldLeft(0)((x,y) => x + y) == 0)

  //Task11:

  require(MyList[Int, List](List(1, 2, 3, 4, 5, 6)).map(p => p * 2).data == List(2, 4, 6, 8, 10, 12))
  require(MyList[Long, ListBuffer](ListBuffer(1, 2, 3, 4, 5, 6)).filter(_ % 2 == 0).data == List(2, 4, 6))
  require(MyList[Int, List](List(1, 2, 3, 4, 5, 6)).foldLeft(0)((a,b) => a + b) == 21)
  require(MyList[Float, IndexedSeq](ArrayBuffer.empty[Float]).foldLeft(0)((a,b) => a + b) == 0)

}