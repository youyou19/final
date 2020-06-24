package mum.edu.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mum.edu.model.Order;
import mum.edu.model.OrderItem;
import mum.edu.model.Product;
import mum.edu.repository.OrderItemRepository;
import mum.edu.repository.OrderRepository;
import mum.edu.service.OrderService;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public Order saveOrder(Order order) {
      order.setCreationDate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public Order findOrder(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findAll() {

     return (List<Order>) orderRepository.findAll();
    }

    @Override
    public void cancelOrder(Long id) {

        orderRepository.deleteById(id);

    }

    @Override
    public Order updateOrder(Order order) {
        order.setUpdateDate(new Date());
      return orderRepository.save(order);
    }

    @Override
    public boolean createPDF(Order order, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        try {

            Document document=new Document(PageSize.A4, 15,15,15,15);
            String path=context.getRealPath("/resources/reports");
            File file=new File(path);
            boolean exists=new File(path).exists();
            if(!exists) new File(path).mkdirs();
            PdfWriter pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(file+"/"+"orders"+".pdf"));
            document.open();
            Paragraph paraInvoice = new Paragraph();
            paraInvoice.add(new Paragraph("INVOICE # "+order.getId()));
            paraInvoice.add(new Paragraph(""+order.getCreationDate()));
            paraInvoice.setIndentationLeft(50);
            paraInvoice.setIndentationRight(50);
            paraInvoice.setSpacingAfter(40);
            paraInvoice.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paraInvoice);


            Font fontBuyer = FontFactory.getFont("Arial", 12, Font.BOLD);
            Paragraph paraBuyer = new Paragraph();
            paraBuyer.add(new Paragraph(order.getBuyer().getFirstName()+" "+order.getBuyer().getLastName(), fontBuyer));
            paraBuyer.add(new Paragraph(order.getBuyer().getEmail(), fontBuyer));
            paraBuyer.setAlignment(Paragraph.ALIGN_LEFT);
            paraBuyer.setIndentationLeft(50);
            paraBuyer.setIndentationRight(50);
            paraBuyer.setSpacingAfter(40);
            document.add(paraBuyer);


            Font fontbold = FontFactory.getFont("Arial", 12, Font.UNDERLINE);
           // Font font= FontFactory.getFont("Arial",12, BaseColor.BLACK);
            Paragraph paraTitle=new Paragraph("List of Items",fontbold);
            paraTitle.setAlignment(Element.ALIGN_CENTER);
            paraTitle.setIndentationLeft(50);
            paraTitle.setIndentationRight(50);
            paraTitle.setSpacingAfter(10);
            document.add(paraTitle);


            PdfPTable table=new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font headerFont=FontFactory.getFont("Arial",9, Font.BOLD);
            Font bodyFont=FontFactory.getFont("Arial",9, BaseColor.BLACK);
            double sum=0.0;

            float []columnWidth= {2f,2f,5f,2f,2f,2f};
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

            PdfPCell productPrixTotal=new PdfPCell(new Paragraph("Total Price", headerFont));
            productPrixTotal.setBorderColor(BaseColor.BLACK);
            productPrixTotal.setPaddingLeft(10);
            productPrixTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
            productPrixTotal.setVerticalAlignment(Element.ALIGN_CENTER);
            productPrixTotal.setBackgroundColor(BaseColor.GRAY);
            productPrixTotal.setExtraParagraphSpace(5f);
            table.addCell(productPrixTotal);

            for(OrderItem orderItem:order.getOrderItems().values()) {
                double prixTotal=orderItem.getItemQuantity()*orderItem.getPrice();
                PdfPCell categoryValue=new PdfPCell(new Paragraph(orderItem.getProduct().getCategory().getName(), bodyFont));
                categoryValue.setBorderColor(BaseColor.BLACK);
                categoryValue.setPaddingLeft(10);
                categoryValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                categoryValue.setVerticalAlignment(Element.ALIGN_CENTER);
                //	nomValue.setBackgroundColor(BaseColor.GRAY);
                categoryValue.setExtraParagraphSpace(5f);
                table.addCell(categoryValue);

                PdfPCell nomValue=new PdfPCell(new Paragraph(orderItem.getProduct().getName(), bodyFont));
                nomValue.setBorderColor(BaseColor.BLACK);
                nomValue.setPaddingLeft(10);
                nomValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nomValue.setVerticalAlignment(Element.ALIGN_CENTER);
                //	nomValue.setBackgroundColor(BaseColor.GRAY);
                nomValue.setExtraParagraphSpace(5f);
                table.addCell(nomValue);

                PdfPCell descriptionValue=new PdfPCell(new Paragraph(orderItem.getProduct().getDescription(), bodyFont));
                descriptionValue.setBorderColor(BaseColor.BLACK);
                descriptionValue.setPaddingLeft(10);
                descriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                descriptionValue.setVerticalAlignment(Element.ALIGN_CENTER);
                descriptionValue.setExtraParagraphSpace(5f);
                table.addCell(descriptionValue);

                PdfPCell priceValue=new PdfPCell(new Paragraph(""+orderItem.getPrice(), bodyFont));
                priceValue.setBorderColor(BaseColor.BLACK);
                priceValue.setPaddingLeft(10);
                priceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                priceValue.setVerticalAlignment(Element.ALIGN_CENTER);
                priceValue.setExtraParagraphSpace(5f);
                table.addCell(priceValue);

                PdfPCell quantityValue=new PdfPCell(new Paragraph(""+orderItem.getItemQuantity(), bodyFont));
                quantityValue.setBorderColor(BaseColor.BLACK);
                quantityValue.setPaddingLeft(10);
                quantityValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                quantityValue.setVerticalAlignment(Element.ALIGN_CENTER);
                quantityValue.setExtraParagraphSpace(5f);
                table.addCell(quantityValue);

                PdfPCell prixttotValue=new PdfPCell(new Paragraph(""+prixTotal, bodyFont));
                prixttotValue.setBorderColor(BaseColor.BLACK);
                prixttotValue.setPaddingLeft(10);
                prixttotValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                prixttotValue.setVerticalAlignment(Element.ALIGN_CENTER);
                prixttotValue.setExtraParagraphSpace(5f);
                table.addCell(prixttotValue);
                sum+=prixTotal;
            }

            Paragraph paraSum = new Paragraph();
            paraSum.add(new Paragraph("Total : "+sum));
            paraSum.setAlignment(Element.ALIGN_RIGHT);
            paraSum.setIndentationLeft(50);
            paraSum.setIndentationRight(50);
            paraSum.setSpacingAfter(40);
            paraSum.setAlignment(Element.ALIGN_LEFT);
            document.add(paraSum);

            document.add(table);
            document.close();
            pdfWriter.close();
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    @Override
    public boolean createExcel(Order order, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        String path=context.getRealPath("/resources/reports");
        File file=new File(path);
        boolean exists=new File(path).exists();
        if(!exists) new File(path).mkdirs();
        try {
            FileOutputStream outputStream=new FileOutputStream(file+"/"+"orders"+".xls");
            HSSFWorkbook book=new HSSFWorkbook();
            HSSFSheet sheet=book.createSheet("Order");
            sheet.setDefaultColumnWidth(30);

            HSSFCellStyle header=book.createCellStyle();
            header.setFillForegroundColor(HSSFColor.RED.index);
            header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFCellStyle headerInvoice=book.createCellStyle();
            headerInvoice.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFRow rowInvoice=sheet.createRow(0);
            HSSFCell invoiceCell=rowInvoice.createCell(0);
            invoiceCell.setCellValue("INVOICE # "+order.getId());
            invoiceCell.setCellStyle(headerInvoice);
             HSSFCell dateCell=rowInvoice.createCell(1);
            dateCell.setCellValue("Date: "+order.getCreationDate());
            dateCell.setCellStyle(headerInvoice);


            HSSFCellStyle headerBuyer=book.createCellStyle();
            headerBuyer.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFRow rowBuyer=sheet.createRow(2);
            HSSFCell buyerCell=rowBuyer.createCell(0);
            buyerCell.setCellValue("Customer :"+order.getBuyer().getFirstName()+" "+order.getBuyer().getLastName());
            buyerCell.setCellStyle(headerBuyer);

            HSSFCell emailCell=rowBuyer.createCell(1);
            emailCell.setCellValue("Email :"+order.getBuyer().getEmail());
            emailCell.setCellStyle(headerBuyer);


            HSSFCellStyle headerTitle=book.createCellStyle();
            headerTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFRow rowTitle=sheet.createRow(4);
            HSSFCell headerCell=rowTitle.createCell(0);
            headerCell.setCellValue(" List Of Items ");
            headerCell.setCellStyle(headerTitle);
            CellUtil.setAlignment(headerCell, book, CellStyle.ALIGN_CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,4));


            HSSFRow rowHeader=sheet.createRow(6);
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



            int i=7;
            for(OrderItem orderItem:order.getOrderItems().values()) {
//                for(Product product:productList) {
                HSSFRow rowBody=sheet.createRow(i);
                HSSFCellStyle cellBody=book.createCellStyle();
                cellBody.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell categoryValue=rowBody.createCell(0);
                categoryValue.setCellValue(orderItem.getProduct().getCategory().getName());
                categoryValue.setCellStyle(cellBody);

                HSSFCell nameValue=rowBody.createCell(1);
                nameValue.setCellValue(orderItem.getProduct().getName());
                nameValue.setCellStyle(cellBody);

                HSSFCell descriptionValue=rowBody.createCell(2);
                descriptionValue.setCellValue(orderItem.getProduct().getDescription());
                descriptionValue.setCellStyle(cellBody);

                HSSFCell priceValue=rowBody.createCell(3);
                priceValue.setCellValue(orderItem.getPrice());
                priceValue.setCellStyle(cellBody);

                HSSFCell quantityValue=rowBody.createCell(4);
                quantityValue.setCellValue(orderItem.getItemQuantity());
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
    public boolean createCSV(List<Order> orderList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
