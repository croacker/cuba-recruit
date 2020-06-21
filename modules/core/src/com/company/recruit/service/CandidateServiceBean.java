package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;
import com.company.recruit.service.mapper.MapperService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Сервис операций с хранимыми объектами
 */
@Service(CandidateService.NAME)
public class CandidateServiceBean implements CandidateService {

    /**
     * Сервис-хранилище
     */
    @Inject
    CandidateRepositoryService repository;

    /**
     * Сервис трансляции в
     */
    @Inject
    MapperService mapperService;

    /**
     * Получить всех
     * @return коллекция хранимых объектов
     */
    @Override
    public Collection<InmemoryCandidate> findAll() {
        return repository.findAll();
    }

    /**
     * Указать объект по-умолчанию
     * @param id идентификатор
     */
    @Override
    public void setDefaultCandidate(Object id) {
        InmemoryCandidate defaultCandidate = repository.getDefault();
        defaultCandidate.setDefault(false);
        InmemoryCandidate newDefaultCandidate = repository.findById(id);
        newDefaultCandidate.setDefault(true);
    }

    /**
     * Указать объект по-умолчанию
     * @param id идентификатор
     */
    @Override
    public void replace(Object id, InmemoryCandidate newCandidate) {
        InmemoryCandidate candidate = repository.findById(id);
        candidate.setFullName(mapperService.toFullName(candidate));
        candidate.setFirstName(newCandidate.getFirstName());
        candidate.setMiddleName(newCandidate.getMiddleName());
        candidate.setLastName(newCandidate.getLastName());
        candidate.setEmail(newCandidate.getEmail());
        candidate.setAge(newCandidate.getAge());
    }

    /**
     * Получить объект по-умолчанию
     * @return объект по-умолчанию
     */
    @Override
    public InmemoryCandidate getDefault() {
        return repository.getDefault();
    }

    /**
     * Сохранить новый объект
     * @param candidate хранимый объект
     */
    @Override
    public void save(InmemoryCandidate candidate) {
        repository.add(candidate);
    }
}