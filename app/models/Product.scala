package models

case class Product
(id: Option[Long],
 name: String,
 details: String,
 price: BigDecimal) {
  override def toString: String = {
    "Product: { id: " + id.getOrElse(0) + ", name: " + name + ", details: " + details + ", price: " + price + "}"
  }
}
