package com.example.demo.data.repository;

import com.example.demo.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM user_table u WHERE u.deleted = false", nativeQuery = true)
  List<User> getNonDeletedUsers();

  @Query(value = "SELECT * FROM  user_table u WHERE u.id = :id and  u.deleted = false", nativeQuery = true)
  User getNonDeletedUsersById(@Param("id") long id);

  @Query(value = "SELECT * FROM  user_table u WHERE u.display_name = :display_name and u.display_surname = :display_surname and u.deleted = false", nativeQuery = true)
  User getNonDeletedUsersWithNameAndSurname(@Param("display_name") String name,
      @Param("display_surname") String surname);

}
