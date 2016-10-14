package edu.school.controller.administrativos;

import edu.school.utilities.JsfUtils;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CargaArchivoAlumnos implements Serializable{
    
    private static final int FIRST_SHEET = 0;
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void handleFileUpload(FileUploadEvent event){
        file = event.getFile();
        if(file != null){
            if(file.getFileName().endsWith(".xls")){
                readDataXls();
            } else if(file.getFileName().endsWith(".xlsx")){
                readDataXlsx();
            } else {
                JsfUtils.messageError("Archivo inválido, debe ser un archivo excel");
            }
        } else {
            JsfUtils.messageError("No ha cargado un archivo");
        }
    }
    
    private void readDataXls(){
        System.out.println("es un archivo xls");
    }
    
    private void readDataXlsx(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DataFormatter formatter = new DataFormatter();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputstream());
            XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);
            sheet.forEach(row ->{
                row.forEach((Cell cell) ->{
//                    String text = formatter.formatCellValue(cell);
//                    System.out.print(text + "\t");
                    
                    switch(cell.getCellTypeEnum()){
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            if(DateUtil.isCellDateFormatted(cell)){
                                System.out.print(df.format(cell.getDateCellValue() + "\t"));
                            } else {
                                System.out.print(cell.getNumericCellValue() + "\t");
                            }
                        case BLANK:
                            System.out.println("celda sin datos");
                    }
                    
                });
                System.out.println("");
            });
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.messageError("No se ha podido leer los datos del archivo, posible datos aml formateados");
        }
    }
}
