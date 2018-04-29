package com.isabela.v1.core.repository;

import com.isabela.v1.core.model.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface InputRepository extends PagingAndSortingRepository<Input, Long>, JpaRepository<Input, Long>, JpaSpecificationExecutor<Input> {

    Input findByDocNo(Long docNo);

}
