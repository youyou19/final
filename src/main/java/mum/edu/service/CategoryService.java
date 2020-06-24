package mum.edu.service;
import mum.edu.model.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CategoryService {

    public Category saveCategory(Category category);
    public Category findCategory(Long id);
    public List<Category> findAll();
    public void deleteCategory(Long id);
    public Category updateCategory(Category category);

    boolean createPDF(List<Category> categoryList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

    boolean createExcel(List<Category> categoryList, ServletContext context, HttpServletRequest request,
                        HttpServletResponse response);

    boolean createCSV(List<Category> categoryList, ServletContext context, HttpServletRequest request,
                      HttpServletResponse response);

 }
