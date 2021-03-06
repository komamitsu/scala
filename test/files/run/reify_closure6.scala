import scala.reflect.mirror._

object Test extends App {
  var q = 0
  def foo[T: TypeTag](ys: List[T]): Int => Int = {
    val z = 1
    var y = 0
    val fun = reify{(x: Int) => {
      y += 1
      q += 1
      println("q = " + q)
      println("y = " + y)
      x + ys.length * z + q + y
    }}

    val toolbox = mkToolBox()
    val dyn = toolbox.runExpr(fun.tree)
    dyn.asInstanceOf[Int => Int]
  }

  val fun1 = foo(List(1, 2, 3))
  println("first invocation = " + fun1(10))
  val fun2 = foo(List(1, 2, 3, 4))
  println("second invocation = " + fun2(10))
  println("q after second invocation = " + q)
}