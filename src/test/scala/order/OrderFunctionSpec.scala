package order

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class OrderFunctionSpec extends FlatSpec with Matchers with MockitoSugar {

  trait Fixture {
    val orderFunction = new OrderFunction()
  }

  "sequentialInsert: the first two element of displayOrderList is empty" should "throw exception" in new Fixture {
    // given
    val csvList = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", ""), List("Eisaku Omori", "大友栄作", ""), List("Soichiro Tachibana", "橘総一朗", ""), List("Soichiro Tachibana", "橘総一朗", "38"), List("Soichiro Tachibana", "橘総一朗", ""))

    // when
    val thrown: IllegalArgumentException = the [IllegalArgumentException] thrownBy orderFunction.sequentialInsert(csvList)

    // then
    thrown.getMessage should be ("requirement failed: The first two element of displayOrderList require a value.")
  }

  "sequentialInsert: the csvData has one empty" should "success" in new Fixture {
    // given
    val csvList = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", "10"), List("Eisaku Omori", "大友栄作", "20"), List("Soichiro Tachibana", "橘総一朗", ""), List("Keji Togashi", "冨樫慶司", "38"))
    val expectedValue = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", "10"), List("Eisaku Omori", "大友栄作", "20"), List("Soichiro Tachibana", "橘総一朗", "30"), List("Keji Togashi", "冨樫慶司", "38"))

    // when
    val result: List[List[String]] = orderFunction.sequentialInsert(csvList)

    // then
    result should be (expectedValue)
  }

  "sequentialInsert: the csvData has multiple empties" should "success" in new Fixture {
    // given
    val csvList = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", "10"), List("Eisaku Omori", "大友栄作", "20"), List("Soichiro Tachibana", "橘総一朗", ""), List("Keji Togashi", "冨樫慶司", "38"), List("Kanbe Kuroda", "黒田勘平", ""))
    val expectedValue = List(List("EnglishName", "JapaneseName", "displayOrder"), List("Ashito Aoi", "青井葦人", "10"), List("Eisaku Omori", "大友栄作", "20"), List("Soichiro Tachibana", "橘総一朗", "30"), List("Keji Togashi", "冨樫慶司", "38"), List("Kanbe Kuroda", "黒田勘平", "46"))

    // when
    val result: List[List[String]] = orderFunction.sequentialInsert(csvList)

    // then
    result should be (expectedValue)
  }

}
