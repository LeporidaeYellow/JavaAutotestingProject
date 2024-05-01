package controllers;

import configurations.TestConfig;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import io.restassured.specification.RequestSpecification;
import models.Pet;

import static io.restassured.RestAssured.given;
import static testdata.ApiTestData.DEFAULT_PET;

public class FluentPetController {
    TestConfig config = new TestConfig();
    RequestSpecification requestSpecification = given();

    public FluentPetController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(config.getBaseUrl());
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add default pet")
    public HttpResponse addDefaultPet() {
        this.requestSpecification.body(DEFAULT_PET);
        return new HttpResponse(given(this.requestSpecification).post("pet").then());
    }

    @Step("Add pet")
    public HttpResponse addPet(Pet pet) {
        this.requestSpecification.body(pet);
        return new HttpResponse(given(this.requestSpecification).post("pet").then());
    }

    @Step("Get pet by id")
    public HttpResponse getPetById(Long id) {
        return new HttpResponse(given(this.requestSpecification).get(String.format("pet/" + id)).then());
    }

    @Step("Delete pet by id")
    public HttpResponse deletePetById(Long id) {
        return new HttpResponse(given(this.requestSpecification).delete(String.format("pet/" + id)).then());
    }
}

