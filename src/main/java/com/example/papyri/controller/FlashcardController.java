package com.example.papyri.controller;

import com.example.papyri.entity.Flashcard;
import com.example.papyri.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @GetMapping("/users/{userId}/flashcards")
    public List<Flashcard> getFlashcardsByUser(@PathVariable Long userId) {
        return flashcardService.findFlashcardsByUserId(userId);
    }

    @GetMapping("/decks/{deckId}/flashcards")
    public List<Flashcard> getFlashcardsByDeck(@PathVariable Long deckId) {
        return flashcardService.findFlashcardsByDeckId(deckId);
    }

    @PostMapping("/users/{userId}/flashcards")
    public ResponseEntity<Flashcard> createFlashcardForUser(@PathVariable Long userId,
            @RequestBody Flashcard flashcard) {
        Flashcard newFlashcard = flashcardService.createFlashcardForUser(userId, flashcard);
        return ResponseEntity.ok(newFlashcard);
    }

    @DeleteMapping("/flashcards/{flashcardId}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable Long flashcardId) {
        flashcardService.deleteFlashcard(flashcardId);
        return ResponseEntity.ok().build();
    }
}
