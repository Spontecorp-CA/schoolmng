package edu.school.controller.docentes;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@ViewScoped
public class WriteCircularController implements Serializable{
    
    private String para;
    private String subject;
    private String message;
    private Part file;
    private String directory;
    private String fileLabel;
    private String grupo;
    private List<String> grupos;
    
}
