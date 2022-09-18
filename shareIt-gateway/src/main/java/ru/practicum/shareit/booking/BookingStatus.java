package ru.practicum.shareit.booking;

public enum BookingStatus {
    ALL,
    CURRENT,
    FUTURE,
    PAST,
    REJECTED,
    WAITING;

    public static BookingStatus statusFromString(String status) {
        for (BookingStatus value : values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        return null;
    }
}
