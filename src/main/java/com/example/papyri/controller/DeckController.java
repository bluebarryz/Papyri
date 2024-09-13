package com.example.papyri.controller;

import com.example.papyri.entity.Deck;
import com.example.papyri.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @GetMapping("/users/{userId}/decks")
    public List<Deck> getAllDecksByUser(@PathVariable Long userId) {
        return deckService.findDecksByUserId(userId);
    }

    @PostMapping("/users/{userId}/decks")
    public ResponseEntity<Deck> createDeckForUser(@PathVariable Long userId, @RequestBody Deck deck) {
        Deck newDeck = deckService.createDeckForUser(userId, deck);
        return ResponseEntity.ok(newDeck);
    }

    @DeleteMapping("/decks/{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteDeck(deckId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decks/{deckId}/flashcards/{flashcardId}")
    public ResponseEntity<Void> addFlashcardToDeck(@PathVariable Long deckId, @PathVariable Long flashcardId) {
        deckService.addFlashcardToDeck(deckId, flashcardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/decks/{deckId}/flashcards/{flashcardId}")
    public ResponseEntity<Void> removeFlashcardFromDeck(@PathVariable Long deckId, @PathVariable Long flashcardId) {
        deckService.removeFlashcardFromDeck(deckId, flashcardId);
        return ResponseEntity.ok().build();
    }
}
