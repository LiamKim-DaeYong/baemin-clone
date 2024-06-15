package com.demin.store.enums

data class HolidayType(val value: Type) {
    enum class Type {
        NONE,
        EVERY_MONDAY,
        EVERY_TUESDAY,
        EVERY_WEDNESDAY,
        EVERY_THURSDAY,
        EVERY_FRIDAY,
        EVERY_SATURDAY,
        EVERY_SUNDAY,
        WEEKDAYS,
        WEEKENDS,
    }
}
