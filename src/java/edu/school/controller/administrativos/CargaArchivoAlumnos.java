package edu.school.controller.administrativos;

import edu.school.entities.Alumno;
import edu.school.entities.AlumnoHasRepresentante;
import edu.school.entities.DatosPersona;
import edu.school.entities.Representante;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class CargaArchivoAlumnos implements Serializable {

    private static final int FIRST_SHEET = 0;

    private static final int CELL_CI = 0;
    private static final int CELL_APELLIDO = 1;
    private static final int CELL_NOMBRE = 2;
    private static final int CELL_CURSO = 3;
    private static final int CELL_EMAIL_MADRE = 21;
    private static final int CELL_TLF_MADRE = 22;
    private static final int CELL_EMAIL_PADRE = 23;
    private static final int CELL_TLF_PADRE = 24;
    private static final int CELL_APELLIDO_PADRE = 27;
    private static final int CELL_NOMBRE_PADRE = 28;
    private static final int CELL_APELLIDO_MADRE = 29;
    private static final int CELL_NOMBRE_MADRE = 30;

    private UploadedFile file;

    private List<Alumno> alumnos;
    private List<AlumnoHasRepresentante> alumnosRepsList;

    @PostConstruct
    public void init() {
        alumnos = new ArrayList<>();
        alumnosRepsList = new ArrayList<>();
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

    private void readRowIterator(Iterator<Row> rowIterator) {
        Cell cell;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() != 0) {

                cell = row.getCell(CELL_CI);
                if (cell != null) {
                    if (cell.getCellTypeEnum() != CellType.STRING) {
                        continue;
                    }

                    rowDataParser(row);
                }
            }
        }

        printAlumnosRepList();
    }

    private void rowDataParser(Row row) {
        String ciStr = row.getCell(CELL_CI).getStringCellValue();
        Alumno alumno = createAlumno(row, ciStr);

        alumnos.add(alumno);

        Representante representante = createRepresentante(row, alumno,
                Constantes.PARENTESCO_MADRE);
        AlumnoHasRepresentante ahr = new AlumnoHasRepresentante(alumno, representante);
        alumnosRepsList.add(ahr);

        representante = createRepresentante(row, alumno, Constantes.PARENTESCO_PADRE);
        ahr = new AlumnoHasRepresentante(alumno, representante);
        alumnosRepsList.add(ahr);
    }

    private Alumno createAlumno(Row row, String ciStr) {
        NumberFormat nf = new DecimalFormat("###,###,##0");
        Number number = null;
        Alumno alumno = new Alumno();
        try {
            DatosPersona dpAl = new DatosPersona();
            number = nf.parse(ciStr);
            int numberValue = number.intValue();
            if (numberValue < 10000000) {
                alumno.setIdColegio(number.toString());
            } else {
                alumno.setCi(numberValue);
                dpAl.setCi(numberValue);
            }
            dpAl.setApellido(row.getCell(CELL_APELLIDO).getStringCellValue());
            dpAl.setNombre(row.getCell(CELL_NOMBRE).getStringCellValue());
            alumno.setDatosPersonaId(dpAl);
            return alumno;
        } catch (ParseException ex) {
            Logger.getLogger(CargaArchivoAlumnos.class.getName())
                    .log(Level.SEVERE,
                            "No pudo convertir un idColegio o una CI",
                            ex);
            return null;
        }
    }

    private Representante createRepresentante(Row row, Alumno alumno,
            String relacion) {
        Representante representante = new Representante();
        DatosPersona dp = new DatosPersona();
        String cellApellido = "";
        String cellNombre = "";
        String cellEmail = "";
        String cellTlf = "";
        String cellCelular = "";
        String parentesco = "";
        switch (relacion) {
            case Constantes.PARENTESCO_MADRE:
                cellApellido = row.getCell(CELL_APELLIDO_MADRE).getStringCellValue();
                cellNombre = row.getCell(CELL_NOMBRE_MADRE).getStringCellValue();
                cellEmail = row.getCell(CELL_EMAIL_MADRE).getStringCellValue();
                cellTlf = row.getCell(CELL_TLF_MADRE).getStringCellValue();
                cellCelular = row.getCell(CELL_TLF_MADRE).getStringCellValue();
                parentesco = Constantes.PARENTESCO_MADRE;
                break;
            case Constantes.PARENTESCO_PADRE:
                cellApellido = row.getCell(CELL_APELLIDO_PADRE).getStringCellValue();
                cellNombre = row.getCell(CELL_NOMBRE_PADRE).getStringCellValue();
                cellEmail = row.getCell(CELL_EMAIL_PADRE).getStringCellValue();
                cellTlf = row.getCell(CELL_TLF_PADRE).getStringCellValue();
                cellCelular = row.getCell(CELL_TLF_PADRE).getStringCellValue();
                parentesco = Constantes.PARENTESCO_PADRE;
                break;
        }
        dp.setApellido(cellApellido);
        dp.setNombre(cellNombre);
        dp.setEmail(cellEmail);
        dp.setTelefono(cellTlf);
        dp.setCelular(cellCelular);
        representante.setParentesco(parentesco);
        representante.setDatosPersonaId(dp);

        return representante;
    }

    private void printAlumnosRepList() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        
        Representante rep;
        DatosPersona dpRep;
        StringBuilder sb = new StringBuilder();
        List<StringBuilder> sbList = new ArrayList<>();
        Alumno oldAlumno = null;
        for (AlumnoHasRepresentante ahr : alumnosRepsList) {
            Alumno alumno = ahr.getAlumnoId();

            if (alumno != oldAlumno) {
                DatosPersona dp = alumno.getDatosPersonaId();
                sb.append(dp.getApellido()).append("      ");
                sb.append(dp.getNombre()).append("      ");
                sb.append(alumno.getIdColegio() != null ? alumno.getIdColegio() : "").append("      ");
                sb.append(alumno.getCi() != null ? alumno.getCi() : "").append("      ");

                rep = ahr.getRepresentanteId();
                dpRep = rep.getDatosPersonaId();
                sb.append(rep.getParentesco()).append(": ").append("      ");
                sb.append(dpRep.getApellido()).append("      ");
                sb.append(dpRep.getNombre()).append("      ");
                sb.append(dpRep.getEmail()).append("      ");
                sb.append(dpRep.getTelefono()).append("      ");
                sb.append(dpRep.getCelular()).append("      ");
                oldAlumno = alumno;

            } else {
                rep = ahr.getRepresentanteId();
                dpRep = rep.getDatosPersonaId();
                sb.append(rep.getParentesco()).append(": ").append("      ");
                sb.append(dpRep.getApellido()).append("      ");
                sb.append(dpRep.getNombre()).append("      ");
                sb.append(dpRep.getEmail()).append("      ");
                sb.append(dpRep.getTelefono()).append("      ");
                sb.append(dpRep.getCelular()).append("      ");

                sbList.add(sb);
                sb = new StringBuilder();
            }
        }
        
        sbList.stream().forEach(txt ->{
            System.out.println(txt.toString());
        });

    }
}
