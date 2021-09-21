package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.FoodEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DateService {

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return now.format(dtf);
    }

    public Map<String, List<FoodEntity>> sortByWeek(List<FoodEntity> list){
        Map<String, List<FoodEntity>> entriesPerWeek = list.stream()
                .collect(Collectors.groupingBy(FoodEntity::getDate));
        return entriesPerWeek;
    }
}
