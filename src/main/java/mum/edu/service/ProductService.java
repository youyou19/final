package mum.edu.service;

import mum.edu.model.Category;
import mum.edu.model.Product;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);
    public Product findProduct(Long id);
    public List<Product> findAll();
    public void deleteProduct(Long id);
    public List<Product> findProductByCategory(String categoryName);
    public Product updateProduct(Product product);
    public List<Product> findProductBySeller(Long user_id);

    boolean createPDF(List<Product> productList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

    boolean createExcel(List<Product> productList, ServletContext context, HttpServletRequest request,
                        HttpServletResponse response);

    boolean createCSV(List<Product> productList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

}
