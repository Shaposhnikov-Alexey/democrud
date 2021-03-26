package com.bftcom.democrud.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@EntityScan
@Table(value = "person")
data class Person(
    @Id
    @Column(value = "id")
    @JsonProperty("id")
    val id: Int,

    @Column(value = "name")
    @JsonProperty("name")
    val name: String,

    @Column(value = "lastname")
    @JsonProperty("lastName")
    val lastName: String,

    @Column(value = "email")
    @JsonProperty("email")
    val email: String? = null
)