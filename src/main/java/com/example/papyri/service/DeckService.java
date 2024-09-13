package com.example.papyri.service;

import com.example.papyri.entity.Deck;
import com.example.papyri.entity.Flashcard;
import com.example.papyri.entity.User;
import com.example.papyri.repository.DeckRepo;
import com.example.papyri.repository.FlashcardRepo;
import com.example.papyri.repository.UserRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    @Autowired
    private DeckRepo deckRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlashcardRepo flashcardRepo;

    public List<Deck> findDecksByUserId(Long userId) {
        return deckRepo.findByUserId(userId);
    }

    @Transactional
    public Deck createDeckForUser(Long userId, Deck deck) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        User user = userOptional.get();
        deck.setUser(user); // Set the existing user to the flashcard
        return deckRepo.save(deck);
    }

    public void deleteDeck(Long deckId) {
        deckRepo.deleteById(deckId);
    }

    @Transactional
    public void addFlashcardToDeck(Long deckId, Long flashcardId) {
        Deck deck = deckRepo.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck not found with ID: " + deckId));
        Flashcard flashcard = flashcardRepo.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found with ID: " + flashcardId));

        // Check if the flashcard's user matches the deck's user before adding
        if (!flashcard.getUser().getId().equals(deck.getUser().getId())) {
            throw new IllegalStateException("Flashcard and Deck belong to different users.");
        }

        // Check if the flashcard is already associated with the deck
        if (deck.getFlashcards().contains(flashcard)) {
            throw new IllegalStateException("Flashcard is already in the deck.");
        }

        // Add the flashcard to the deck
        deck.getFlashcards().add(flashcard);
        deckRepo.save(deck);
    }

    @Transactional
    public void removeFlashcardFromDeck(Long deckId, Long flashcardId) {
        Deck deck = deckRepo.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck not found with ID: " + deckId));
        Flashcard flashcard = flashcardRepo.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found with ID: " + flashcardId));

        // Check if the flashcard is currently associated with the deck
        if (!deck.getFlashcards().contains(flashcard)) {
            throw new IllegalStateException("Flashcard is not in the deck.");
        }

        // Remove the flashcard from the deck
        deck.getFlashcards().remove(flashcard);
        deckRepo.save(deck);

        // No need to delete the flashcard as it should remain associated with the user
    }
}
