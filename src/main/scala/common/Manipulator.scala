package common

import order.OrderFunction
import order.OrderValue._

import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source

class Manipulator {
  private[common] var inputFilePath: String = ""
  private[common] var outputFilePath: Option[String] = None
  private[common] var csvData: List[List[String]] = _

//  abstract class Command extends OrderFunction {
    abstract class Command {
    /* the order of command execution */
    val priority: Int

    /* command execution */
    def exec: Unit
  }

  /* -s --source */
  case class OptionF(value: String) extends Command {

    override val priority: Int = 0

    override def exec: Unit = {
      inputFilePath = value
      val src = Source.fromFile(value)
      try {
        csvData = src
          .getLines()
          .map(_.replace(",", ", ").split(",").toList)
          .toList
          .map(_.map(_.trim))
        println(csvData)

      } finally {
        src.close()
      }
    }

//    override def equals(other: Any) = {
//      println("called!")
//      println(other.getClass)
//      other match {
//        case OptionF(path) => value == path
//        case a: common.Manipulator
//        case _ => false
//      }
//    }
  }

  /* -d --destination */
  case class OptionD(value: String) extends Command {

    override val priority: Int = 1

    override def exec: Unit = {
      outputFilePath = Some(value)
    }
  }

  /* -o --order */
  case class OptionO(value: String) extends Command {
    import OptionO._

    override val priority: Int = 2

    override def exec = {
      value match {
        case SequentialInsert.value =>
          csvData = sequentialInsert(csvData)
        case _ =>
          csvData = pass(csvData)
      }
    }
  }

  object OptionO {
    val a = new OrderFunction()
    def sequentialInsert(d: List[List[String]]): List[List[String]] = a.sequentialInsert(d)
    def pass(d: List[List[String]]): List[List[String]] = a.pass(d)
  }

  /* default */
  case object WriteOut extends Command {

    override val priority: Int = Int.MaxValue

    override def exec = {
      val bufferedWriter = new BufferedWriter(
        new FileWriter(new File(outputFilePath.getOrElse(inputFilePath)))
      )
      csvData.foreach { row =>
        bufferedWriter.append(row.mkString(","))
        bufferedWriter.newLine()
      }
      bufferedWriter.close()
    }
  }
}


