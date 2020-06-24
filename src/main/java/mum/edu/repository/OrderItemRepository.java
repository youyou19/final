package mum.edu.repository;

import mum.edu.model.Order;
import mum.edu.model.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Long>{

  // @Query("select distinct ord from Order ord where ord.=:order_id")
 // public List<OrderItem> findOrderItemByOrder(@Param("order_id") Long order_id);
}

