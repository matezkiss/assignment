package com.wob.assignment.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wob.assignment.app.persistence.entity.ListingStatusEntity;

@Repository
public interface IListingStatusRepository extends JpaRepository<ListingStatusEntity, Integer> {

}
