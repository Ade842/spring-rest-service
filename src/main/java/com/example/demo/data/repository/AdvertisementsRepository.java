package com.example.demo.data.repository;

import com.example.demo.data.entity.Advertisements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementsRepository extends JpaRepository<Advertisements, Long> {
  @Query(value = "SELECT * FROM advertisements u WHERE u.deleted = false", nativeQuery = true)
  List<Advertisements> getNonDeletedAdvertisements();

  @Query(value = "SELECT * FROM advertisements u WHERE u.id = :id and  u.deleted = false", nativeQuery = true)
  Advertisements getNonDeletedAdvertisementById(@Param("id") long id);
}
