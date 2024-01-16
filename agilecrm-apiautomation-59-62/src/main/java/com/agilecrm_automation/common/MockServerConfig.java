package com.agilecrm_automation.common;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServerConfig {
    public static WireMockServer mockServer;

    public static void startServer() {
        //create an instance of mock server and define the port num: 8081
        mockServer = new WireMockServer(WireMockConfiguration.options().port(8081));

        //configure host and port
        WireMock.configureFor("localhost", 8081);

        mockServer.start();

        System.out.println("*****************Mock server started on 8081 port successfully*******************");
    }

    public static void stubForGet() {
        String respBody = "{\n" +
                "    \"data\": {\n" +
                "        \"id\": 2,\n" +
                "        \"email\": \"janet.weaver@reqres.in\",\n" +
                "        \"first_name\": \"Janet\",\n" +
                "        \"last_name\": \"Weaver\",\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}";

        WireMock.stubFor(WireMock.get(urlEqualTo("/contact"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", ContentType.JSON.toString())
                        .withBody(respBody)));
    }

    public static void stopServer() {
        mockServer.stop();
        System.out.println("*****wiremock server stopped successfully*****");
    }
}

