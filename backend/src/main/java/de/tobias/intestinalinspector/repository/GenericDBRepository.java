package de.tobias.intestinalinspector.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericDBRepository<T> extends JpaRepository<T, Long> {

    List<T> findAllByUserNameOrderByDate(String userName);

    void deleteAllByUserName(String userName);
}
