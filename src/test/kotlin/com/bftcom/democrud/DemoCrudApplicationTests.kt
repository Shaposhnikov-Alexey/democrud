package com.bftcom.democrud

import com.bftcom.democrud.model.Person
import com.bftcom.democrud.model.PersonRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ResourceLoader
import kotlin.random.Random

@SpringBootTest
@AutoConfigureDataJdbc
class DemoCrudApplicationTests(
    @Autowired val repository: PersonRepository,
    @Autowired val resourceLoader: ResourceLoader
) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun standardMethods() {
        val personList = repository.findAll()
        val idSet = mutableSetOf<Int>()
        personList.forEach {
            assert(!idSet.contains(it.id))
            idSet.add(it.id)
        }
        val first = personList.first()
        val id = first.id
        println(first)
        println(repository.findById(id))
        assert(repository.findById(id).get() == first)
        for (person in personList)
            if (person.id != id)
                assert(person != first)
        assert(repository.getPersonsByName(first.name)!!.any { it == first })
        assert(repository.getPersonsByLastName(first.lastName)!!.any { it == first })
    }

//    @Test
//    fun deleteUpdateSave() {
//        val personList = repository.findAll()
//        val name = "John"
//        val lastName = "Doe"
//        val rand = Random(42).nextInt(personList.count())
//        repository.replacePerson(rand, name, lastName)
//        repository.findById(rand).get().let { p ->
//            assert(p.name == name)
//            assert(p.lastName == lastName)
//        }
//        repository.deleteById(rand)
//        assert(repository.findById(rand).isEmpty)
//        assert(repository.savePerson(name, lastName))
//        var index: Int = -1
//        repository.getPersonsByLastName(lastName)?.forEach { if (it.name == name) index = it.id }
//        assert(index != -1)
//        personList.forEach { assert(it.id < index) }
//    }

    @Test
    fun checkInitialPersons() {
        val jsonFile = resourceLoader.getResource("classpath:initPersons.json").file
        if (!jsonFile.exists())
            println("Please ensure that [initPersons.json] is placed in 'resources' for auto-adding persons in db.")
        val persons = ObjectMapper().readerForListOf(Person::class.java).readValue<Collection<Person>>(jsonFile)
        persons.forEach {
            val listWithSameName = repository.getPersonsByName(it.name)
            assert(listWithSameName?.any { p -> p.lastName == it.lastName } ?: false)
        }
    }
}