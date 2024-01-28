package volxard.Geocoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import volxard.Geocoding.service.GeocodingService;

@RestController
@RequestMapping("/api/geocoding")
public class GeocodingController {

    private final GeocodingService geocodingService;

    @Autowired
    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/address")
    public String getAddress(@RequestParam double latitude, @RequestParam double longitude) {
        return geocodingService.getAddress(latitude, longitude);
    }

    @GetMapping("/coordinates")
    public String getCoordinates(@RequestParam String address) {
        return geocodingService.getCoordinates(address);
    }
}
