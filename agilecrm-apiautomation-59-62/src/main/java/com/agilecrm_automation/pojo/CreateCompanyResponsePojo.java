package com.agilecrm_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CreateCompanyResponsePojo {

    private long id;
    private String type;
    private int created_time;
    private int updated_time;
    private int last_contacted;
    private int last_emailed;
    private int last_campaign_emaild;
    private int last_called;
    private int viewed_time;
    private Map<String,Object> viewed;
    private int star_value;
    private int lead_score;
    private String klout_score;
    private List<Object> tags;
    private List<Object> tagsWithTime;
    private List<Map<String,String>> properties;
    private List<Object> campaignStatus;
    private String entity_type;
    private String source;
    private List<Object> unsubscribeStatus;
    private List<Object> emailBounceStatus;
    private int formId;
    private List<Object> browserId;
    private int lead_source_id;
    private int lead_status_id;
    private Boolean is_lead_converted;
    private int lead_converted_time;
    private Boolean is_duplicate_existed;
    private int trashed_time;
    private int restored_time;
    private Boolean is_duplicate_verification_failed;
    private Boolean is_client_import;
    private Boolean concurrent_save_allowed;
    private Map<String,Object> owner;
}
