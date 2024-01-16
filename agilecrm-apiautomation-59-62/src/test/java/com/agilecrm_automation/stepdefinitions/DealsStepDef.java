package com.agilecrm_automation.stepdefinitions;

import com.agilecrm_automation.common.CommonFunctions;
import com.agilecrm_automation.pojo.CreateDealPojo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DealsStepDef {
    CreateDealPojo createDealPojo;
    Response response;
    CommonFunctions commonFunctions= new CommonFunctions();

    @Given("I create a deal using serialization")
    public void createDeal(){
        // create request body
        createDealPojo = new CreateDealPojo();
        createDealPojo.setName("IPL_Auction");
        createDealPojo.setExpected_value(20.00);
        createDealPojo.setProbability("100");
        createDealPojo.setMilestone("Won");
        createDealPojo.setContact_ids(List.of("4671829567143936"));

        List<Map<String,String>> customDataList = new ArrayList<>();
        customDataList.add(Map.of("name","Group Size", "value", "10"));

        createDealPojo.setCustom_data(customDataList);
        response=  commonFunctions.postWithBody(createDealPojo, "createDeal");


    }

    @Then("I verify deal created successfully")
    public void iVerifyDealCreatedSuccessfully() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());
    }

    @And("I verify created deal in get by id api")
    public void iVerifyCreatedDealInGetByIdApi() {

       response=  commonFunctions.getResponseWithPathParam("createDeal", String.valueOf(response.jsonPath().getLong("id")));

       response.prettyPrint();
    }

    @Given("I create a deal using serialization with invalid details")
    public void iCreateADealUsingSerializationWithInvalidDetails(DataTable table) {
        createDealPojo = new CreateDealPojo();
        Map<String,String> testData = table.asMaps().get(0);
        createDealPojo.setName(testData.get("name"));
        response = commonFunctions.postWithBody(createDealPojo, "createDeal");

    }

    @Then("I verify error messages in response")
    public void iVerifyErrorMessagesInResponse(DataTable table) {
        response.prettyPrint();

        Map<String,String > testData = table.asMaps().get(0);
        Assert.assertEquals(Integer.parseInt(testData.get("statusCode")), response.getStatusCode());
        Assert.assertEquals(testData.get("errorMessage"), response.asString());
    }
}
