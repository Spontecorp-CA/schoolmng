package edu.school.controller.config;

import edu.school.ejb.PlantillaCircularFacadeLocal;
import edu.school.entities.PlantillaCircular;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class AdminCircularHeaderAndBottomController implements Serializable{
    
    private UploadedFile file;
    private String directory;
    private String encabezado;
    private String pie;
    private String image;
    @EJB
    private PlantillaCircularFacadeLocal plantillaCircularFacade;
    @Inject
    private PlantillaCircular plantillaCircular;
    
    @PostConstruct
    public void init(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        directory = context.getInitParameter("images.location");
        Path path = Paths.get(directory);
        try {
            if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectories(path);
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminCircularHeaderAndBottomController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        loadData();
        
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public String getImage() {
        if(image == null){
            image = "default.png";
        }
        return "/images/" + image;
    }

    public void setImage(String image){
        this.image = image;
    }
    
    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }
    
    public void upload(){
        if(file != null){
            try (InputStream input = file.getInputstream()){
                Path path = Paths.get(directory);
                Files.copy(input, new File(path.toUri().getPath(),
                                    file.getFileName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                image = file.getFileName();
                
            } catch (IOException e) {
                Logger.getLogger(AdminCircularHeaderAndBottomController.class.getName())
                        .log(Level.SEVERE, "Dió error al cargar el archivo ", e);
            }
        }
    }
    
    public void savePlantillaCircular(){
        Optional<PlantillaCircular> optional = Optional.ofNullable(plantillaCircularFacade
                            .findByStatus(Constantes.PLANTILLA_CIRCULAR_ACTIVA));
      
        if(optional.isPresent()){
            plantillaCircular = optional.get();

            System.out.println("encontró la plantilla id " + plantillaCircular.getId());
            plantillaCircular.setStatus(Constantes.PLANTILLA_CIRCULAR_INACTIVA);
            plantillaCircularFacade.edit(plantillaCircular);
        }
            plantillaCircular = new PlantillaCircular();
            plantillaCircular.setStatus(Constantes.PLANTILLA_CIRCULAR_ACTIVA);
            plantillaCircular.setLogo(image);
            plantillaCircular.setHeader(encabezado);
            plantillaCircular.setFooter(pie);
            
            plantillaCircularFacade.create(plantillaCircular);
        
        JsfUtils.messageSuccess("Plantilla de Circular actualizada con éxito");
    }
    
    public void clearFields() {
        image = "default.png";
        encabezado = "";
        pie = "";
    }
    
    private String getImageNameFromDB(){
        Optional<PlantillaCircular> optional = Optional.ofNullable(plantillaCircularFacade
                .findByStatus(Constantes.PLANTILLA_CIRCULAR_ACTIVA));
        
        String imageName = "";
        if(optional.isPresent()){
            imageName = optional.get().getLogo();
        }
        return imageName;
    }
    
    private void loadData(){
        Optional<PlantillaCircular> optional = Optional.ofNullable(plantillaCircularFacade
                .findByStatus(Constantes.PLANTILLA_CIRCULAR_ACTIVA));
        if(optional.isPresent()){
            plantillaCircular = optional.get();
            setImage(plantillaCircular.getLogo());
            setEncabezado(plantillaCircular.getHeader());
            setPie(plantillaCircular.getFooter());
        } else {
            clearFields();
        }
    }
    
}
