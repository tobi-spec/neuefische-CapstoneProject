package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PainService {

    PainRepository painRepository;

    @Autowired
    public PainService(PainRepository painRepository) {
        this.painRepository = painRepository;
    }

    public PainEntity add(PainEntity painToPersist) {
        return painRepository.save(painToPersist);
    }
}
