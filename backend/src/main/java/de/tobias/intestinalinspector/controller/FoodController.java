package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.api.FrontendFoodDto;
import de.tobias.intestinalinspector.api.FrontendFoodListDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/food")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public ResponseEntity<FrontendFoodDto> add(@AuthenticationPrincipal AppUserEntity appUser,
                                               @RequestBody FrontendFoodDto frontendFoodDto) {
        FoodEntity foodToPersist = map(appUser, frontendFoodDto);
        FoodEntity persistedFood = foodService.add(foodToPersist);
        FrontendFoodDto foodToReturn = map(persistedFood);
        return ok(foodToReturn);
    }

    @GetMapping
    public ResponseEntity<FrontendFoodListDto> getAll(@AuthenticationPrincipal AppUserEntity appUser){
        List<FoodEntity> listOfFood = foodService.getAll(appUser.getUserName());
        FrontendFoodListDto foodList = new FrontendFoodListDto();
        map(listOfFood, foodList);
        return ok(foodList);
    }

    @PutMapping
    public ResponseEntity<Integer> update(@RequestBody long id, String newName){
        int numberOfChangedRows = foodService.update(id, newName);
        if(numberOfChangedRows == 1){
            return ok(numberOfChangedRows);
        } else {
            throw new IllegalArgumentException("No Element with this ID in database");
        }
    }


    private void map(List<FoodEntity> listOfFood, FrontendFoodListDto frontendFoodListDto) {
        for( FoodEntity foodItem: listOfFood){
            FrontendFoodDto foodDto = FrontendFoodDto.builder()
                    .foodName(foodItem.getFoodName())
                    .id(foodItem.getId())
                    .date(foodItem.getDate())
                    .build();
            frontendFoodListDto.addFood(foodDto);
        }
    }

    private FrontendFoodDto map(FoodEntity foodEntity) {
        return FrontendFoodDto.builder()
                .foodName(foodEntity.getFoodName())
                .build();
    }

    private FoodEntity map(AppUserEntity appUser, FrontendFoodDto frontendFoodDto) {
        return FoodEntity.builder()
                .foodName(frontendFoodDto.getFoodName())
                .userName(appUser.getUserName())
                .build();
    }


}
