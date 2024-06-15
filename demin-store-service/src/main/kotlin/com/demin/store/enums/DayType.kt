package com.demin.store.enums

data class DayType(val value: Type) {
    enum class Type {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
        WEEKDAYS,
        WEEKENDS,
        EVERYDAY,
    }
}
