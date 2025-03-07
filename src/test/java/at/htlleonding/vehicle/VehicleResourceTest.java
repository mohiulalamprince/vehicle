package at.htlleonding.vehicle;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class VehicleResourceTest {

    @Test
    public void testHelloEndpoint() {

        List<VehicleDto> expectedVehicles = List.of(
                new VehicleDto(2L, "VW", "Käfer"),
                new VehicleDto(1L, "Opel", "Kadett")
        );

        List<VehicleDto> retrievedVehicles = new LinkedList<>();
        retrievedVehicles = given()
                .when().get("/vehicle")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", VehicleDto.class);

        assertThat(retrievedVehicles).hasSize(2)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringCollectionOrder()
                .isEqualTo(expectedVehicles);
    }

}