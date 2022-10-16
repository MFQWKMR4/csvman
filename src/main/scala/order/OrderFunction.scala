package order

import scala.math.abs

object OrderFunction {
  // Todo Refactoring
  def sequentialInsert(csvList: List[List[String]]): List[List[String]] = {
    // immutableに変える
    var list: Seq[Int] = Seq.empty
    val (header, rawFields) = (csvList.head, csvList.tail)
    val displayOrderIndex = header.indexOf("displayOrder")

    rawFields.map { raw =>
      raw.zipWithIndex.map {
        case (cell, columnIndex) =>
          if (columnIndex == displayOrderIndex) {
            cell match {
              case cell if cell.nonEmpty => list :+= cell.toInt
              case cell if cell.isEmpty =>
                require(list.length > 1, "Cannot calculate the difference.")
                val diffSeq = createDiffSeq(Seq.empty, list)
                if (diffSeq.forall(diffSeq.head == _)) {
                  list :+= list.last + diffSeq.head
                }
                else {
                  throw new Exception("Seq elements are not the same increment.")
                }
            }
          }
      }
    }

    val updatedRawFields = rawFields.zipWithIndex.map { case (csvRaw, csvRawIndex) =>
      val (_, i) = csvRaw.zipWithIndex.filter { case (_, csvColumnIndex) => csvColumnIndex == displayOrderIndex }.head
      csvRaw.updated(i, list(csvRawIndex).toString)
    }

    header :: updatedRawFields
  }

  /**
    * nothing to do
    *
    * @param csvList
    * @return
    */
  def pass(csvList: List[List[String]]): List[List[String]] = {
    csvList
  }

  private def createDiffSeq(diffSeq: Seq[Int], numSeq: Seq[Int]): Seq[Int] = {
    numSeq match {
      case head +: second +: tail =>
        createDiffSeq(diffSeq :+ abs(head - second), second +: tail)
      case _ => diffSeq
    }
  }
}