package com.agilecrm_automation.stepdefinitions;

import com.agilecrm_automation.pojo.CreateUserPojo;
import com.agilecrm_automation.pojo.CreateUserResponsePojo;
import com.agilecrm_automation.pojo.UsersResponsePojo;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ReqResStepDef {

    RequestSpecification specification;
    Response response;

    CreateUserPojo userPojo;
    Faker faker= new Faker(new Random());

    @Given("I setup request structure to get all users info")
    public void setup(){
        specification = RestAssured.given();

        specification.baseUri("https://reqres.in")
                .basePath("/api/users")
                .log().all();
    }

    @When("I hit an get all users api")
    public void iHitAnGetAllUsersApi() {

        response = specification.get();
    }

    @Then("I verify all users response")
    public void iVerifyAllUsersResponse() {

        response.prettyPrint();

        int statusCode = response.getStatusCode();

        Assert.assertEquals(200, statusCode);

        List<Long> ids = response.jsonPath().getList("data.id");

        System.out.println(ids);

        List<String> emailIds = response.jsonPath().getList("data.email");

        System.out.println(emailIds);

        Map<String,Object> userObject = response.jsonPath().getMap("data[0]");

        System.out.println(userObject);

        Long pageNum = response.jsonPath().getLong("page");
        System.out.println("Page Num:"+ pageNum);
    }

    @Then("I verify total users in response")
    public void iVerifyTotalUsersInResponse() {

        //get total num of pages available with user info
        Long totalPages= response.jsonPath().getLong("total_pages");

        //get total num of records available in response
        long totalUsers = response.jsonPath().getLong("total");

        List<Object> dataList = response.jsonPath().getList("data");

        int firstPagedataSize = dataList.size();

        Long size = Long.parseLong(String.valueOf(firstPagedataSize));

        Long perPageSize = response.jsonPath().getLong("per_page");

        Assert.assertEquals(size,perPageSize);
        long nextPageRecords = totalUsers - firstPagedataSize; //10
        long leftRecords = 0;
        for(int i=2;i<=perPageSize;i++){

            RequestSpecification reqSpec = RestAssured.given();
            Response resp = reqSpec.baseUri("https://reqres.in")
                    .queryParam("page",i)
                    .basePath("/api/users")
                    .log().all().get();
            resp.prettyPrint();

            long dataSize = resp.jsonPath().getList("data").size(); //4

            leftRecords =nextPageRecords-dataSize;
            if(leftRecords ==0){
                break;
            }
            Assert.assertTrue(dataSize <=leftRecords);
        }
    }

    @Given("I setup request structure to create users using serialization")
    public void iSetupRequestStructureToCreateUsers() {
        //Create an instance of encapsulation class to assign values against variables
        userPojo = new CreateUserPojo();
        userPojo.setName(faker.company().name());
        userPojo.setJob(faker.job().title());

        System.out.println(faker.name().firstName());
        System.out.println(faker.name().lastName());
        faker.book().title();

        specification = RestAssured.given();
        specification.baseUri("https://reqres.in")
                .basePath("/api/users")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userPojo)
                .log().all();

    }

    @When("I hit a api to create user")
    public void iHitAApiToCreateUser() {
        response = specification.post();
    }

    @Then("I verify user created successfully")
    public void iVerifyUserCreatedSuccessfully() {
    }

    @Then("I verify all users response using deserialization")
    public void iVerifyAllUsersResponseUsingDeserialization() {

        Assert.assertEquals(200,response.getStatusCode());

        //deserialization of response into an object
        UsersResponsePojo usersResponsePojo= response.as(UsersResponsePojo.class);

        System.out.println(usersResponsePojo.getPage());

        System.out.println(usersResponsePojo.getData());

       for(Map<String,Object>  dataMap:usersResponsePojo.getData()){

           System.out.println(dataMap.get("id"));
       }
        System.out.println(usersResponsePojo.getSupport().get("url"));
    }

    @Then("I verify user created successfully using deserialization")
    public void iVerifyUserCreatedSuccessfullyUsingDeserialization() {

        response.prettyPrint();

        Assert.assertEquals(201, response.getStatusCode());

        CreateUserResponsePojo[] responsePojo =response.as(CreateUserResponsePojo[].class);


        for (CreateUserResponsePojo createUserResponsePojo  :responsePojo){
            createUserResponsePojo.getId();
        }

        //compare name of user
        String expectedName = userPojo.getName();
//        String actualName = responsePojo.getName();

//        Assert.assertEquals(expectedName, actualName);

        //compare job value
        String expectedJob = userPojo.getJob();
//        String actualJob = responsePojo.getJob();

//        Assert.assertEquals(expectedJob, actualJob);
    }
}
