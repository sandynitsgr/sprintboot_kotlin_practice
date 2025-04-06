package com.practice.spring.kotlin.sprint_boot_in_kotlin.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    @Id val id: ObjectId = ObjectId(),
    val email: String,
    val hashedPassword: String,
)
