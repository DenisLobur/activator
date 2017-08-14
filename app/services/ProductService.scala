package services

import models.Product
import javax.inject._

trait IProductService extends BaseService[Product] {

  override def insert(product: Product): Long

  override def update(id: Long, product: Product): Boolean

  override def remove(id: Long): Boolean

  override def findById(id: Long): Option[Product]

  override def findAll(): Option[List[Product]]

  def findAllProducts(): Seq[(String, String)]
}

@Singleton
class ProductService extends IProductService {

  def insert(product: Product): Long = {
    val id = idCounter.incrementAndGet()
    product.id = Some(id)
    inMemoryDB.put(id, product)
    id
  }

  def update(id: Long, product: Product): Boolean = {
    validateId(id)
    product.id = Some(id)
    inMemoryDB.put(id, product)
    true
  }

  def remove(id: Long): Boolean = {
    validateId(id)
    inMemoryDB.remove(id)
    true
  }

  def findById(id: Long): Option[Product] = {
    inMemoryDB.get(id)
  }


  def findAll(): Option[List[Product]] = {
    if (inMemoryDB.values == Nil ||
      inMemoryDB.values.toList.size == 0) return None
    Some(inMemoryDB.values.toList)
  }

  private def validateId(id: Long): Unit = {
    val entry = inMemoryDB.get(id)
    if (entry == null) throw new RuntimeException("Could	not	find Product: " + id)
  }

  def findAllProducts(): Seq[(String, String)] = {
    val products: Seq[(String, String)] = this
      .findAll()
      .getOrElse(List(Product(Some(0), "", "", 0)))
      .toSeq
      .map { product => (product.id.get.toString, product.name) }
    return products
  }
}
