package com.example.papyri.repository;

import com.example.papyri.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeckRepo extends JpaRepository<Deck, Long> {
    List<Deck> findByUserId(Long userId);
}
