package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;

import java.util.Collection;

/**
 * Сервис операций с хранимыми объектами
 */
public interface CandidateService {
    String NAME = "cubarecruit_CandidateService";

    /**
     * Получить всех
     * @return коллекция хранимых объектов
     */
    Collection<InmemoryCandidate> findAll();

    /**
     * Заменить объект в хранилище
     * @param id идентификатор заменяемого объекта
     * @param newCandidate новый объект
     */
    void replace(Object id, InmemoryCandidate newCandidate);

    /**
     * Указать объект по-умолчанию
     * @param id идентификатор
     */
    void setDefaultCandidate(Object id);

    /**
     * Получить объект по-умолчанию
     * @return объект по-умолчанию
     */
    InmemoryCandidate getDefault();

    /**
     * Сохранить новый объект
     * @param candidate хранимый объект
     */
    void save(InmemoryCandidate candidate);
}