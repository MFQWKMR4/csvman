package common

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class ManipulatorSpec extends FlatSpec with Matchers with MockitoSugar {

  trait Fixture {
    val pwd: String = sys.env.getOrElse("PWD", "~/csvman")
    val readPath: String = pwd + "/files/sample1.csv"
    val writePath: String = pwd + "/files/sampleOut1.csv"
    val manipulator = new Manipulator()
  }

  "read csv file" should "success" in new Fixture {
    // given
    val expectedValue = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", "10"), List("Eisaku Omori", "大友栄作", "20"), List("Soichiro Tachibana", "橘総一朗", ""))

    // when
    manipulator.OptionF(readPath).exec

    // then
    manipulator.csvData should be(expectedValue)
  }

  "write csv file" should "success" in new Fixture {
    // given
    manipulator.inputFilePath = readPath
    manipulator.outputFilePath = Some(writePath)
    val expectedValue = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", "10"), List("Eisaku Omori", "大友栄作", "20"), List("Soichiro Tachibana", "橘総一朗", "30"))
    // ファイルの書き込み処理で参照するため準備
    manipulator.csvData = expectedValue

    // when
    manipulator.WriteOut.exec
    // ファイルの書き込みが完了したら空で更新する
    manipulator.csvData = List.empty

    // then
    manipulator.OptionF(writePath).exec
    val writtenCsvData: Seq[List[String]] =  manipulator.csvData
    writtenCsvData should be(expectedValue)
  }

}
