package com.fitee.search;

import com.fitee.search.interfaces.QueryMap;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

public abstract class BaseSpecification<T> implements Specification<T>, QueryMap {
    public final Map<String, String> queryMap;
    public final List<Predicate> predicates;
    public final Map<String, TriConsumer<String, Root<T>, CriteriaBuilder>> customFilters;

    public BaseSpecification(Map<String, String> queryMap) {
        this.queryMap = clean(queryMap);
        this.predicates = new ArrayList<>();
        customFilters = new HashMap<>();
    }

    @Override
    public Predicate toPredicate(Root<T> rt, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        queryMap.forEach((k,v) -> constructFilters(k, rt, cb));
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private void constructFilters(String attr, Root<T> rt, CriteriaBuilder cb) {
        if (customFilters.containsKey(attr)) {
            customFilters.get(attr).accept(attr, rt, cb);
        } else {
            defaultFilter(attr, rt, cb);
        }
    }

    private void defaultFilter(String attr, Root<T> rt, CriteriaBuilder cb) {
        // http://localhost:8080/api/v1/products?id=1,5,30              --> works with non-strings
        // http://localhost:8080/api/v1/products?name=tomatoes,bananas  --> multiple items
        // http://localhost:8080/api/v1/products?name=tomatoes          --> single item
        // http://localhost:8080/api/v1/products?name=ToMaToEs          --> not case-sensitive
        // http://localhost:8080/api/v1/products?name=*t*               --> contains    't'
        // http://localhost:8080/api/v1/products?name=*t                --> endsWith    't'
        // http://localhost:8080/api/v1/products?name=t*                --> startsWith  't'
        // http://localhost:8080/api/v1/products?sort=name              --> sorts on given attribute
        // http://localhost:8080/api/v1/products?name=t*&sort=name      --> mix and match

        // #1: db values of type <Number> are cast to <String> to fully support criteria filtering.
        // #2: currently all compared values are transformed to lower case.
        final Predicate[] arr = Arrays.stream(queryMap.get(attr).split(","))
                .map(v -> v.replace('*', '%'))
                .map(v -> cb.like(constructExpression(attr, rt, cb), constructPattern(v)))
                .toArray(Predicate[]::new);
        this.predicates.add(cb.or(arr));
    }

    private Expression<String> constructExpression(String attr, Root<T> rt, CriteriaBuilder cb) {
        Expression<String> expression = rt.get(attr).as(String.class); // String typecast
        return cb.lower(expression); // lowercase
    }

    private String constructPattern(String value) {
        return value.toLowerCase();
    }
}
