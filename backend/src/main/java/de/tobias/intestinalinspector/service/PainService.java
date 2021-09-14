package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PainService {

    private final PainRepository painRepository;
    private final DateService dateService;

    @Autowired
    public PainService(PainRepository painRepository, DateService dateService) {
        this.painRepository = painRepository;
        this.dateService = dateService;
    }


    public PainEntity add(PainEntity painEntity) {
        painEntity.setDate(dateService.getDate());
        return painRepository.save(painEntity);
    }
}
