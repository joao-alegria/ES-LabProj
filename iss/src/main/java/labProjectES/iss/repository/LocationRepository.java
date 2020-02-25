/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labProjectES.iss.repository;

/**
 *
 * @author joaoalegria
 */

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location,Long> {

    @Query(value = "SELECT * FROM LOCATION ORDER BY timestamp DESC", nativeQuery = true)
    List<Location> getOrderLocations();
}