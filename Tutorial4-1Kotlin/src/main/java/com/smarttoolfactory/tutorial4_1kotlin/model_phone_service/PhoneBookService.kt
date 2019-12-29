package com.smarttoolfactory.tutorial4_1kotlin.model_phone_service

class PhoneBookService(private val phoneBookRepository: PhoneBookRepository) {
    /**
     * Register a contact
     *
     * @param name  Contact name
     * @param phone Phone number
     */
    fun register(name: String, phone: String) {
        if (name.isNotEmpty() && phone.isNotEmpty() && !phoneBookRepository.contains(name)) {
            phoneBookRepository.insert(name, phone)
        }
    }

    /**
     * Search for a phone number by contact name
     *
     * @param name Contact name
     * @return Phone number
     */
    fun search(name: String): String? {
        return if (!name.isEmpty() && phoneBookRepository.contains(name)) {
            phoneBookRepository.getPhoneNumberByContactName(name)
        } else null
    }

}