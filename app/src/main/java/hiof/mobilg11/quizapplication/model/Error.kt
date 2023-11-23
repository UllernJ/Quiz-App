package hiof.mobilg11.quizapplication.model

enum class Error(val message: String) {
    USERNAME_TAKEN("Username is already taken"),
    USERNAME_TOO_SHORT("Username is too short"),
    USERNAME_TOO_LONG("Username is too long"),
    USERNAME_INVALID("Username is invalid, do not use spaces or special characters"),
    EMAIL_INVALID("Email is invalid"),
    EMAIL_TAKEN("Email is already taken"),
    PASSWORD_TOO_SHORT("Password is too short"),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match"),
    UNKNOWN("Unknown error"),
    EMPTY_FIELDS("Please fill in all fields")
}