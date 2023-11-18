package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.Logger
import play.api.libs.json.Json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import org.apache.spark.sql.DataFrame
import models.Model._


class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val headers: Seq[String] = getHeaders(data)

  def index(pageNumber: Int) = Action { implicit request =>
    val paginatedDataFrame = paginate(data, pageNumber)

    Ok(views.html.index(dataFrameToJson(paginatedDataFrame), headers, pageNumber))
  }

  def sort(pageNumber: Int) = Action { implicit request =>
    val sortField = request.getQueryString("_sortField").getOrElse("")
    val sortOrder = request.getQueryString("_sortOrder").getOrElse("")

    print(s"\n\nSORT FIELD: ${sortField}\n\nSORT ORDER: ${sortOrder}")

    val sortedDataFrame = sortDataFrame(data, sortField, sortOrder)
    val paginatedDataFrame = paginate(sortedDataFrame, pageNumber)
    
    Ok(views.html.index(dataFrameToJson(paginatedDataFrame), headers, pageNumber))
  }
  // def editRecord(pageNumber: Int, __rowId: String) = Action { implicit request =>
  //   val token = CSRF.getToken(request)
  //   val paginatedDataFrame = paginate(data, pageNumber)

  //   Ok(views.html.index(dataFrameToJson(paginatedDataFrame), headers, pageNumber, token))
  // }

  def deleteRecord(pageNumber: Int, __rowId: String) = Action { implicit request =>
    val updatedDataFrame = deleteRecordById(data, __rowId)
    val paginatedDataFrame = paginate(updatedDataFrame, pageNumber)

    Ok(views.html.index(dataFrameToJson(paginatedDataFrame), headers, pageNumber))
  }

}
