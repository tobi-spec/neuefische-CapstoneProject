package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final DateService dateService;

    @Autowired
    public FoodService(FoodRepository foodRepository, DateService dateService) {
        this.foodRepository = foodRepository;
        this.dateService = dateService;
    }


    public FoodEntity add(FoodEntity foodEntity) {
        foodEntity.setDate(dateService.getDate());
        return foodRepository.save(foodEntity);
    }

    public List<FoodEntity> getAll(String userName) {
        return foodRepository.findAllByUserNameOrderByDate(userName);
    }

    public FoodEntity update(Long id, String newName) {
        foodRepository.updateFoodFromUser(id, newName);
        Optional<FoodEntity> changedEntity = foodRepository.findById(id);
        if (changedEntity.isPresent()) {
            return changedEntity.get();
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }

    public FoodEntity delete(Long id) {
        Optional<FoodEntity> entityToDelete = foodRepository.findById(id);
        if (entityToDelete.isPresent()) {
            foodRepository.delete(entityToDelete.get());
            return entityToDelete.get();
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }

    public List<FoodEntity> getAllByDate(String date) {
        Optional<List<FoodEntity>> entityList = foodRepository.findAllByDate(date);
        if (entityList.isPresent()) {
            return entityList.get();
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }
}
