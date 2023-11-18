package models

import org.apache.spark.sql.types._
import org.apache.spark.sql.Encoders

final case class TravelqSchema(
  ref_number: String,
  disclosure_group: String,
  title_en: String,
  title_fr: String,
  name: String,
  purpose_en: String,
  purpose_fr: String,
  start_date: String,
  end_date: String,
  destination_en: String,
  destination_fr: String,
  airfare: String,
  other_transport: String,
  lodging: String,
  meals: String,
  other_expenses: String,
  total: String,
  additional_comments_en: String,
  additional_comments_fr: String,
  owner_org: String,
  owner_org_title: String
)
object TravelqSchema {
  val schema = Encoders.product[TravelqSchema].schema
}