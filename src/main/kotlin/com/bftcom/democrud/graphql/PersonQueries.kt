package com.bftcom.democrud.graphql

import com.bftcom.democrud.model.Person
import com.bftcom.democrud.model.PersonRepository
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import com.expediagroup.graphql.server.operations.Query
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PersonQueries(val personRepository: PersonRepository): Query {
    fun all(): List<Person> = personRepository.findAll() as List<Person>

    fun getPersonById(id: Int): Person? = personRepository.findById(id).orElse(null)

    fun getPersonByName(name: String): List<Person>? = personRepository.getPersonsByName(name)

    fun getPersonByLastName(lastName: String): List<Person>? = personRepository.getPersonsByLastName(lastName)
}