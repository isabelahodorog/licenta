package com.isabela.v1.core.repository;

import com.isabela.v1.core.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class InputSpecification {
    /**
     * This method specifies which parameter will be used when querying the database
     *
     * @param providerId
     * @param startDate
     * @param endDate
     * @param dueDate
     * @return
     */
    public static Specification<Input> searchBy(final Long providerId, final Date startDate, final Date endDate, final Date dueDate){
        return new Specification<Input>() {
            @Override
            public Predicate toPredicate(Root<Input> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (providerId != null) {
                    predicates.add(criteriaBuilder.equal(root.get(Input_.providerId), providerId));
                }
                if (startDate != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Input_.releaseDate), startDate));
                }
                if (endDate != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Input_.releaseDate), endDate));
                }

                if (dueDate != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Input_.dueDate), dueDate));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
