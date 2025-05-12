package com.zanoappsa.auth.domain

/*Encoding password validation state, where we check all the conditions to be meet to be able to register a user
* we define this in domain since we are going to later validate the password inside domain*/


data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasNumber && hasLowerCaseCharacter && hasUpperCaseCharacter
}
