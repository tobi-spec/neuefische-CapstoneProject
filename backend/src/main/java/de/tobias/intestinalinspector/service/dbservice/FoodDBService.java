package de.tobias.intestinalinspector.service.dbservice;

import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import de.tobias.intestinalinspector.service.DateService;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FoodDBService extends GenericDBService<FoodEntity, FoodRepository> {

    public FoodDBService(DateService dateService, FoodRepository repository) {
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
