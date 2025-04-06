package com.practice.spring.kotlin.sprint_boot_in_kotlin.database.repository

import com.practice.spring.kotlin.sprint_boot_in_kotlin.database.model.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<Note, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<Note>
}