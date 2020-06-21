package com.company.recruit.service;

import com.company.recruit.entity.InmemoryCandidate;

import java.util.Collection;

public interface CandidateService {
    String NAME = "cubarecruit_CandidateService";

    Collection<InmemoryCandidate> findAll();

    void setDefaultCandidate(Object uuid);

    void replace(Object id, InmemoryCandidate newCandidate);

    InmemoryCandidate getDefault();

    void save(InmemoryCandidate candidate);
}