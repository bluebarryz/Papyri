package com.example.papyri.service;

import com.example.papyri.entity.Flashcard;
import com.example.papyri.entity.User;
import com.example.papyri.repository.FlashcardRepo;
import com.example.papyri.repository.UserRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepo flashcardRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Flashcard> findFlashcardsByUserId(Long userId) {
        return flashcardRepo.findByUserId(userId);
    }

    public List<Flashcard> findFlashcardsByDeckId(Long deckId) {
        return flashcardRepo.findByDecksId(deckId);
    }

    @Transactional
    public Flashcard createFlashcardForUser(Long userId, Flashcard flashcard) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        User user = userOptional.get();
        flashcard.setUser(user); // Set the existing user to the flashcard
        return flashcardRepo.save(flashcard);
    }

    public void deleteFlashcard(Long flashcardId) {
        flashcardRepo.deleteById(flashcardId);
    }
}
