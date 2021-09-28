package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


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

    public List<PainEntity> getAll(String username) {
        return painRepository.findAllByUserNameOrderByDate(username);
    }

    public PainEntity update(Long id, int newNumber) {
        Optional<PainEntity> entityToChange= painRepository.findById(id);
        if(entityToChange.isPresent()) {
            entityToChange.get().setPainLevel(newNumber);
            return painRepository.save(entityToChange.get());
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }

    public PainEntity delete(Long id) {
        Optional<PainEntity> entityToDelete = painRepository.findById(id);
        if(entityToDelete.isPresent()) {
            painRepository.delete(entityToDelete.get());
            return entityToDelete.get();
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }
}
