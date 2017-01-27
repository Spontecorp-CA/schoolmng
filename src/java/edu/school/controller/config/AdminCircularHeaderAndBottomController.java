package edu.school.controller.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class AdminCircularHeaderAndBottomController implements Serializable{
    
    private UploadedFile file;
    private String directory;
    
    @PostConstruct
    public void init(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        directory = context.getInitParameter("images.location");

        Path path = Paths.get(directory);
        try {
            if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectory(path);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminCircularHeaderAndBottomController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload(){
        if(file != null){
            
            try (InputStream input = file.getInputstream()){
                Path path = Paths.get(directory);
                Files.copy(input, new File(path.toUri().getPath(),
                                    "logo.jpg").toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Logger.getLogger(AdminCircularHeaderAndBottomController.class.getName())
                        .log(Level.SEVERE, "Dió error al cargar el archivo ", e);
            }
        }
    }
    
    public String getImage(){
        return "logo.jpg";
    }
}
