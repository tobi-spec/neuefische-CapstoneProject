package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.dto.FrontendFoodDto;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/food)")
public class FoodController {

    FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public FrontendFoodDto add(@RequestBody FrontendFoodDto frontendFoodDto) {

        FoodEntity foodToPersist = FoodEntity.builder()
                .foodName(frontendFoodDto.getFoodName())
                .build();

        FoodEntity persistedFood = foodService.add(foodToPersist);

        FrontendFoodDto foodToReturn = FrontendFoodDto.builder()
                .foodName(persistedFood.getFoodName())
                .build();

        return foodToReturn;
    }
}
