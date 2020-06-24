package mum.edu.serviceImpl;

import mum.edu.model.Payment;
import mum.edu.repository.PaymentRepository;
import mum.edu.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment savePayment(Payment payment) {
        payment.setCreationDate(new Date());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findPayment(Long id) {

        return paymentRepository.findById(id).get();
    }

    @Override
    public List<Payment> findAll() {

        return (List<Payment>) paymentRepository.findAll();
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        payment.setUpdateDate(new Date());
       return paymentRepository.save(payment);
       }

    @Override
    public boolean createPDF(List<Payment> paymentList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    @Override
    public boolean createExcel(List<Payment> paymentList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    @Override
    public boolean createCSV(List<Payment> paymentList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
