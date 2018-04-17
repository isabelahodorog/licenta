package com.isabela.v1.core.repository;

import com.isabela.v1.core.model.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface InputRepository extends PagingAndSortingRepository<Input, Long>, JpaRepository<Input, Long>, JpaSpecificationExecutor<Input> {

    List<Input> findAllByReleaseDateAfter(Date date);

    List<Input> findAllByReleaseDateBetween(Date startDate, Date endDate);

    List<Input> findAllByDueDateBefore(Date date);

    Input findByDocNo(Long docNo);

    List<Input> findAllByProviderId(Long providerId);

    List<Input> findAllByProviderIdAndReleaseDateBetween(Long providerId, Date startDate, Date endDate);

    List<Input> findAllByProviderIdAndDueDateBefore(Long providerId, Date dueDate);

    List<Input> findAllByProviderIdAndReleaseDateAfter(Long providerId, Date startDate);
}
