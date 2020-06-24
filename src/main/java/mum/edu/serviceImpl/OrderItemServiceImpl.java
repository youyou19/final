package mum.edu.serviceImpl;
import mum.edu.model.OrderItem;
import mum.edu.repository.OrderItemRepository;
import mum.edu.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl  implements OrderItemService {

    @Autowired
  private OrderItemRepository orderItemRepository;

      @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem findOrderItem(Long id) {

      return orderItemRepository.findById(id).get();

    }

    @Override
    public List<OrderItem> findAll() {

       return (List<OrderItem>) orderItemRepository.findAll();
    }

    @Override
    public void deleteOrderItem(Long id) {

        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }

    @Override
    public boolean createPDF(List<OrderItem> orderItemList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    @Override
    public boolean createExcel(List<OrderItem> orderItemList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    @Override
    public boolean createCSV(List<OrderItem> orderItemList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
