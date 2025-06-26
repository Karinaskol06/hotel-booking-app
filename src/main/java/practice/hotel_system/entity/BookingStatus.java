package practice.hotel_system.entity;

import lombok.Getter;

@Getter
public enum BookingStatus {
    NOT_PROCESSED("Необроблене"),
    CONFIRMED("Підтверджене"),
    CANCELLED("Скасоване");

    private final String displayName;

    BookingStatus(String displayName) {
        this.displayName = displayName;
    }
}
