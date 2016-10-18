package edu.school.controller.administrativos;

import edu.school.entities.Alumno;
import edu.school.entities.DatosPersona;
import edu.school.utilities.JsfUtils;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CargaArchivoAlumnos implements Serializable{
    
    private static final int FIRST_SHEET = 0;
    private static final int FIRST_ROW = 1;
    private static final int FIRST_CELL = 0;
    private UploadedFile file;
    
    private List<Alumno> alumnos;

    @PostConstruct
    public void init(){
        alumnos = new ArrayList<>();
    }
    
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
        try {
            
            /*
            Ojo: este método debe adaptarse para que haga lo mismo que los 
                archivos xlsx
            */
            
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputstream());
            HSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);

            sheet.forEach(row -> {
                StringBuffer sb = new StringBuffer();
                row.forEach((Cell cell) -> {
                    sb.append(cellContentParser(cell));
                });
                System.out.println(sb.toString());
            });
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.messageError("No se ha podido leer los datos del archivo, posible datos mal formateados");
        }
    }
    
    
    private void readDataXlsx(){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputstream());
            XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);
            
            sheet.forEach(row ->{
                final Alumno alumno = new Alumno();
                System.out.println("row number: " + row.getRowNum());
                row.forEach((Cell cell) ->{
                    if(row.getRowNum() != 0){
                        alumno.setDatosPersonaId(cellContentParser(cell));
                    }
                });
                alumnos.add(alumno);
            });
            printAlumnos();
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.messageError("No se ha podido leer los datos del archivo, posible datos mal formateados");
        }
    }
    
    private DatosPersona cellContentParser(Cell cell){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuffer sb = new StringBuffer();
        CellAddress ca = cell.getAddress();
        int columna = ca.getColumn();
        DatosPersona dp = new DatosPersona();
        System.out.println("Cell: " + ca.formatAsString() + "  - columna: " + columna);
        switch(columna){
            case 0:
                if(cell.getCellTypeEnum() == CellType.STRING){
                    dp.setApellido(cell.getStringCellValue());
                }
                break;
            case 1:
                if(cell.getCellTypeEnum() == CellType.STRING){
                dp.setNombre(cell.getStringCellValue());
                }
                break;
            case 3:
                if(cell.getCellTypeEnum() == CellType.NUMERIC){
                dp.setCi(Integer.valueOf((int)cell.getNumericCellValue()));
                }
                break;
            case 4:
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    dp.setFechaNacimiento(cell.getDateCellValue());
                }
                break;
        }
        
//        switch (cell.getCellTypeEnum()) {
//            case STRING:
//                sb.append(cell.getStringCellValue()).append("\t");
//                break;
//            case NUMERIC:
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    sb.append(df.format(cell.getDateCellValue())).append("\t");
//                } else {
//                    sb.append(cell.getNumericCellValue()).append("\t");
//                }
//                break;
//        }
        return dp;
    }
    
    private void printAlumnos(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        System.out.println("Alumnos tiene " + alumnos.size() + " registros.");
        
        alumnos.stream().forEach(al ->{
            StringBuffer sb = new StringBuffer();
            DatosPersona dp = al.getDatosPersonaId();
            sb.append("Apellido: ").append(dp.getApellido() != null ? dp.getApellido() : "");
            sb.append(", Nombre: ").append(dp.getNombre() != null ? dp.getNombre() : "");
            sb.append(", CI: ").append(dp.getCi() != null ? dp.getCi() : 0);
            sb.append("F. Nac: ").append(
                    dp.getFechaNacimiento() != null ? df.format(dp.getFechaNacimiento()) : "");
            
            System.out.println(sb.toString());
        });
    }
}
