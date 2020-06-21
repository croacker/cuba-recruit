package com.company.recruit.service.mapper;

import com.company.recruit.entity.InmemoryCandidate;

public interface MapperService {
    String NAME = "cubarecruit_CandidateToFullNameService";

    String toFullName(InmemoryCandidate candidate);
}