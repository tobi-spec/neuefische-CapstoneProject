package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.model.EntityInterface;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.repository.GenericDBRepository;
import lombok.NoArgsConstructor;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class GenericDBService <T extends EntityInterface, G extends GenericDBRepository<T>> {

    private final DateService dateService;
    final GenericDBRepository<T> repository;

    public GenericDBService(DateService dateService, G repository) {
        this.dateService = dateService;
        this.repository = repository;
    }

    public T add(T t){
        t.setDate(dateService.getDate());
        return repository.save(t);
    }

    public List<T> getAll(String userName) {
        return repository.findAllByUserNameOrderByDate(userName);
    }

    @Transactional
    public T delete(Long id) {
        Optional<T> entityToDelete = repository.findById(id);
        if (entityToDelete.isPresent()) {
            repository.delete(entityToDelete.get());
            return entityToDelete.get();
        } else {
            throw new EntityNotFoundException("No such entry found");
        }
    }
}
