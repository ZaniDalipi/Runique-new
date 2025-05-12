package com.zanoappsa.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}