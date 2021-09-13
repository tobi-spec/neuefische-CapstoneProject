package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return foodRepository.findAllByUserName(userName);
    }
}
