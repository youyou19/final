package mum.edu.service;

import mum.edu.model.Payment;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PaymentService {
    public Payment savePayment(Payment payment);
    public Payment findPayment(Long id);
    public List<Payment> findAll();
    public void deletePayment(Long id);
    public Payment updatePayment(Payment payment);

    boolean createPDF(List<Payment> paymentList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

    boolean createExcel(List<Payment> paymentList, ServletContext context, HttpServletRequest request,
                        HttpServletResponse response);

    boolean createCSV(List<Payment> paymentList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);
}
