package com.practice.spring.kotlin.sprint_boot_in_kotlin.database.repository

import com.practice.spring.kotlin.sprint_boot_in_kotlin.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
}