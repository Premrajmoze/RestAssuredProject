package com.agilecrm_automation.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;

public class LoginStefDefinition {

    @BeforeAll
    public static void beforeAll(){
        System.out.println(".......................This is before all method....................");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("This is after all method..........");
    }


    @Before("@Tag1" )
    public void beforeHook1(){
        System.out.println("This is before hook 1");
    }

    @Before("@Tag1 and @Tag2")
    public void beforeHook2(){
        System.out.println("This is before hook 2");
    }

    @After("@Tag2")
    public void afterHook(){
        System.out.println("This is after hook");
    }

    @BeforeStep
    public void beforeStep(){
        System.out.println("This is before step...");
    }

    @AfterStep
    public void afterStep(){
        System.out.println("This is after step hook....");
    }

    @When("I Enter valid username")
    public void enterUsername(){
        System.out.println("Enter username method");
    }

    @When("I Enter valid  password")
    public void enterPassword(){
        System.out.println("Enter password method");
    }


    @Given("I launch {string} browser and navigate to website {string}")
    public void initialization(String browser, String url){
        System.out.println("Launch browser: "+ browser);
        System.out.println("Navigate website: "+url);
    }

    @When("I enter username {string}")
    public void enterUN(String username){
        System.out.println("Username: "+ username);
    }
    @And("I enter password {string}")
    public void enterPass(String password){
        System.out.println("Password: "+ password);
    }

    @Given("I have {int} mangoes in bucket")
    public void step1(int num){
        System.out.println("I have "+ num + " mangoes");
    }
    @When("I sell {int} mangoes")
    public void step2(int num){
        System.out.println("I sell "+num + " mangoes");
    }
    @Then("I left with {int} mangoes")
    public void step3(int num){
        System.out.println("I am left with "+ num + " mangoes");
    }

    @Given("I have following browsers")
    public void browserDetails(List<String> browsers){
        System.out.println(browsers);

        browsers.forEach(values-> System.out.println(values));

    }

    @Given("We have following animals in zoo")
    public void animals(DataTable table){
        System.out.println(table);

        List<Map<String, String>> animalNames = table.asMaps();

        animalNames.forEach(values->{
                values.entrySet().forEach(obj->{
                   String key = obj.getKey();
                    String value= obj.getValue();
                    System.out.println(key + ":" + value);
                });
        });
    }

    @Given("I have below browsers")
    public void browsers(String browserList){
        System.out.println(browserList);

        String list [] = browserList.split(",");

        System.out.println(list);

        for(String val : list){
            System.out.println(val.trim());
        }
}

    @Given("I create a user with following details")
    public void iCreateAUserWithFollowingDetails(DataTable table) {
        System.out.println(table);

        List<Map<String,String>> empData= table.asMaps();

        for(Map<String,String> mapObject :empData){

            mapObject.forEach((key,val)->{
                System.out.println(key + " : " + val);
            });
        }
    }

    @Given("I add company with following details")
    public void iAddCompanyWithFollowingDetails(DataTable dataTable) {

        List<Map<String,String>> compData = dataTable.asMaps();
    }

    @Given("I create emp with following info")
    public void createEmp(Map<String,String> data){
        System.out.println(data);

    }

    @When("I Enter valid username and password")
    public void iEnterValidUsernameAndPassword() {
    }

    @Then("I Verify user logged in successfully into the application")
    public void iVerifyUserLoggedInSuccessfullyIntoTheApplication() {
    }

    @Given("I Launch the browser and navigate to website")
    public void iLaunchTheBrowserAndNavigateToWebsite(List<String> list) {
        System.out.println(list);
    }
}
