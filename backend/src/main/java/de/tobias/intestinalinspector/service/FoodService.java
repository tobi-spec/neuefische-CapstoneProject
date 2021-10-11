package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService extends GenericDBService<FoodEntity, FoodRepository> {

    public FoodService(DateService dateService, FoodRepository repository) {
        super(dateService, repository);
    }

    @Transactional
    public FoodEntity update(Long id, String newName) {
        Optional<FoodEntity> entityToChange = repository.findById(id);
        if (entityToChange.isPresent()) {
            entityToChange.get().setFoodName(newName);
            return repository.save(entityToChange.get());
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }

}
