package com.wob.assignment.app.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wob.assignment.app.persistence.entity.LocationEntity;

@Repository
public interface ILocationRepository extends JpaRepository<LocationEntity, UUID> {

}
