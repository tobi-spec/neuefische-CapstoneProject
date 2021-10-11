package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class PainService extends GenericDBService<PainEntity, PainRepository>{


    public PainService(DateService dateService, PainRepository repository) {
        super(dateService, repository);
    }

    @Transactional
    public PainEntity update(Long id, int newNumber) {
        Optional<PainEntity> entityToChange= repository.findById(id);
        if(entityToChange.isPresent()) {
            entityToChange.get().setPainLevel(newNumber);
            return repository.save(entityToChange.get());
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }

}
