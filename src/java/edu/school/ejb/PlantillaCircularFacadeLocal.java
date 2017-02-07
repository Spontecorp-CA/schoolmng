/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.school.ejb;

import edu.school.entities.PlantillaCircular;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlantillaCircularFacadeLocal {

    void create(PlantillaCircular plantillaCircular);

    void batchCreate(Collection<PlantillaCircular> collection);

    void edit(PlantillaCircular plantillaCircular);

    void batchEdit(Collection<PlantillaCircular> collection);

    void remove(PlantillaCircular plantillaCircular);

    PlantillaCircular find(Object id);

    List<PlantillaCircular> findAll();

    List<PlantillaCircular> findRange(int[] range);

    int count();

    PlantillaCircular findByStatus(int status);
}
