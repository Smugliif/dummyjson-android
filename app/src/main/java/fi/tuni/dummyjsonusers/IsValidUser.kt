package fi.tuni.dummyjsonusers

import fi.tuni.dummyjsonusers.dataclasses.User

/**
 * Is valid user checks if a User object has empty names
 *
 * @param user User that will be validated
 * @return Boolean true if user has either a firstName or lastName
 */
fun isValidUser(user: User): Boolean {
    if (user.firstName == "" && user.lastName == "") {
        return false
    }
    return true
}