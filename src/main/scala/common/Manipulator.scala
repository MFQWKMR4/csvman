package common

import java.io.{ File, FileWriter, BufferedWriter }
import order.OrderFunction._
import order.OrderValue._

import scala.io.Source

object Manipulator {

  private var inputFilePath: String          = ""
  private var outputFilePath: Option[String] = None
  private var csvData: List[List[String]]    = _

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
          .map(_.replace(",", ", ").split(", ").toList)
          .toList
          .map(_.map(_.trim))

      } finally {
        src.close()
      }
    }
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
