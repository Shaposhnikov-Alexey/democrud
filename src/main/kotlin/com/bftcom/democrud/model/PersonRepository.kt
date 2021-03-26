package com.bftcom.democrud.model

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: CrudRepository<Person, Int> {

    @Query("select * from person where name=:name")
    fun getPersonsByName(name: String): List<Person>?

    @Query("select * from person where lastName=:lastName")
    fun getPersonsByLastName(lastName: String): List<Person>?

    @Modifying
    @Query("insert into person (name, lastName, email) values (:name, :lastName, :email)")
    fun savePerson(name: String, lastName: String, email: String): Boolean

    @Modifying
    @Query("insert into person (name, lastName) values (:name, :lastName)")
    fun savePerson(name: String, lastName: String): Boolean

    @Modifying
    @Query("update person set name=:name, lastName=:lastName, email=:email where id=:id")
    fun replacePerson(id: Int, name: String, lastName: String, email: String?): Boolean

    @Modifying
    @Query("update person set name=:name, lastName=:lastName where id=:id")
    fun replacePerson(id: Int, name: String, lastName: String): Boolean
}