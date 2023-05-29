package fi.tuni.dummyjsonusers.dataclasses

/**
 * User is a dataclass that is used to represent the user objects fetched from the DummyJSON API.
 *
 * @property id Unique ID of the User. Default null.
 * @property firstName User's first name. Default empty String.
 * @property lastName User's last name. Default empty String.
 * @constructor Create empty User
 */
data class User(
    val id: Int? = null,
    var firstName: String = "",
    var lastName: String = ""
)
