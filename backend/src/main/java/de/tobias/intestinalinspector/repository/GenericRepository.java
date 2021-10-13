package de.tobias.intestinalinspector.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long> {

    List<T> findAllByUserNameOrderByDate(String userName);

    T findByUserName(String userName);

    void deleteAllByUserName(String userName);

}
