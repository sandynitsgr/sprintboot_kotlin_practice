package com.practice.spring.kotlin.sprint_boot_in_kotlin.controllers

import com.practice.spring.kotlin.sprint_boot_in_kotlin.database.model.Note
import com.practice.spring.kotlin.sprint_boot_in_kotlin.database.repository.NoteRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.Instant


// we still might need different endpoints for this /notes url
// the distinction for the different type of requests are via HTTP methods like GET POST etc
@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteRepository: NoteRepository
) {

    data class NoteRequest(
        val id: String?,
        @field:NotBlank(message = "Title can't be blank!")
        val title: String,
        val content: String,
        val color: Long,  // we couldn't mention direction ownerId:String as a param because then user can access other's data just by having their ownerId
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant,
    )

    @PostMapping
    fun save(
        @Valid @RequestBody body: NoteRequest): NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        val note = noteRepository.save(
            Note(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                createdAt = Instant.now(),
                ownerId = ObjectId(ownerId),
            )
        )
        return note.toNoteResponse()
    }

//    @GetMapping
//    fun findByOwnerId(
//        @RequestParam(required = true) ownerId: String
//    ): List<NoteResponse> {
//        return repository.findByOwnerId(ObjectId(ownerId)).map {
//            it.toNoteResponse()
//        }
//    }

    @GetMapping
    fun findByOwnerId(): List<NoteResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        return noteRepository.findByOwnerId(ObjectId(ownerId)).map {
            it.toNoteResponse()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val note = noteRepository.findById(ObjectId(id)).orElseThrow {
            IllegalArgumentException("Note not found!")
        }
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String

        if(note.ownerId.toHexString() == ownerId) {
            noteRepository.deleteById(ObjectId(id))
        }
    }
}

private fun Note.toNoteResponse() = NoteController.NoteResponse(
    id = id.toHexString(),
    title = title,
    content = content,
    color = color,
    createdAt = createdAt,
)