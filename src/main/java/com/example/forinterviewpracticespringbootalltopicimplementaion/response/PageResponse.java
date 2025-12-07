package com.example.forinterviewpracticespringbootalltopicimplementaion.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private List<T> data;
    private long totalRecords;
    private int totalPages;
    private int currentPage;

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

}
