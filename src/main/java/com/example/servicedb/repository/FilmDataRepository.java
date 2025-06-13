package com.example.servicedb.repository;

import com.example.servicedb.model.FilmData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmDataRepository extends JpaRepository<FilmData, Long> {}