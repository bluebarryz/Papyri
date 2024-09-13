package com.example.papyri.repository;

import com.example.papyri.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepo extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByUserId(Long userId); // Find all flashcards owned by a specific user

    List<Flashcard> findByDecksId(Long deckId); // Find all flashcards in a specific deck
}
