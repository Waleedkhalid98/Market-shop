package com.xantrix.webapp.repository;

import com.xantrix.webapp.entities.FamAssort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<FamAssort, Integer> {
}
