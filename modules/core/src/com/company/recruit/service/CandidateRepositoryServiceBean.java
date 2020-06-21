package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service(CandidateRepositoryService.NAME)
public class CandidateRepositoryServiceBean implements CandidateRepositoryService {

    private List<InmemoryCandidate> candidates;

    @Override
    public InmemoryCandidate findById(Object uuid) {
        return candidates.stream().filter(candidate -> candidate.getId().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public List<InmemoryCandidate> findAll() {
        return candidates;
    }

    @Override
    public void setDefaultCandidate(InmemoryCandidate defaultCandidate) {
        candidates.forEach(candidate -> {
            if (candidate.equals(defaultCandidate)) {
                candidate.setDefault(true);
            } else {
                candidate.setDefault(false);
            }
        });
    }

    public InmemoryCandidate getDefaultCandidate() {
        return candidates.stream().filter(InmemoryCandidate::isDefault).findFirst().orElse(null);
    }

    @Override
    public void add(InmemoryCandidate candidate) {
        candidates.add(candidate);
    }

    //TODO подмать как лучше заменить
    @Override
    public void replace(InmemoryCandidate candidate, InmemoryCandidate newCandidate) {
        int idx = candidates.indexOf(candidate);
        candidates.remove(idx);
        candidates.add(idx, newCandidate);
    }

    @Override
    public InmemoryCandidate getDefault() {
        return candidates.stream().filter(candidate -> candidate.isDefault()).findFirst().orElse(null);
    }

    @PostConstruct
    private void init() {
        candidates = new ArrayList<>();
        candidates.add(new InmemoryCandidate("firstName0 middleName0 lastName0", "firstName0", "middleName0", "lastName0", "lastName0@company.com", 20, true));
        candidates.add(new InmemoryCandidate("firstName1 middleName1 lastName1", "firstName1", "middleName1", "lastName1", "email1@company.com", 21, false));
        candidates.add(new InmemoryCandidate("firstName2 middleName2 lastName2", "firstName2", "middleName2", "lastName2", "email2@company.com", 22, false));
        candidates.add(new InmemoryCandidate("firstName3 middleName3 lastName3", "firstName3", "middleName3", "lastName3", "email3@company.com", 24, false));
    }
}