package com.agilecrm_automation.pojo;

import java.util.List;
import java.util.Map;

public class CreateCompanyPojo {


    private Long id;
   private String type;
   private List<Map<String,String>> properties;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperties(List<Map<String, String>> properties) {
        this.properties = properties;
    }

    public String getType(){
        return  this.type;
    }

    public List<Map<String, String>> getProperties() {
        return properties;
    }
}
