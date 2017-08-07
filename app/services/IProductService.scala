package services

import models.Product

trait IProductService extends BaseService[Product] {
  override def insert(product: Product): Long

  override def update(id: Long, product: Product): Boolean

  override def remove(id: Long): Boolean

  override def findById(id: Long): Option[Product]

  override def findAll(): Option[List[Product]]

  def findAllProducts():Seq[(String, String)]
}
