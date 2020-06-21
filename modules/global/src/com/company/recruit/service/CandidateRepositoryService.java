package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;

import java.util.Collection;
import java.util.Optional;

/**
 * Сервис хранилище
 */
public interface CandidateRepositoryService {
    String NAME = "cubarecruit_CandidateRepositoryService";

    /**
     * Найти по идентификатору
     * @param id идентификатор
     * @return хранимая сущность
     */
    InmemoryCandidate findById(Object id);

    /**
     * Получить все хранимые сущности
     * @return хранимые сущности
     */
    Collection<InmemoryCandidate> findAll();

    /**
     * Добавить сущность в хранилище
     * @param candidate хранимая сущность
     */
    void add(InmemoryCandidate candidate);


//    void replace(InmemoryCandidate candidate, InmemoryCandidate newCandidate);

    /**
     * Сущность по-умолчанию
     * @return сущность по-умолчанию
     */
    InmemoryCandidate getDefault();
}