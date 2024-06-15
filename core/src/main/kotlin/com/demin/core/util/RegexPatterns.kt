package com.demin.core.util

object RegexPatterns {
    const val PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
    const val PHONE_NUMBER = "^(\\+\\d{1,3}[- ]?)?(010|011|016|017|018|019)([- ]?\\d{3,4})[- ]?\\d{4}\$"
}
