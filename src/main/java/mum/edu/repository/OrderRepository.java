package mum.edu.repository;

import mum.edu.model.Order;
import mum.edu.model.OrderItem;
import mum.edu.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends  CrudRepository<Order, Long>{
//    @Query("select  ord from Order ord where ord.buyer.id=:buyer_id")
//    public List<Order> findOrderByBuyer(@Param("buyer_id") Long buyer_id);

//    @Query("select  ord from Order ord")
//    public List<Order> findOrderByBuyer(@Param("buyer_id") Long buyer_id);

    //@Query("select distinct ord from Order ord Join  ord.orderItems it where it. =:order_id")
   // public List<OrderItem> findOrderItemByOrder(@Param("order_id") Long order_id);
}
