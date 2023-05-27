package fi.tuni.dummyjsonusers

import fi.tuni.dummyjsonusers.dataclasses.User

fun isValidUser(user: User): Boolean {
    if (user.firstName == "" && user.lastName == "") {
        return false
    }
    return true
}