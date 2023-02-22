package com.video.videostreaming.utils;

import com.video.videostreaming.model.entity.Video;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class VIDSpec {
    public static Specification<Video> findCriteria(String name, String description){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("name"),"%" +name+ "%"));
            predicates.add(criteriaBuilder.like(root.get("description"), "%" +description+ "%"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
