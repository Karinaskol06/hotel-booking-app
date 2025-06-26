package practice.hotel_system.converted;

import org.springframework.stereotype.Component;
import practice.hotel_system.dto.ApartmentClassDto;
import practice.hotel_system.entity.ApartmentClasses;

import java.util.List;

@Component
public class ConvertedInfo {

    public ApartmentClassDto mapApartmentClassToDto(ApartmentClasses apartmentClass) {

        ApartmentClassDto apartmentClassDto = new ApartmentClassDto();
        apartmentClassDto.setId(apartmentClass.getId());
        apartmentClassDto.setName(apartmentClass.getApartmentClass());
        apartmentClassDto.setDescription(apartmentClass.getClassDescription());
        apartmentClassDto.setImage(apartmentClass.getLinkImg());

        return apartmentClassDto;
    }

    public List<ApartmentClassDto> mapCategoriesToListDao(List<ApartmentClasses> categories){
        return categories
                .stream()
                .map(entity -> mapApartmentClassToDto(entity))
                .toList();
    }

    public ApartmentClasses mapDtoToApartmentClass(ApartmentClassDto apartmentClassDto){

        ApartmentClasses apartmentClass = new ApartmentClasses();
        apartmentClass.setId(apartmentClassDto.getId());
        apartmentClass.setApartmentClass(apartmentClassDto.getName());
        apartmentClass.setLinkImg(apartmentClassDto.getImage());
        apartmentClass.setClassDescription(apartmentClassDto.getDescription());

        return apartmentClass;
    }

}
