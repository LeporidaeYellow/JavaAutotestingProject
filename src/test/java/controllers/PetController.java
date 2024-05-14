package controllers;

import configurations.TestConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;


import static io.restassured.RestAssured.given;
import static testdata.ApiTestData.DEFAULT_PET;

public class PetController {
    TestConfig config = new TestConfig();
    RequestSpecification requestSpecification = given();

    public PetController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(config.getBaseUrl());
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add default pet")
    public Response addDefaultPet() {
        this.requestSpecification.body(DEFAULT_PET);
        return given(this.requestSpecification).post("pet").andReturn();
    }

    @Step("Add pet")
    public Response addPet(Pet pet) {
        this.requestSpecification.body(pet);
        return given(this.requestSpecification).post("pet").andReturn();
    }

    @Step("Get pet by name")
    public Response getPetById(Long id) {
        return given(this.requestSpecification).get(String.format("pet/" + id)).andReturn();
    }

    @Step("Delete pet by name")
    public Response deletePetByName(Long id) {
        return given(this.requestSpecification).delete(String.format("pet/" + id)).andReturn();
    }
}
