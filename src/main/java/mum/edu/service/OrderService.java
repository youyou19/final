package mum.edu.service;

import mum.edu.model.Category;
import mum.edu.model.Order;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderService {

    public Order saveOrder(Order order);
    public Order findOrder(Long id);
    public List<Order> findAll();
    public void cancelOrder(Long id);
    public Order updateOrder(Order order);

    boolean createPDF(Order orderList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

    boolean createExcel(Order orderList, ServletContext context, HttpServletRequest request,
                        HttpServletResponse response);

    boolean createCSV(List<Order> orderList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);


}
