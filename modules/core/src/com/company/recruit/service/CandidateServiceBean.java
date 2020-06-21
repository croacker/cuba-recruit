package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;
import com.company.recruit.service.mapper.MapperService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

@Service(CandidateService.NAME)
public class CandidateServiceBean implements CandidateService {

    @Inject
    CandidateRepositoryService repository;

    @Inject
    MapperService mapperService;

    @Override
    public Collection<InmemoryCandidate> findAll() {
        return repository.findAll();
    }

    @Override
    public void setDefaultCandidate(Object uuid) {
        InmemoryCandidate defaultCandidate = repository.getDefault();
        defaultCandidate.setDefault(false);
        InmemoryCandidate newDefaultCandidate = repository.findById(uuid);
        newDefaultCandidate.setDefault(true);
    }

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

    @Override
    public InmemoryCandidate getDefault() {
        return repository.getDefault();
    }

    @Override
    public void save(InmemoryCandidate candidate) {
        repository.add(candidate);
    }
}