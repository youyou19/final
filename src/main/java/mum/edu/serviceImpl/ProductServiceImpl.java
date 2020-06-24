package mum.edu.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import mum.edu.model.Category;
import mum.edu.model.Product;
import mum.edu.repository.ProductRepository;
import mum.edu.service.ProductService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        product.setCreationDate(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product findProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductByCategory(String categoryName) {
        return productRepository.findProductByCategory(categoryName);
    }

    @Override
    public Product updateProduct(Product product) {
        product.setUpdateDate(new Date());
        return productRepository.save(product);

    }

    @Override
    public List<Product> findProductBySeller(Long user_id) {
        return productRepository.findProductBySeller(user_id);
    }

    @Override
    public boolean createPDF(List<Product> productList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        try {

            Document document=new Document(PageSize.A4, 15,15,15,15);
            String path=context.getRealPath("/resources/reports");
            File file=new File(path);
            boolean exists=new File(path).exists();
            if(!exists) new File(path).mkdirs();
            PdfWriter pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(file+"/"+"products"+".pdf"));
            document.open();
            Font font= FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Paragraph paragraph=new Paragraph("List of Products",font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);


            PdfPTable table=new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font headerFont=FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Font bodyFont=FontFactory.getFont("Arial",9, BaseColor.BLACK);

            float []columnWidth= {3f,3f,4f,2f,2f};
            table.setWidths(columnWidth);

            PdfPCell categoryName=new PdfPCell(new Paragraph("Category", headerFont));
            categoryName.setBorderColor(BaseColor.BLACK);
            categoryName.setPaddingLeft(10);
            categoryName.setHorizontalAlignment(Element.ALIGN_CENTER);
            categoryName.setVerticalAlignment(Element.ALIGN_CENTER);
            categoryName.setBackgroundColor(BaseColor.GRAY);
            categoryName.setExtraParagraphSpace(5f);
            table.addCell(categoryName);

            PdfPCell productName=new PdfPCell(new Paragraph("Name", headerFont));
            productName.setBorderColor(BaseColor.BLACK);
            productName.setPaddingLeft(10);
            productName.setHorizontalAlignment(Element.ALIGN_CENTER);
            productName.setVerticalAlignment(Element.ALIGN_CENTER);
            productName.setBackgroundColor(BaseColor.GRAY);
            productName.setExtraParagraphSpace(5f);
            table.addCell(productName);


            PdfPCell productDescription=new PdfPCell(new Paragraph("Description", headerFont));
            productDescription.setBorderColor(BaseColor.BLACK);
            productDescription.setPaddingLeft(10);
            productDescription.setHorizontalAlignment(Element.ALIGN_CENTER);
            productDescription.setVerticalAlignment(Element.ALIGN_CENTER);
            productDescription.setBackgroundColor(BaseColor.GRAY);
            productDescription.setExtraParagraphSpace(5f);
            table.addCell(productDescription);

            PdfPCell productPrice=new PdfPCell(new Paragraph("Price", headerFont));
            productPrice.setBorderColor(BaseColor.BLACK);
            productPrice.setPaddingLeft(10);
            productPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
            productPrice.setVerticalAlignment(Element.ALIGN_CENTER);
            productPrice.setBackgroundColor(BaseColor.GRAY);
            productPrice.setExtraParagraphSpace(5f);
            table.addCell(productPrice);

            PdfPCell productQuantity=new PdfPCell(new Paragraph("Quantity", headerFont));
            productQuantity.setBorderColor(BaseColor.BLACK);
            productQuantity.setPaddingLeft(10);
            productQuantity.setHorizontalAlignment(Element.ALIGN_CENTER);
            productQuantity.setVerticalAlignment(Element.ALIGN_CENTER);
            productQuantity.setBackgroundColor(BaseColor.GRAY);
            productQuantity.setExtraParagraphSpace(5f);
            table.addCell(productQuantity);

            for(Product product:productList) {
                PdfPCell categoryValue=new PdfPCell(new Paragraph(product.getCategory().getName(), bodyFont));
                categoryValue.setBorderColor(BaseColor.BLACK);
                categoryValue.setPaddingLeft(10);
                categoryValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                categoryValue.setVerticalAlignment(Element.ALIGN_CENTER);
                //	nomValue.setBackgroundColor(BaseColor.GRAY);
                categoryValue.setExtraParagraphSpace(5f);
                table.addCell(categoryValue);

                PdfPCell nomValue=new PdfPCell(new Paragraph(product.getName(), bodyFont));
                nomValue.setBorderColor(BaseColor.BLACK);
                nomValue.setPaddingLeft(10);
                nomValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nomValue.setVerticalAlignment(Element.ALIGN_CENTER);
                //	nomValue.setBackgroundColor(BaseColor.GRAY);
                nomValue.setExtraParagraphSpace(5f);
                table.addCell(nomValue);

                PdfPCell descriptionValue=new PdfPCell(new Paragraph(product.getDescription(), bodyFont));
                descriptionValue.setBorderColor(BaseColor.BLACK);
                descriptionValue.setPaddingLeft(10);
                descriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                descriptionValue.setVerticalAlignment(Element.ALIGN_CENTER);
                descriptionValue.setExtraParagraphSpace(5f);
                table.addCell(descriptionValue);

                PdfPCell priceValue=new PdfPCell(new Paragraph(product.getPrice().toString(), bodyFont));
                priceValue.setBorderColor(BaseColor.BLACK);
                priceValue.setPaddingLeft(10);
                priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
                priceValue.setExtraParagraphSpace(5f);
                table.addCell(priceValue);

                PdfPCell quantityValue=new PdfPCell(new Paragraph(product.getQuantity().toString(), bodyFont));
                quantityValue.setBorderColor(BaseColor.BLACK);
                quantityValue.setPaddingLeft(10);
                quantityValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                quantityValue.setVerticalAlignment(Element.ALIGN_CENTER);
                quantityValue.setExtraParagraphSpace(5f);
                table.addCell(quantityValue);
            }
            document.add(table);
            document.close();
            pdfWriter.close();
            return true;
        }catch (Exception e) {
            return false;
        }
}

    @Override
    public boolean createExcel(List<Product> productList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        String path=context.getRealPath("/resources/reports");
        File file=new File(path);
        boolean exists=new File(path).exists();
        if(!exists) new File(path).mkdirs();
        try {
            FileOutputStream outputStream=new FileOutputStream(file+"/"+"products"+".xls");
            HSSFWorkbook book=new HSSFWorkbook();
            HSSFSheet sheet=book.createSheet("Products");
            sheet.setDefaultColumnWidth(30);

            HSSFCellStyle header=book.createCellStyle();
            header.setFillForegroundColor(HSSFColor.RED.index);
            header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


            HSSFCellStyle headerTitle=book.createCellStyle();
          //  headerTitle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            headerTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFRow rowTitle=sheet.createRow(0);
            HSSFCell titleCell=rowTitle.createCell(0);
            titleCell.setCellValue("List Of Products");
            titleCell.setCellStyle(headerTitle);
            CellUtil.setAlignment(titleCell, book, CellStyle.ALIGN_CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,4));


            HSSFRow rowHeader=sheet.createRow(4);
            HSSFCell categoryCell=rowHeader.createCell(0);
            categoryCell.setCellValue("Category");
            categoryCell.setCellStyle(header);
            CellUtil.setAlignment(categoryCell, book, CellStyle.ALIGN_CENTER);


            HSSFCell nomCell=rowHeader.createCell(1);
            nomCell.setCellValue("Nom");
            nomCell.setCellStyle(header);
            CellUtil.setAlignment(nomCell, book, CellStyle.ALIGN_CENTER);

            HSSFCell descriptionCell=rowHeader.createCell(2);
            descriptionCell.setCellValue("Description");
            descriptionCell.setCellStyle(header);
            CellUtil.setAlignment(descriptionCell, book, CellStyle.ALIGN_CENTER);

            HSSFCell priceCell=rowHeader.createCell(3);
            priceCell.setCellValue("Price");
            priceCell.setCellStyle(header);
            CellUtil.setAlignment(priceCell, book, CellStyle.ALIGN_CENTER);

            HSSFCell quantityCell=rowHeader.createCell(4);
            quantityCell.setCellValue("Quantity");
            quantityCell.setCellStyle(header);
            CellUtil.setAlignment(quantityCell, book, CellStyle.ALIGN_CENTER);



            int i=5;

            for(Product product:productList) {
                HSSFRow rowBody=sheet.createRow(i);
                HSSFCellStyle cellBody=book.createCellStyle();
                cellBody.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell categoryValue=rowBody.createCell(0);
                categoryValue.setCellValue(product.getCategory().getName());
                categoryValue.setCellStyle(cellBody);

                HSSFCell nameValue=rowBody.createCell(1);
                nameValue.setCellValue(product.getName());
                nameValue.setCellStyle(cellBody);

                HSSFCell descriptionValue=rowBody.createCell(2);
                descriptionValue.setCellValue(product.getDescription());
                descriptionValue.setCellStyle(cellBody);

                HSSFCell priceValue=rowBody.createCell(3);
                priceValue.setCellValue(product.getPrice());
                priceValue.setCellStyle(cellBody);

                HSSFCell quantityValue=rowBody.createCell(4);
                quantityValue.setCellValue(product.getQuantity());
                quantityValue.setCellStyle(cellBody);

                i++;
            }
            book.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        }catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean createCSV(List<Product> productList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        String path=context.getRealPath("/resources/reports");
        boolean exists=new File(path).exists();
        if(!exists) new File(path).mkdirs();

        File file=new File(path+"/"+File.separator+"products.csv");
        try {
            FileWriter fileWriter=new FileWriter(file);
            CSVWriter csvWriter=new CSVWriter(fileWriter);
            List<String[]> data=new ArrayList<String[]>();
            data.add(new String[] {"Category","Nom","Description","Price","Quantity"});
            for(Product product:productList) {
                data.add(new String[] {product.getCategory().getName(),product.getName()
                        ,product.getDescription(),product.getPrice().toString(),product.getQuantity().toString()});
            }

            csvWriter.writeAll(data);
            csvWriter.close();
            return true;


        }catch(Exception e) {

            return false;
        }

    }
}
