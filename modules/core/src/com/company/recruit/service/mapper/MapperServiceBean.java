package com.company.recruit.service.mapper;

import com.company.recruit.entity.InmemoryCandidate;
import org.springframework.stereotype.Service;

/**
 * Сервис трансляции
 * TODO трансляция конкретного класса в отдельный транслятор
 */
@Service(MapperService.NAME)
public class MapperServiceBean implements MapperService {
    /**
     * Полное имя кандидата.
     * @return полное имя кандидата
     */
    public String toFullName(InmemoryCandidate candidate){
        return candidate.getFirstName() + " "
                + candidate.getMiddleName() + " "
                + candidate.getLastName();
    }
}