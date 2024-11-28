package com.example.IITCW.Repository;

import com.example.IITCW.Entities.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {
}

/*This call is used for methods like save(), findbyID, findAll(), deleteByID() basically CRUD operations it
 handles the database interactions*/
