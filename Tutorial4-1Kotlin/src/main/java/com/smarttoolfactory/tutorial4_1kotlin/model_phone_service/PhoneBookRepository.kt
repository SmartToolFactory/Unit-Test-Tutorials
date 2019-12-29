package com.smarttoolfactory.tutorial4_1kotlin.model_phone_service

interface PhoneBookRepository {
    /**
     * Insert phone record
     * @param name Contact name
     * @param phone Phone number
     */
    fun insert(name: String?, phone: String?)

    /**
     * Search for contact phone number
     * @param name Contact name
     * @return phone number
     */
    fun getPhoneNumberByContactName(name: String?): String?

    /**
     * Check if the phonebook contains this contact
     * @param name Contact name
     * @return true if this contact name exists
     */
    operator fun contains(name: String?): Boolean
}