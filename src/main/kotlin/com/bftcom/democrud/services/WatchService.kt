package com.bftcom.democrud.services

import com.bftcom.democrud.model.Person
import com.bftcom.democrud.model.PersonRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class WatchService(
    val repository: PersonRepository,
    val resourceLoader: ResourceLoader
) {
    @PostConstruct
    fun init() {
        val jsonFile = resourceLoader.getResource("classpath:initPersons.json").file
        if (!jsonFile.exists())
            println("Please ensure that [initPersons.json] is placed in 'resources' for auto-adding persons in db.")
        val persons = ObjectMapper().readerForListOf(Person::class.java).readValue<Collection<Person>>(jsonFile)
        persons.forEach {
            val listWithSameName = repository.getPersonsByName(it.name)
            when {
                listWithSameName?.any { p -> p.lastName == it.lastName }
                    ?: false -> println("ignoring Person{${it.name}, ${it.lastName}}")
                it.email == null -> repository.savePerson(it.name, it.lastName)
                else -> repository.savePerson(it.name, it.lastName, it.email)
            }
        }
    }
}