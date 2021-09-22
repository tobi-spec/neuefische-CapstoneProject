package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.FoodListDto;
import de.tobias.intestinalinspector.api.FoodUpdateDto;
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
    public ResponseEntity<FoodDto> add(@AuthenticationPrincipal AppUserEntity appUser,
                                       @RequestBody FoodDto foodDto) {
        String foodName = foodDto.getFoodName();
        if(foodName == null || foodName.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        FoodEntity foodToPersist = map(appUser, foodDto);
        FoodEntity persistedFood = foodService.add(foodToPersist);
        FoodDto foodToReturn = map(persistedFood);
        return ok(foodToReturn);
    }

    @GetMapping
    public ResponseEntity<FoodListDto> getAll(@AuthenticationPrincipal AppUserEntity appUser){
        List<FoodEntity> listOfFood = foodService.getAll(appUser.getUserName());
        FoodListDto foodListDto = new FoodListDto();
        map(listOfFood, foodListDto);
        return ok(foodListDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<FoodDto> update(@PathVariable Long id, @RequestBody FoodUpdateDto updateDto){
        String newName = updateDto.getNewValue();
        FoodEntity changedEntity = foodService.update(id, newName);
        FoodDto returnDto = map(changedEntity);
        return ok(returnDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable Long id) {
        FoodEntity deleteEntity = foodService.delete(id);
        FoodDto returnDto = map(deleteEntity);
        return ok(returnDto);
    }


    private void map(List<FoodEntity> listOfFood, FoodListDto foodListDto) {
        for( FoodEntity foodItem: listOfFood){
            FoodDto foodDto = FoodDto.builder()
                    .foodName(foodItem.getFoodName())
                    .id(foodItem.getId())
                    .date(foodItem.getDate())
                    .build();
            foodListDto.addFood(foodDto);
        }
    }

    private FoodDto map(FoodEntity foodEntity) {
        return FoodDto.builder()
                .foodName(foodEntity.getFoodName())
                .id(foodEntity.getId())
                .date(foodEntity.getDate())
                .build();
    }

    private FoodEntity map(AppUserEntity appUser, FoodDto foodDto) {
        return FoodEntity.builder()
                .foodName(foodDto.getFoodName())
                .userName(appUser.getUserName())
                .build();
    }


}