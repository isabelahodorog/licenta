package com.isabela.v1.core.repository;

import com.isabela.v1.core.model.Input;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InputRepository extends JpaRepository<Input, Long> {

    List<Input> findAllByReleaseDateAfter(Date date);

    List<Input> findAllByReleaseDateBetween(Date startDate, Date endDate);

    List<Input> findAllByDueDateBefore(Date date);

    Input findByDocNo(Long docNo);

    List<Input> findAllByProviderId(Long providerId);

    List<Input> findAllByProviderIdAndReleaseDateBetween(Long providerId, Date startDate, Date endDate);

    List<Input> findAllByProviderIdAndDueDateBefore(Long providerId, Date dueDate);

    List<Input> findAllByProviderIdAndReleaseDateAfter(Long providerId, Date startDate);
}
