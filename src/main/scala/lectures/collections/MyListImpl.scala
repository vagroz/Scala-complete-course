package lectures.collections

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

  case class MyList(data: List[Int]) {

    def flatMap(f: (Int => MyList)) =
      MyList(data.flatMap(inp => f(inp).data))

    /** я вообще думал, что flatMap следует
      * реализовывать через map (ведь map может позволять
      * создавать List[ List[Int] ]. Но если надо наоборот,
      * то на ум приходит только один вариант: (Int)=>Int
      */
    def map(f: (Int => Int)) =
      flatMap(inp => MyList(List(f(inp))))

    def foldLeft(acc: Int)(op: (Int,Int) => Int): Int = data match{
      case Nil => acc
      case head :: tail => MyList(tail).foldLeft(op(acc,head))(op)
    }

    def filter(f: (Int) => Boolean) =
      flatMap{ inp => MyList(
          if (f(inp)) List(inp)
          else List.empty[Int]
        )
      }
  }

  require(MyList(List(1, 2, 3, 4, 5, 6)).map(_ * 2).data == List(2, 4, 6, 8, 10, 12))
  require(MyList(List(1, 2, 3, 4, 5, 6)).filter(_ % 2 == 0).data == List(2, 4, 6))
  require(MyList(List(1, 2, 3, 4, 5, 6)).foldLeft(0)((x,y) => x + y) == 21)
  require(MyList(Nil).foldLeft(0)((x,y) => x + y) == 0)

}