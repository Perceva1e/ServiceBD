package com.example.servicedb.repository;

import com.example.servicedb.model.FilmData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmDataRepository extends JpaRepository<FilmData, Long> {}