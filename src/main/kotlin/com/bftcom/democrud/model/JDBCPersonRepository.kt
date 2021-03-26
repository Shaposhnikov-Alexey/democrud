package com.bftcom.democrud.model

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class JDBCPersonRepository(val jdbc: JdbcTemplate) : PersonRepository {
    override fun getAllPersons(): List<Person> =
        jdbc.query("select id, name, lastName, email from person", ::mapRow)

    override fun getPersonByID(id: Int): Person? {
        return try {
            jdbc.queryForObject("select * from person where id=?", ::mapRow, id)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun getPersonsByName(name: String): List<Person> =
        jdbc.query("select * from person where name=?", ::mapRow, name)


    override fun getPersonsByLastName(lastName: String): List<Person> =
        jdbc.query("select * from person where lastName=?", ::mapRow, lastName)


    override fun savePerson(name: String, lastName: String, email: String?): Boolean {
        if (email != null)
            jdbc.update(
                "insert into person (name, lastName, email) values (?, ?, ?)",
                name,
                lastName,
                email
            )
        else
            jdbc.update(
                "insert into person (name, lastName) values (?, ?)",
                name,
                lastName
            )
        return true
    }

    override fun replacePerson(id: Int, name: String, lastName: String, email: String?): Boolean {
        try {
            jdbc.queryForObject("select * from person where id=?", ::mapRow, id)
        } catch (e: EmptyResultDataAccessException) {
            return false
        }

        if (email != null)
            jdbc.update(
                "insert into person (name, lastName, email) values (?, ?, ?)",
                name,
                lastName,
                email
            )
        else
            jdbc.update(
                "insert into person (name, lastName) values (?, ?)",
                name,
                lastName
            )
        return true
    }

    override fun deletePerson(id: Int): Boolean {
        jdbc.update("delete from person where id = ?", id)
        return true
    }

    fun mapRow(rs: ResultSet, rowNum: Int): Person = Person(
        rs.getInt("id"), rs.getString("name"),
        rs.getString("lastName"), rs.getString("email") ?: "email is not specified"
    )
}