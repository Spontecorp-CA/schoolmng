package edu.school.controller.administrativos;

import edu.school.entities.Alumno;
import edu.school.entities.DatosPersona;
import edu.school.utilities.JsfUtils;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CargaArchivoAlumnos implements Serializable {

    private static final int FIRST_SHEET = 0;
    private static final int CELL_NOMBRE = 0;
    private static final int CELL_APELLIDO = 1;
    private static final int CELL_CI = 2;
    private static final int CELL_FECHA_NACIMIENTO = 3;

    private UploadedFile file;

    private List<Alumno> alumnos;

    @PostConstruct
    public void init() {
        alumnos = new ArrayList<>();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        if (file != null) {
            if (file.getFileName().endsWith(".xls")) {
                readDataXls();
            } else if (file.getFileName().endsWith(".xlsx")) {
                readDataXlsx();
            } else {
                JsfUtils.messageError("Archivo inválido, debe ser un archivo excel");
            }
        } else {
            JsfUtils.messageError("No ha cargado un archivo");
        }
    }

    private void readDataXls() {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputstream());
            HSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);

            Iterator<Row> rowIterator = sheet.iterator();
            readRowIterator(rowIterator);
            
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.messageError("No se ha podido leer los datos del archivo, posible datos mal formateados");
        }
    }

    private void readDataXlsx() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputstream());
            XSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);

            Iterator<Row> rowIterator = sheet.iterator();
            readRowIterator(rowIterator);
            
        } catch (IOException ex) {
            Logger.getLogger(CargaArchivoAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.messageError("No se ha podido leer los datos del archivo, posible datos mal formateados");
        }
    }
    
    private void readRowIterator(Iterator<Row> rowIterator){
        Alumno alumno;
        Cell cell;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() != 0) {
                cell = row.getCell(CELL_NOMBRE);
                if (cell != null) {
                    if (cell.getCellTypeEnum() != CellType.STRING) {
                        continue;
                    }
                    alumno = new Alumno();
                    alumno.setDatosPersonaId(rowContentParser(row));
                    alumnos.add(alumno);
                }
            }
        }
        
        printAlumnos();
    }

    private DatosPersona rowContentParser(Row row) {
        DatosPersona dp = new DatosPersona();
        dp.setNombre(row.getCell(CELL_NOMBRE).getStringCellValue());
        dp.setApellido(row.getCell(CELL_APELLIDO).getStringCellValue());
        dp.setCi((int) row.getCell(CELL_CI).getNumericCellValue());
        Date fechaNac = row.getCell(CELL_FECHA_NACIMIENTO).getDateCellValue();
        dp.setFechaNacimiento(fechaNac);
        return dp;
    }

    private void printAlumnos() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Alumnos tiene " + alumnos.size() + " registros.");
        alumnos.stream().forEach(al -> {
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
