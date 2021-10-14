package de.tobias.intestinalinspector.service.dbservice;

import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import de.tobias.intestinalinspector.service.DateService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class PainDBService extends GenericDBService<PainEntity, PainRepository>{


    public PainDBService(DateService dateService, PainRepository repository) {
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
