package com.example.FMIbook.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ServiceUtils {
    public static Pageable buildOrder(Integer limit,
                                      Integer offset,
                                      String sort,
                                      String defaultSort,
                                      Sort.Direction defaultDirection) {
        Sort.Direction orderOptions = defaultDirection;
        String sortField = defaultSort;
        if (sort != null) {
            sortField = sort.charAt(0) == '-' ? sort.substring(1) : sort;
            orderOptions = sort.charAt(0) == '-' ? Sort.Direction.DESC : Sort.Direction.ASC;
        }
        int pageNumber = offset == null ? 0 : offset;
        int pageSize = limit == null ? 5 : limit;

        return PageRequest.of(pageNumber, pageSize,
                orderOptions == Sort.Direction.ASC ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
    }
}
