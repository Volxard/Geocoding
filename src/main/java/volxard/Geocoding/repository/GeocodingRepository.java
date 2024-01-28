package volxard.Geocoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import volxard.Geocoding.entity.GeocodingEntity;

@Repository
public interface GeocodingRepository extends JpaRepository<GeocodingEntity, Long> {
    GeocodingEntity findByLatitudeAndLongitude(Double latitude, Double longitude);

    GeocodingEntity findByAddress(String address);
}
