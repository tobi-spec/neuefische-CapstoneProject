package de.tobias.intestinalinspector.controller;

import de.tobias.intestinalinspector.api.food.FoodDto;
import de.tobias.intestinalinspector.api.food.FoodMapDto;
import de.tobias.intestinalinspector.api.food.FoodMapsDto;
import de.tobias.intestinalinspector.api.food.FoodUpdateDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.service.DateService;
import de.tobias.intestinalinspector.service.dbservice.FoodDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/food")
public class FoodController {

    private final FoodDBService foodDBService;
    private final DateService dateService;

    @Autowired
    public FoodController(FoodDBService foodDBService, DateService dateService) {
        this.foodDBService = foodDBService;
        this.dateService = dateService;
    }

    @PostMapping
    public ResponseEntity<FoodDto> add(@AuthenticationPrincipal AppUserEntity appUser,
                                       @RequestBody FoodDto foodDto) {
        String foodName = foodDto.getFoodName();
        if(foodName == null || foodName.isEmpty() || foodName == ""){
            throw new IllegalArgumentException("please type in food");
        }
        FoodEntity foodToPersist = map(appUser, foodDto);
        FoodEntity persistedFood = foodDBService.add(foodToPersist);
        FoodDto foodToReturn = map(persistedFood);
        return ok(foodToReturn);
    }

    @GetMapping
    public ResponseEntity<FoodMapsDto> getAll(@AuthenticationPrincipal AppUserEntity appUser){
        List<FoodEntity> listOfFood = foodDBService.getAll(appUser.getUserName());
        List<FoodDto> foodListToMap = map(listOfFood);
        Map<String, List<FoodDto>> results = dateService.sortByDay(foodListToMap);
        FoodMapsDto foodMapsDto = new FoodMapsDto();

        for(Map.Entry<String, List<FoodDto>> entry: results.entrySet()){
            String date = entry.getKey();
            List<FoodDto> foods = entry.getValue();
            FoodMapDto foodMapDto = new FoodMapDto();
            foodMapDto.setDate(date);
            foodMapDto.setFoods(foods);
            foodMapsDto.getFoodMaps().add(foodMapDto);
        }
        Collections.sort(foodMapsDto.getFoodMaps());
        return ok(foodMapsDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<FoodDto> update(@PathVariable Long id, @RequestBody FoodUpdateDto updateDto){
        String newName = updateDto.getNewValue();
        FoodEntity changedEntity = foodDBService.update(id, newName);
        FoodDto returnDto = map(changedEntity);
        return ok(returnDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable Long id) {
        FoodEntity deleteEntity = foodDBService.delete(id);
        FoodDto returnDto = map(deleteEntity);
        return ok(returnDto);
    }


    private List<FoodDto> map(List<FoodEntity> listOfFood) {
        List<FoodDto> foodListDto = new ArrayList<>();
        for( FoodEntity foodItem: listOfFood){
            FoodDto foodDto = FoodDto.builder()
                    .foodName(foodItem.getFoodName())
                    .id(foodItem.getId())
                    .date(foodItem.getDate())
                    .build();
            foodListDto.add(foodDto);
        }
        return foodListDto;
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