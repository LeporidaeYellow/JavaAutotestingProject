import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PdfUtils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Story("Download API")
class DownloadAndUploadApiTests {
    PdfUtils pdfUtils = new PdfUtils();

    @Test
    @DisplayName("Check upload file")
    void uploadTest() {
        int petId = 1000;
        String url = String.format("https://petstore.swagger.io/v2/pet/%s/uploadImage", petId);
        String fileName = "Selenium.png";
        File file = new File(fileName);

        given().
            when().
                multiPart("file", file, "image/png").
                contentType("multipart/form-data").
                accept("application/json").
                post(url).
            then().
                log().
                all().
                assertThat().
                contentType(ContentType.JSON).
                statusCode(200).
                body("code", equalTo(200)).
                body("type", equalTo("unknown")).
                body("message", containsString("File uploaded to ./" + fileName));
    }

    @Test
    @DisplayName("Check download file")
    void downloadTest() {
        String endpoint = "https://alfabank.servicecdn.ru/site-upload/67/dd/356/zayavlenie-IZK.pdf";
        String fileName = "downloaded.pdf";

        Response response =
        given().
                when().
                        get(endpoint).
                then().
                        contentType("application/pdf").
                        statusCode(200).
                        extract().response();
        pdfUtils.savePdf(response, fileName);
        String pdfText = pdfUtils.readPdf(fileName);
        assertThat(pdfText).contains("Чтобы стать индивидуальным зарплатным клиентом,")
                .contains("нужно получать на карту зарплату от 30 000 ₽ в месяц");
    }
}
