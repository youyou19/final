package mum.edu.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import mum.edu.model.Category;
import mum.edu.repository.CategoryRepository;
import mum.edu.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {

        category.setCreationDate(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>)categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    @Override
    public Category updateCategory(Category category) {
        category.setUpdateDate(new Date());
        return categoryRepository.save(category);

    }

    @Override
    public boolean createPDF(List<Category> categoryList, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        try {

            Document document=new Document(PageSize.A4, 15,15,15,15);
            String path=context.getRealPath("/resources/reports");
            File file=new File(path);
            boolean exists=new File(path).exists();
            if(!exists) new File(path).mkdirs();
            PdfWriter pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(file+"/"+"categories"+".pdf"));
            document.open();
            Font font= FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Paragraph paragraph=new Paragraph("List of Categories",font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);


            PdfPTable table=new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font headerFont=FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Font bodyFont=FontFactory.getFont("Arial",9, BaseColor.BLACK);

            float []columnWidth= {2f,2f};
            table.setWidths(columnWidth);

            PdfPCell nomCategory=new PdfPCell(new Paragraph("Name", headerFont));
            nomCategory.setBorderColor(BaseColor.BLACK);
            nomCategory.setPaddingLeft(10);
            nomCategory.setHorizontalAlignment(Element.ALIGN_CENTER);
            nomCategory.setVerticalAlignment(Element.ALIGN_CENTER);
            nomCategory.setBackgroundColor(BaseColor.GRAY);
            nomCategory.setExtraParagraphSpace(5f);
            table.addCell(nomCategory);

            PdfPCell descriptionCategory=new PdfPCell(new Paragraph("Description", headerFont));
            descriptionCategory.setBorderColor(BaseColor.BLACK);
            descriptionCategory.setPaddingLeft(10);
            descriptionCategory.setHorizontalAlignment(Element.ALIGN_CENTER);
            descriptionCategory.setVerticalAlignment(Element.ALIGN_CENTER);
            descriptionCategory.setBackgroundColor(BaseColor.GRAY);
            descriptionCategory.setExtraParagraphSpace(5f);
            table.addCell(descriptionCategory);

            for(Category category:categoryList) {
                PdfPCell nomValue=new PdfPCell(new Paragraph(category.getName(), bodyFont));
                nomValue.setBorderColor(BaseColor.BLACK);
                nomValue.setPaddingLeft(10);
                nomValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nomValue.setVerticalAlignment(Element.ALIGN_CENTER);
               //	nomValue.setBackgroundColor(BaseColor.GRAY);
                nomValue.setExtraParagraphSpace(5f);
                table.addCell(nomValue);

                PdfPCell descriptionValue=new PdfPCell(new Paragraph(category.getDescription(), bodyFont));
                descriptionValue.setBorderColor(BaseColor.BLACK);
                descriptionValue.setPaddingLeft(10);
                descriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                descriptionValue.setVerticalAlignment(Element.ALIGN_CENTER);
                //	descriptionValue.setBackgroundColor(BaseColor.GRAY);
                descriptionValue.setExtraParagraphSpace(5f);
                table.addCell(descriptionValue);
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
    public boolean createExcel(List<Category> categoryList, ServletContext context, HttpServletRequest request,
                               HttpServletResponse response) {
        // TODO Auto-generated method stub
        String path=context.getRealPath("/resources/reports");
        File file=new File(path);
        boolean exists=new File(path).exists();
        if(!exists) new File(path).mkdirs();
        try {
            FileOutputStream outputStream=new FileOutputStream(file+"/"+"categories"+".xls");
            HSSFWorkbook book=new HSSFWorkbook();
            HSSFSheet  sheet=book.createSheet("Categories");
            sheet.setDefaultColumnWidth(30);

            HSSFCellStyle header=book.createCellStyle();
          //  header.setFillForegroundColor(HSSFColor.RED.index);
            header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


            HSSFCellStyle headerTitle=book.createCellStyle();
            headerTitle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            headerTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFRow rowTitle=sheet.createRow(0);
            HSSFCell titleCell=rowTitle.createCell(0);
            titleCell.setCellValue("List Of Categories");
            titleCell.setCellStyle(headerTitle);
            CellUtil.setAlignment(titleCell, book, CellStyle.ALIGN_CENTER);
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,2));


            HSSFRow rowHeader=sheet.createRow(4);
            HSSFCell nomCell=rowHeader.createCell(0);
            nomCell.setCellValue("Nom");
            nomCell.setCellStyle(header);
            CellUtil.setAlignment(nomCell, book, CellStyle.ALIGN_CENTER);

            HSSFCell descriptionCell=rowHeader.createCell(1);
            descriptionCell.setCellValue("Description");
            descriptionCell.setCellStyle(header);
            CellUtil.setAlignment(descriptionCell, book, CellStyle.ALIGN_CENTER);



            int i=5;

            for(Category category:categoryList) {
                HSSFRow rowBody=sheet.createRow(i);
                HSSFCellStyle cellBody=book.createCellStyle();
                cellBody.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell nameValue=rowBody.createCell(0);
                nameValue.setCellValue(category.getName());
                nameValue.setCellStyle(cellBody);

                HSSFCell descriptionValue=rowBody.createCell(1);
                descriptionValue.setCellValue(category.getDescription());
                descriptionValue.setCellStyle(cellBody);


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
    public boolean createCSV(List<Category> categoryList, ServletContext context, HttpServletRequest request,
                             HttpServletResponse response) {
        String path=context.getRealPath("/resources/reports");
        boolean exists=new File(path).exists();
        if(!exists) new File(path).mkdirs();

        File file=new File(path+"/"+File.separator+"categories.csv");
        try {

            FileWriter fileWriter=new FileWriter(file);
            CSVWriter csvWriter=new CSVWriter(fileWriter);
            List<String[]> data=new ArrayList<String[]>();
            data.add(new String[] {"Nom","Description"});
            for(Category category:categoryList) {
                data.add(new String[] {category.getName(),category.getDescription()});
            }

            csvWriter.writeAll(data);
            csvWriter.close();
            return true;


        }catch(Exception e) {

            return false;
        }

    }

}
