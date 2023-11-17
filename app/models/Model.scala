package models

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, DataFrame, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._
import play.api.libs.json.{ Json, JsValue }

object Model {
  val filePath: String = "C:\\Users\\ryant\\Play-with-Spark\\app\\resources\\travelq.csv"
  val spark = SparkSession.builder.appName("Travel Expenses").master("local[4]").getOrCreate
  val pageSize: Int = 20
  import spark.implicits._

  def data: DataFrame = {

    val dataFrame: DataFrame = 
      spark
        .read
        .format("csv")
        .option("header", "true")
        .option("inferSchema", "true")
        .load(filePath)

    dataFrame
    .withColumn("__rowId", monotonically_increasing_id().cast("string"))
    .limit(100) //For testing
  }

  def paginate(dataFrame: DataFrame, pageNumber: Int): DataFrame = {
    val startIndex = (pageNumber - 1) * pageSize
    val endIndex = startIndex + pageSize

    dataFrame.filter($"__rowId" >= startIndex && $"__rowId" < endIndex).drop("__rowId")
  }

  def getHeaders(dataFrame: DataFrame): Seq[String] = {
    val schema: StructType = dataFrame.schema
    schema.map(_.name)
  }

  def dataFrameToJson(dataFrame: DataFrame): Seq[JsValue] = {
    val headers = getHeaders(dataFrame)

    val dataset: Dataset[DataFrameConverter] = dataFrame.map { row =>
      val data = headers.map(header => header -> row.getAs[String](header)).toMap
      DataFrameConverter(data)  
    }

    val rows: Seq[JsValue] = dataset.collect().map(converter => Json.toJson(converter))

    rows

  }

  def jsonToDataFrame(jsonData: String): DataFrame = {

    val dataset: Dataset[DataFrameConverter] = Seq(jsonData).toDS().as[DataFrameConverter]

    val headers = getHeaders(dataset.toDF())

    val dataFrame = dataset.map { converter =>
      headers.map(header => header -> converter.data(header))
    }.toDF()

    dataFrame
    
  }

}
