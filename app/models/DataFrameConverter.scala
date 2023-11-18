package models

import play.api.libs.json._

final case class DataFrameConverter(data: Map[String, String]) extends Serializable

object DataFrameConverter {
  implicit val format: Format[DataFrameConverter] = new Format[DataFrameConverter] {
    override def writes(converter: DataFrameConverter): JsValue = Json.toJson(converter.data)

    override def reads(json: JsValue): JsResult[DataFrameConverter] = {
      json.validate[Map[String, String]].map { data =>
        DataFrameConverter(data)
      }
    }

  }
  
}
