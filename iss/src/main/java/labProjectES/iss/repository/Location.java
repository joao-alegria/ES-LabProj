package labProjectES.iss.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Location implements Serializable{

    @Id
    private Integer timestamp;
    
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public Location() {
    }
    
    public Location(Integer timestamp, Double latitude, Double longitude, Double altitude) {
        this.timestamp=timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
    
    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "ISS Location: {timestamp=" + timestamp + ", latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + '}';
    }
    
}
