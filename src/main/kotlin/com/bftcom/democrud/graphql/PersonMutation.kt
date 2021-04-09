package com.bftcom.democrud.graphql

import com.bftcom.democrud.model.PersonRepository
import com.expediagroup.graphql.server.operations.Mutation
import org.springframework.stereotype.Component

@Component
class PersonMutation(val personRepository: PersonRepository) : Mutation {
    fun addPerson(name: String, lastName: String): Boolean = personRepository.savePerson(name, lastName)

    fun addPersonWithEmail(name: String, lastName: String, email: String): Boolean =
        personRepository.savePerson(name, lastName, email)

    fun deletePerson(id: Int): Boolean {
        personRepository.deleteById(id)
        return personRepository.findById(id).isEmpty
    }
}