package com.agilecrm_automation.stepdefinitions;

import com.agilecrm_automation.common.CommonFunctions;
import com.agilecrm_automation.pojo.CreateCompanyPojo;
import com.agilecrm_automation.pojo.CreateCompanyResponsePojo;
import com.github.javafaker.Faker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CompanyStepDef {
    RequestSpecification specification;
    Response response;

    String compName;
    String compUrl;
    CreateCompanyPojo companyPojo;
    CreateCompanyResponsePojo companyResponse;
    CommonFunctions commonFunctions = new CommonFunctions();

    @Given("I setup a request structure to get all company information")
    public void setupRequestStructure(DataTable table) {

        //initializing request specification
        specification = RestAssured.given().relaxedHTTPSValidation();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/contacts/companies/")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Accept", "application/json")
                .log().all();

    }

    @When("I hit an api to get all company info")
    public void iHitAnApiToGetAllCompanyInfo() {

        response = specification.post("list");

    }

    @Then("I verify company response")
    public void iVerifyCompanyResponse() {
        //print the response in pretty format
        response.prettyPrint();

        //verify status code
        int statusCode = response.getStatusCode();
        if (statusCode == 201) {
            System.out.println("Successfully verified the status code....");
        } else {
            System.out.println("Actual and expected status code are not equal: actual=" + statusCode + " expected=201");
        }

        //verify the type in the response

        //1. get all types from the response in the form of List<String>
        List<String> typeList = response.jsonPath().getList("type");

        System.out.println(typeList);

        //iterate the list and verify each value is correct or not....
        for (String type : typeList) {
            if (type.equals("COMPANY")) {
                System.out.println("Successfully verified type of company");
            }
        }

        //get the response in the form of list
        List<Object> listResponse = response.jsonPath().getList("");

        //to get each object, firstly we need iterate the list and convert each object into Map object
        for (Object object : listResponse) {
            //convert or typecast object into Map<String,Object>
            Map<String, Object> compObject = (Map<String, Object>) object;

            //get the company type
            String companyType = compObject.get("type").toString();
            System.out.println(companyType);

            //get the name of each company

//            compObject.get("properties")


        }
    }

    @When("I get name of all companies")
    public void iGetNameOfAllCompanies() {
        //get all companies object in list
        List<Object> compList = response.jsonPath().getList("");

        //iterate through each company object
        for (Object compObj : compList) {

            //type cast each comp object into Map<String,Object>
            Map<String, Object> comp = (Map<String, Object>) compObj;

            //get the value of properties array
            Object propObject = comp.get("properties");

            //type cast the property object retrived from map object into List<Object>
            List<Object> propList = (List<Object>) propObject;

            //iterate property list to get each object of property
            for (Object object : propList) {

                Map<String, String> mapObject = (Map<String, String>) object;

                //check if the value of name attribute is "name" or not
                if (mapObject.get("name").equals("name")) {
                    String id = comp.get("id").toString();
                    String name = mapObject.get("value");
                    System.out.println(id + ":" + name);
                }
            }


        }


//        List<Object> propertiesList= response.jsonPath().getList("properties.value");

//        System.out.println(propertiesList);

    }

    @When("I get the name of company which tags are not empty")
    public void iGetTheNameOfCompanyWhichTagsAreNotEmpty() {

        List<Object> list = response.jsonPath().getList("");

        List<Map<String, Object>> list1 = response.jsonPath().getList("");


        for (Object obj : list) {

            Map<String, Object> compMap = (Map<String, Object>) obj;

            Object tagObject = compMap.get("tags");

            List<String> tagList = (List<String>) tagObject;

            if (!tagList.isEmpty()) {  //tagList.size()>0

                Object propObject = compMap.get("properties");

                List<Object> propList = (List<Object>) propObject;

                for (Object propObj : propList) {

                    Map<String, String> object = (Map<String, String>) propObj;

                    String name = object.get("name");

                    if (name.equals("name")) {
                        String compName = object.get("value");
                        System.out.println(compName);
                    }

                }


            }


        }

    }

    @Given("I setup request structure to get all company info with below keyword")
    public void iSetupRequestStructureToGetAllCompany(Map<String, String> params) {

        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/search")
                .queryParams(params)
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Accept", "application/json")
                .log().all();
    }

    @When("I hit an api to search company with keywords")
    public void iHitAnApiToSearchCompanyWithKeywords() {
        response = specification.get();
    }

    @Then("I verify the result according to keywords")
    public void iVerifyTheResultAccordingToKeywords() {

        response.prettyPrint();
    }

    @Given("I setup request structure to create company with valid details")
    public void createCompany(Map<String, String> compData) {

        compName = compData.get("compName");

        compUrl = compData.get("url");

        String requestBody = "{\n" +
                "    \"type\": \"COMPANY\",\n" +
                "    \"properties\": [\n" +
                "        {\n" +
                "            \"name\": \"Company Type\",\n" +
                "            \"type\": \"CUSTOM\",\n" +
                "            \"value\": \"MNC Inc\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"name\",\n" +
                "            \"value\": \"" + compName + "\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"SYSTEM\",\n" +
                "            \"name\": \"url\",\n" +
                "            \"value\": \"" + compUrl + "\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
//                .header("Accept",ContentType.JSON)
//                .header("Content-Type", "application/json")
                .body(requestBody)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @When("I hit a create company api")
    public void iHitACreateCompanyApi() {
        response = specification.post("/contacts");
    }

    @Then("I verify company created successfully")
    public void iVerifyCompanyCreatedSuccessfully() {
        response.prettyPrint();

        //verify status code
        Assert.assertEquals(200, response.getStatusCode());

        List<Map<String, String>> propList = response.jsonPath().getList("properties");

        for (Map<String, String> propObject : propList) {
            if (propObject.get("name").equals("name")) {
                String actualCompName = propObject.get("value");
                Assert.assertEquals(compName, actualCompName);
            } else if (propObject.get("name").equals("url")) {
                String actualUrl = propObject.get("value");
                Assert.assertEquals(compUrl, actualUrl);
            }
        }

    }

    @Given("I setup request structure to create company with hasmap object")
    public void iSetupRequestStructureWithHasmapObject(DataTable table) {

        List<Map<String, String>> testData = table.asMaps();

        Map<String, String> data = testData.get(0);

        compName = data.get("compName");
        compUrl = data.get("url");

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("type", "COMPANY");

        List<Map<String, String>> propList = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("name", "Company Type");
        map1.put("type", "CUSTOM");
        map1.put("value", "MNC Inc");

        propList.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("type", "SYSTEM");
        map2.put("name", "name");

        Map<String, String> map3 = new HashMap<>();
        map3.put("type", "SYSTEM");
        map3.put("name", "url");

        if (compName == null) {
            map2.put("value", null);
            map3.put("value", compUrl);
        } else if (compUrl == null) {
            map2.put("value", compName);
            map3.put("value", null);
        } else if (compName == null && compUrl == null) {
            map2.put("value", null);
            map3.put("value", null);
        } else {
            map2.put("value", compName);
            map3.put("value", compUrl);
        }

        propList.add(map2);

        propList.add(map3);

        requestBody.put("properties", propList);

        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
                .body(requestBody)
//                .accept(ContentType.JSON)
//                .contentType(ContentType.JSON)
                .log().all();

    }

    @Then("I verify company created successfully with {int} status code")
    public void iVerifyCompanyCreatedSuccessfullyWithStatusCode(int statusCode) {
        response.prettyPrint();

        //verify status code
        Assert.assertEquals(statusCode, response.getStatusCode());

        List<Map<String, String>> propList = response.jsonPath().getList("properties");

        for (Map<String, String> propObject : propList) {
            if (propObject.get("name").equals("name")) {
                String actualCompName = propObject.get("value");
                Assert.assertEquals(compName, actualCompName);
            } else if (propObject.get("name").equals("url")) {
                String actualUrl = propObject.get("value");
                Assert.assertEquals(compUrl, actualUrl);
            }
        }

    }

    @Given("I setup request structure to create company using json file")
    public void iSetupRequestStructureToCreateCompanyUsingJsonFile() throws IOException, ParseException {

        //define the path of json file
        String filePath = System.getProperty("user.dir") + "/src/test/resources/RequestFiles/CreateCompanyRequest.json";

        //create file class object to get the control of the file
        File file = new File(filePath);

        //To get the content of the file, we need parse it using JSONParser class
        //The JSONParser class belongs to simple.json lib

        //read the content of the file
        FileReader fileReader = new FileReader(file);

        //create an instance of JSONParser
        JSONParser jsonParser = new JSONParser();

        Object inputObject = jsonParser.parse(fileReader);

        //convert inputObject into JSONObject using type casting
        JSONObject jsonObject = (JSONObject) inputObject;

        JSONArray propArray = (JSONArray) jsonObject.get("properties");

        //get the name object present at index 1 in properties array
        JSONObject nameObject = (JSONObject) propArray.get(1);

//        nameObject.put("value", "Amazon");

        compName = nameObject.get("value").toString();

        JSONObject urlObject = (JSONObject) propArray.get(2);

        compUrl = urlObject.get("value").toString();

        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
//                .body(file)
                .body(jsonObject)
                .log().all();

    }

    @Given("I setup request structure to create company using seriaization")
    public void iSetupRequestStructureToCreateCompanyUsingSeriaization() {
        companyPojo = new CreateCompanyPojo();
        companyPojo.setType("COMPANY");

        List<Map<String, String>> propList = new ArrayList<>();

        Map<String, String> compType = new HashMap<>();
        compType.put("name", "Company Type");
        compType.put("type", "CUSTOM");
        compType.put("value", "MNC Inc");

        propList.add(compType);

        Map<String, String> compNameObj = new HashMap<>();
        compNameObj.put("name", "name");
        compNameObj.put("type", "SYSTEM");
        compNameObj.put("value", "Cyber Success");

        propList.add(compNameObj);

        Map<String, String> compUrlObj = new HashMap<>();
        compUrlObj.put("name", "url");
        compUrlObj.put("type", "SYSTEM");
        compUrlObj.put("value", "http://www.cybersuccess.biz/");

        propList.add(compUrlObj);

        companyPojo.setProperties(propList);

        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
                .body(companyPojo)
                .log().all();


    }

    @When("I hit an api to create a company")
    public void iHitAnApiToCreateACompany() {
        response = specification.post("/contacts");
    }

    @Then("I verify company created succussfully using serialization")
    public void iVerifyCompanyCreatedSuccussfullyUsingSerialization() {
        response.prettyPrint();

        String actualType = response.jsonPath().getString("type");
        String expectedType = companyPojo.getType();

        Assert.assertEquals(expectedType, actualType);

        //get the properties values

        List<Map<String, String>> actualPropList = response.jsonPath().getList("properties");

        for (Map<String, String> actPropObject : actualPropList) {

            String actualName = actPropObject.get("name");
            String actualValue = actPropObject.get("value");

            List<Map<String, String>> expectedPropList = companyPojo.getProperties();

            for (Map<String, String> expPropObject : expectedPropList) {
                String expectedName = expPropObject.get("name");
                String expectedValue = expPropObject.get("value");

                if (actualName.equals(expectedName)) {
                    Assert.assertEquals(expectedValue, actualValue);

                }
            }

        }
    }

    @When("I update the name and url of a company")
    public void iUpdateTheNameAndUrlOfACompany() {
        companyPojo = new CreateCompanyPojo();
        Faker faker = new Faker(new Random());
        companyPojo.setId(companyResponse.getId());

        List<Map<String, String>> propList = new ArrayList<>();

        Map<String, String> nameObject = new HashMap<>();
        nameObject.put("name", "name");
        nameObject.put("type", "SYSTEM");
        nameObject.put("value", faker.company().name());

        Map<String, String> urlObj = new HashMap<>();
        urlObj.put("name", "url");
        urlObj.put("type", "SYSTEM");
        urlObj.put("value", faker.company().url());

        propList.add(nameObject);
        propList.add(urlObj);

        companyPojo.setProperties(propList);

        specification = RestAssured.given();
        response = specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/contacts")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
                .body(companyPojo)
                .log().all().put("/edit-properties");


    }

    @Then("I verify updated company information in response")
    public void iVerifyUpdatedCompanyInformationInResponse() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());

        companyResponse = response.as(CreateCompanyResponsePojo.class);

        List<Map<String, String>> properties = companyResponse.getProperties();

        for (Map<String, String> mapObject : properties) {


            if (mapObject.get("name").equals("name")) {
                String actualName = mapObject.get("value");

                List<Map<String, String>> expectedPropList = companyPojo.getProperties();
                for (Map<String, String> expMapObject : expectedPropList) {

                    if (expMapObject.get("name").equals("name")) {
                        String expectedName = expMapObject.get("value");
                        Assert.assertEquals(expectedName, actualName);
                        System.out.println("successfully verified the updated name of company");
                    }
                }
            }
        }


    }

    @Then("I verify company created successfully using deserialization")
    public void iVerifyCompanyCreatedSuccessfullyUsingDeserialization() {

        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());

        companyResponse = response.as(CreateCompanyResponsePojo.class);

        //validation......
    }

    @And("I verify updated company information in get all api response")
    public void iVerifyUpdatedCompanyInformationInGetAllApiResponse() {
        specification = RestAssured.given();
        response = specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/contacts")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
                .log().all().post("/companies/list");

        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());

        //int a [] = [1,3,4,5,6,7,7]

        CreateCompanyResponsePojo[] getAllCompResponse = response.as(CreateCompanyResponsePojo[].class);

        for (CreateCompanyResponsePojo companyObject : getAllCompResponse) {

            Long actualId = companyObject.getId();

            Long expectedId = companyResponse.getId();

            if (expectedId == actualId) {
                for (Map<String, String> mapObject : companyObject.getProperties()) {
                    if (mapObject.get("name").equals("name")) {
                        String actualName = mapObject.get("value");

                        List<Map<String, String>> expectedPropList = companyPojo.getProperties();
                        for (Map<String, String> expMapObject : expectedPropList) {

                            if (expMapObject.get("name").equals("name")) {
                                String expectedName = expMapObject.get("value");
                                Assert.assertEquals(expectedName, actualName);
                                System.out.println("successfully verified the updated name of company");
                            }
                        }
                    }
                }
            }
        }
    }

    @When("I delete the company")
    public void iDeleteTheCompany() {
        specification = RestAssured.given();
        response = specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/contacts")
                .pathParam("companyId", companyResponse.getId())
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
                .log().all().delete("{companyId}");
        Assert.assertEquals(204, response.getStatusCode());

    }

    @Then("I verify company deleted succesfully")
    public void iVerifyCompanyDeletedSuccesfully() {

        specification = RestAssured.given();
        response = specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/contacts")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Content-Type", ContentType.JSON)
                .header("Accept", ContentType.JSON)
                .log().all().post("/companies/list");

        List<Long> allCompIds = response.jsonPath().getList("id");

        System.out.println("All company Ids: " + allCompIds);
        System.out.println("Deleted Company Id: " + companyResponse.getId());

        Assert.assertFalse(allCompIds.contains(companyResponse.getId()));

    }

    @Given("I get the contact information from mock server")
    public void getInfoFromMockServer() {

        specification = RestAssured.given();
        response = specification.baseUri("http://localhost:8081")
                .basePath("/contact")
                .header("Accept", ContentType.JSON)
                .log().all().get();
        response.prettyPrint();
    }

    @Given("I create a company with valid info")
    public void iCreateACompanyWithValidInfo() throws IOException, ParseException {
        String filePath = System.getProperty("user.dir")+ "/src/test/resources/RequestFiles/CreateCompanyRequest.json";
        File file= new File(filePath);
        FileReader fileReader = new FileReader(file);

        //create an instance of JSONParser
        JSONParser jsonParser = new JSONParser();

        Object inputObject = jsonParser.parse(fileReader);

        //convert inputObject into JSONObject using type casting
        JSONObject jsonObject = (JSONObject) inputObject;

        JSONArray propArray = (JSONArray) jsonObject.get("properties");

        //get the name object present at index 1 in properties array
        JSONObject nameObject = (JSONObject) propArray.get(1);
        JSONObject urlObject = (JSONObject) propArray.get(2);

        compName = nameObject.get("value").toString();
        compUrl = urlObject.get("value").toString();
        response = commonFunctions.postWithBody(file, "company");

    }
}
