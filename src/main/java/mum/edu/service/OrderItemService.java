package mum.edu.service;

import mum.edu.model.Order;
import mum.edu.model.OrderItem;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderItemService {

    public OrderItem saveOrderItem(OrderItem orderItem);
    public OrderItem findOrderItem(Long id);
    public List<OrderItem> findAll();
    public void deleteOrderItem(Long id);
    public OrderItem updateOrderItem(OrderItem order);

    boolean createPDF(List<OrderItem> orderItemList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

    boolean createExcel(List<OrderItem> orderItemList, ServletContext context, HttpServletRequest request,
                        HttpServletResponse response);

    boolean createCSV(List<OrderItem> orderItemList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);


}
