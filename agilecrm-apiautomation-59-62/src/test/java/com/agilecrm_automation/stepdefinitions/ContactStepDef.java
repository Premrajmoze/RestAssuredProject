package com.agilecrm_automation.stepdefinitions;

import com.agilecrm_automation.common.CommonFunctions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.*;

public class ContactStepDef {
    RequestSpecification specification;
    Response response;
    List<Long> idList;
    CommonFunctions commonFunctions = new CommonFunctions();
    Map<String,String> queryParams = new HashMap<>();

    @Given("I setup request structure to get all contacts")
    public void setup() {
        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Accept", "application/json")
                .log().all();

    }

    @Given("I get contacts information")
    public void iGetContactsInformation() {
        response = commonFunctions.getResponse("contact");

    }

    @When("I hit an get all contact api")
    public void iHitAnGetAllContactApi() {

        response = specification.get("/contacts");
    }

    @Then("I verify all contacts in response")
    public void iVerifyAllContactsInResponse() {
        response.prettyPrint();

        //verify status code
        Assert.assertEquals(200, response.getStatusCode());

        //get all contact ids in list
        idList = response.jsonPath().getList("id");

        System.out.println(idList);

    }

    @When("I hit an get contact by id api")
    public void iHitAnGetContactByIdApi() {
        if (!idList.isEmpty()) {
            response = specification.get("/contacts/" + idList.get(0));
        } else {
            response = specification.get("/contacts/6600727162847232");

        }
    }

    @Then("I verify contact by id in response")
    public void iVerifyContactByIdInResponse() {

        Assert.assertEquals(200, response.getStatusCode());

        //verify contact id in response

        long actualId = response.jsonPath().getLong("id");

        System.out.println(actualId);
    }

    @When("I setup request structure to get contact by id")
    public void iSetupRequestStructureToGetContactById() {
        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api")
//                .pathParam("", "")
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Accept", "application/json")
                .log().all();

        if (idList != null && !idList.isEmpty()) {
            long pathParam = idList.get(1);
            specification.pathParam("contactId", pathParam);
        }

    }

    @And("I hit an get contact by id api with path param")
    public void iHitAnGetContactByIdApiWithPathParam() {
        response = specification.get("/contacts/{contactId}");
    }

    @Given("I setup request structure to get all users info using dynamic filters")
    public void iSetupRequest() {
        Map<String, String> formBody = new HashMap<>();
        formBody.put("page_size", "10");
        formBody.put("global_sort_key", "-created_time");
        formBody.put("filterJson", " {\"contact_type\": \"PERSON\"}");

        specification = RestAssured.given();
        specification.baseUri("https://apiautomation.agilecrm.com")
                .basePath("/dev/api/filters/filter")
                .header("Content-Type", ContentType.URLENC)
                .formParams(formBody)
                .auth().basic("api_test@yopmail.com", "mfgk0li4cmbvv65aln6j5l52gt")
                .header("Accept", "application/json")
                .log().all();

    }

    @When("I hit an api to get the response")
    public void iHitAnApiToGetTheResponse() {
        response = specification.post("/dynamic-filter");
    }

    @Then("I verify response is getting sorted in descending order")
    public void iVerifyResponseIsGettingSortedInDescendingOrder() {
        response.prettyPrint();

        Assert.assertEquals(200, response.getStatusCode());

        List<Integer> timeList = response.jsonPath().getList("created_time");

        System.out.println(timeList);

        List<Integer> expectedTimeList = timeList;

        Comparator<Integer> descdingComp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        Collections.sort(expectedTimeList, Collections.reverseOrder(descdingComp));

        System.out.println("Desc Order : " + expectedTimeList);

        for (int i = 0; i < expectedTimeList.size(); i++) {

            Assert.assertEquals(expectedTimeList.get(i), timeList.get(i));
        }

    }


    @Given("I get contact information by id")
    public void iGetContactInformationById() {

       response = commonFunctions.getResponseWithPathParam("contact", "4671829567143936");
       response.prettyPrint();

    }

    @Given("I get all contacts with page size query param")
    public void iGetAllContactsWithPageSizeQueryParam() {

        queryParams.put("page_size", "2");
        response= commonFunctions.getResponseWithQueryParams(queryParams, "contact");

    }

    @Then("I verify response according to query param")
    public void iVerifyResponseAccordingToQueryParam() {
        response.prettyPrint();
        Assert.assertEquals(200, response.getStatusCode());

        List<Object> responseList =response.jsonPath().getList("");

        Integer expectedSize = Integer.parseInt(queryParams.get("page_size"));

        Integer actualSize =Integer.valueOf(responseList.size());

        Assert.assertEquals(expectedSize,actualSize);

    }
}
