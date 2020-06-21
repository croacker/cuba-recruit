package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;

import java.util.Collection;
import java.util.Optional;

public interface CandidateRepositoryService {
    String NAME = "cubarecruit_CandidateRepositoryService";

    InmemoryCandidate findById(Object uuid);

    Collection<InmemoryCandidate> findAll();

    void setDefaultCandidate(InmemoryCandidate candidate);

    void add(InmemoryCandidate candidate);

    void replace(InmemoryCandidate candidate, InmemoryCandidate newCandidate);

    InmemoryCandidate getDefault();
}