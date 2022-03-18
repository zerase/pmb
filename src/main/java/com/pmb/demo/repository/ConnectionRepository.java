package com.pmb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmb.demo.model.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

}
