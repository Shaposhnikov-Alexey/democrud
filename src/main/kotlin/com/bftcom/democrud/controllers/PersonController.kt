package com.bftcom.democrud.controllers

import com.bftcom.democrud.model.Person
import com.bftcom.democrud.model.PersonRepository
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder
import org.apache.tomcat.util.json.JSONParser
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

@RestController
class PersonController(val repository: PersonRepository) {

    @GetMapping("/persons")
    fun getAllPersons(): MutableIterable<Person> = repository.findAll()

    @GetMapping("/name/{name}")
    fun getPersonsByName(@PathVariable("name", required = true) name: String): List<Person>? =
        repository.getPersonsByName(name)

    @GetMapping("/lastName/{lastName}")
    fun getPersonsByLastName(@PathVariable("lastName", required = true) lastName: String): List<Person>? =
        repository.getPersonsByLastName(lastName)

    @GetMapping("/id/{id}")
    fun getPersonByID(@PathVariable("id", required = true) id: Int) : Person? = repository.findById(id).orElse(null)

    @DeleteMapping("/id/{id}/delete")
    fun deletePerson(@PathVariable("id", required = true) id: Int) = repository.deleteById(id)

    @PostMapping("/addPerson")
    fun addPerson(@RequestBody data: MultiValueMap<String, String>): Boolean {
        val name = data["name"]?.get(0) ?: return false
        val lastName = data["lastName"]?.get(0) ?: return false
        val email = data["email"]?.get(0)
        if (email != null)
            repository.savePerson(name, lastName, email)
        else
            repository.savePerson(name, lastName)
        return true
    }

    @PostMapping("/addPersonJSON")
    @ResponseBody
    fun addPersonJSON(@RequestBody person: Person): Boolean {
        if (person.email != null)
            repository.savePerson(person.name, person.lastName, person.email)
        else
            repository.savePerson(person.name, person.lastName)
        return true
    }

    @PutMapping("/id/{id}/replace")
    fun replacePerson(@RequestBody data: MultiValueMap<String, String>, @PathVariable id: Int): Boolean {
        val name = data["name"]?.get(0) ?: return false
        val lastName = data["lastName"]?.get(0) ?: return false
        val email = data["email"]?.get(0)
        repository.replacePerson(id, name, lastName, email)
        return true
    }
}