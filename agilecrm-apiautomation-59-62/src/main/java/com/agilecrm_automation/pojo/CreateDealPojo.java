package com.agilecrm_automation.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CreateDealPojo {
    private String name;
    private Double expected_value;
    private String  probability;
    private Long close_date;
    private String  milestone;
    private List<String> contact_ids;
    private List<Map<String,String>> custom_data;
}
