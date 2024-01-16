package com.agilecrm_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UsersResponsePojo {

    private Long page;
    private Long per_page;
    private Long total;
    private Long total_pages;
    private List<Map<String,Object>> data;
    private Map<String,String> support;

}
