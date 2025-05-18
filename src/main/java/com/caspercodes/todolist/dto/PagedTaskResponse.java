package com.caspercodes.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedTaskResponse {

    private List<TaskResponse> data;
    private int page;
    private int limit;
    private long total;
}
