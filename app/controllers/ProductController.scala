package controllers

import com.google.inject.{Inject, Singleton}
import models.Product
import play.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{Action, Controller}
import services.IProductService

@Singleton
class ProductController @Inject()(val messagesApi: MessagesApi,
                                  val service: IProductService) extends Controller with I18nSupport {
  val productForm: Form[Product] = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText,
      "details" -> text,
      "price" -> bigDecimal
    )(Product.apply)(Product.unapply))

  def index = Action {
    implicit request =>
      val products = service.findAll().getOrElse(Seq())
      Logger.info("index called. Products: " + products)
      Ok(views.html.product_index(products))
  }

  def blank = Action {
    implicit request =>
      Logger.info("blank called")
      Ok(views.html.product_details(None, productForm))
  }

  def details(id: Long) = Action {
    implicit request =>
      Logger.info("details called. id: " + id)
      val product = service.findById(id).get
      Ok(views.html.product_details(Some(id), productForm.fill(product)))
  }

  def insert() = Action { implicit request =>
    Logger.info("insert called.")
    productForm.bindFromRequest.fold(
      form => {
        BadRequest(views.html.product_details(None, form))
      },
      product => {
        val id = service.insert(product)
        Redirect(routes.ProductController.index).flashing("success" -> Messages("success.insert", id))
      })
  }

  def update(id: Long) = Action { implicit request =>
    Logger.info("updated called. id: " + id)
    productForm.bindFromRequest.fold(
      form => {
        Ok(views.html.product_details(Some(id), form)).flashing("error" -> "Fix the errors!")
      },
      product => {
        service.update(id, product)
        Redirect(routes.ProductController.index).flashing("success" -> Messages("success.update", product.name))
      })
  }

  def remove(id: Long) = Action {
    service.findById(id).map { product =>
      service.remove(id)
      Redirect(routes.ProductController.index).flashing("success" -> Messages("success.delete", product.name))
    }.getOrElse(NotFound)
  }
}
