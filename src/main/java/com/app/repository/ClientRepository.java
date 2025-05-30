package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
}