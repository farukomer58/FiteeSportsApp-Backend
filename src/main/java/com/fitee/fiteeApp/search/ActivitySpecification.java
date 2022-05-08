package com.fitee.fiteeApp.search;

import com.fitee.fiteeApp.model.Activity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class ActivitySpecification extends BaseSpecification<Activity> {

    public ActivitySpecification(Map<String, String> queryMap) {
        super(queryMap);
        customFilters.put("category", this::customCategoryFilter);
        customFilters.put("price", this::customPriceFilter);
    }

    private void customCategoryFilter(String attr, Root<Activity> rt, CriteriaBuilder cb) {
        // http://localhost:8080/api/v1/products?category=2,5            --> multiple categories
        // http://localhost:8080/api/v1/products?category=5              --> exact category

        final Predicate[] arr = Arrays.stream(queryMap.get(attr).split(","))
                .map(v -> cb.equal(rt.get("productCategory"), Long.parseLong(v)))
                .toArray(Predicate[]::new);
        predicates.add(cb.or(arr));
    }

    private void customPriceFilter(String attr, Root<Activity> rt, CriteriaBuilder cb) {
        // http://localhost:8080/api/v1/products?price=2.95-49.95       --> between prices
        // http://localhost:8080/api/v1/products?price=2.95-            --> starting from price
        // http://localhost:8080/api/v1/products?price=2.95             --> exact price

        String[] arr = queryMap.get(attr).split("-", 2);
        final BigDecimal lo = new BigDecimal(arr[0]);
        final BigDecimal hi = arr.length > 1 ? new BigDecimal(resolveMaxPriceFilter(arr[1])) : lo;
        predicates.add(cb.between(rt.get(attr), lo, hi));
    }

    /**
     * Resolves the max price value in a filter.
     */
    private String resolveMaxPriceFilter(String maxPrice) {
        return maxPrice.isEmpty() ? String.valueOf(Integer.MAX_VALUE) : maxPrice;
    }
}
