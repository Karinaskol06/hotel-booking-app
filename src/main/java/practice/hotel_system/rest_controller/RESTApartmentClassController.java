package practice.hotel_system.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.hotel_system.converted.ConvertedInfo;
import practice.hotel_system.dto.ApartmentClassDto;
import practice.hotel_system.entity.ApartmentClasses;
import practice.hotel_system.repository.ApartmentClassRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RESTApartmentClassController {

    private final ConvertedInfo convertedInfo;
    private final ApartmentClassRepository apartmentClassRepository;

    @GetMapping("/ap_class")
    public ResponseEntity<List<ApartmentClassDto>> getApartmentClass() {
        try {
            List<ApartmentClasses> apartmentClasses;
            apartmentClasses = apartmentClassRepository.findAll();

            return (apartmentClasses.isEmpty())
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(convertedInfo.mapCategoriesToListDao(apartmentClasses), HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ap_class/{id}")
    public ResponseEntity<ApartmentClassDto> getApartmentClassDaoById(@PathVariable("id") Long id){

        Optional<ApartmentClasses> apartmentClass = apartmentClassRepository.findById(id);

        return (apartmentClass.isPresent())
                ? new ResponseEntity<>(convertedInfo.mapApartmentClassToDto(apartmentClass.get()), HttpStatus.OK )
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/ap_class")
    public ResponseEntity<ApartmentClassDto> saveApartmentClass(@RequestBody ApartmentClassDto apartmentClassDto){

        try {
            ApartmentClasses apartmentClass = convertedInfo.mapDtoToApartmentClass(apartmentClassDto);
            ApartmentClasses apartmentClass1 = apartmentClassRepository.save(apartmentClass);
            ApartmentClassDto apartmentClassDto1 = convertedInfo.mapApartmentClassToDto(apartmentClass1);

            return new ResponseEntity<>(apartmentClassDto1, HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/ap_class/{id}")
    public ResponseEntity<ApartmentClassDto> update(@PathVariable("id") Long id,
                                              @RequestBody ApartmentClassDto apartmentClassDto){

        try {

            Optional<ApartmentClasses> categoryById = apartmentClassRepository.findById(id);
            if (categoryById.isPresent()) {
                ApartmentClasses apartmentClass = convertedInfo.mapDtoToApartmentClass(apartmentClassDto);
                ApartmentClasses apartmentClass1 = apartmentClassRepository.save(apartmentClass);
                ApartmentClassDto apartmentClassDto1 = convertedInfo.mapApartmentClassToDto(apartmentClass1);
                return new ResponseEntity<>(apartmentClassDto1, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/ap_class/{id}")
    public ResponseEntity<HttpStatus> deleteApartmentClass(@PathVariable("id") Long id){
        try {
            apartmentClassRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/ap_class")
    public ResponseEntity<HttpStatus> deleteAllApartmentClasses(){
        try {
            apartmentClassRepository.deleteAll();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
