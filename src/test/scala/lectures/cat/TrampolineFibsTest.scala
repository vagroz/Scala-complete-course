package lectures.cat
import org.scalatest.{FlatSpec, Matchers}
import lectures.functions.Fibonacci2

import scala.annotation.tailrec
import scala.util.control.TailCalls.{TailRec, done, tailcall}

/**
  * Реализуйте функцию вычисления чисел фибоначи
  * с помощью трамлина
  *
  * Напишите тест который оценит на сколько трамлины менее
  * производительны, чем классическое динамическое программирование
  *
  */
object TrampoilineFibs {

  def fib(n: Int): Int = {

    step(0,1,1,n).result._2
  }

  private def step(a: Int, b: Int, cnt: Int, total: Int): TailRec[(Int, Int)] = {
    if (cnt == total) done((a, b))
    else tailcall(step(b, a+b, cnt+1, total))
  }

}

object DPFib {

  @tailrec
  private def step(a: Int, b: Int, cnt: Int, total: Int): Int = {
    if (cnt == total) b
    else step(b, a + b, cnt+1, total)
  }

  def fib(n: Int) = {
    step(0, 1, 1, n)
  }

}


class TrampolineFibsTest extends FlatSpec with Matchers {

  "TrampolineFibs" should "calculate correctly" in {
    TrampoilineFibs.fib(2) shouldBe 1
    TrampoilineFibs.fib(22) shouldBe 17711
  }

  "Evaluated run time" should "be more in trampoline" in {
    val startDP = System.currentTimeMillis()
    val f1 = DPFib.fib(100500)
    val totalDP = System.currentTimeMillis() - startDP

    val startTrampoline = System.currentTimeMillis()
    val f2 = TrampoilineFibs.fib(100500)
    val totalTrampoline = System.currentTimeMillis() - startTrampoline

    println(s"Total time, DP: $totalDP   trampoline: $totalTrampoline")

    f1 shouldEqual f2
    totalDP should be < totalTrampoline // 4 vs 63
  }


}
