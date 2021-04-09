package com.bftcom.democrud.graphql

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class SimpleQueries: Query {
    fun nameSample(): String = "Sample for name"
    fun booleanSample(): Boolean = false
    fun randSample(): Int = Random.nextInt()
    fun helloWorld(name: String?) = "Hello, ${name ?: "World"}!"
}