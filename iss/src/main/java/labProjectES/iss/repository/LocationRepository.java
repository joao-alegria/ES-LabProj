package labProjectES.iss.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location,Long> {

    @Query(value = "SELECT * FROM LOCATION ORDER BY timestamp DESC", nativeQuery = true)
    List<Location> getOrderLocations();
    
    @Query(value = "SELECT * FROM LOCATION ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Location getLastLocation();
}