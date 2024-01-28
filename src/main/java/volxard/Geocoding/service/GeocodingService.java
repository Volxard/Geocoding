package volxard.Geocoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import volxard.Geocoding.entity.GeocodingEntity;
import volxard.Geocoding.repository.GeocodingRepository;


@Service
public class GeocodingService {

    private final RestTemplate restTemplate;
    private final GeocodingRepository geocodingRepository;
    private final String googleApiKey;
    @Autowired
    public GeocodingService(RestTemplate restTemplate, GeocodingRepository geocodingRepository, String googleApiKey) {
        this.restTemplate = restTemplate;
        this.geocodingRepository = geocodingRepository;
        this.googleApiKey = googleApiKey; // Инициализация API-ключа Google
    }


    public String getAddress(double latitude, double longitude) {
        GeocodingEntity geocodingEntity = geocodingRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (geocodingEntity != null) {
            return geocodingEntity.getResponse();
        }
        // Замените URL на URL Google Geocoding API
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("latlng", latitude + "," + longitude)
                .queryParam("key", googleApiKey)
                .toUriString();
        String response = restTemplate.getForObject(url, String.class);
        geocodingEntity = new GeocodingEntity(null, latitude, longitude, response);
        geocodingRepository.save(geocodingEntity);
        return response;
    }

    public String getCoordinates(String address) {
        GeocodingEntity geocodingEntity = geocodingRepository.findByAddress(address);
        if (geocodingEntity != null) {
            return geocodingEntity.getResponse();
        }
        // Замените URL на URL Google Geocoding API
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("address", address)
                .queryParam("key", googleApiKey)
                .toUriString();
        String response = restTemplate.getForObject(url, String.class);
        geocodingEntity = new GeocodingEntity(address, null, null, response);
        geocodingRepository.save(geocodingEntity);
        return response;
    }
}