package practice.hotel_system.bl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import practice.hotel_system.entity.Apartments;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ItemCart {
    private Apartments apartment;
    //apartment does not contain those fields, so we create them
    private LocalDate checkin;
    private LocalDate checkout;

}
