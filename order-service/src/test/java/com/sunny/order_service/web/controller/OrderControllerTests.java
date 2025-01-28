package com.sunny.order_service.web.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

import com.sunny.order_service.AbstractIntegrationTest;
import com.sunny.order_service.domain.models.OrderSummary;
import com.sunny.order_service.testdata.TestDataFactory;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-orders.sql")
class OrderControllerTests extends AbstractIntegrationTest {

    @Test
    void shouldCreateOrderSuccessfully() {
        mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
        var payload =
                """
                        {
                            "customer" : {
                                "name": "Siva",
                                "email": "siva@gmail.com",
                                "phone": "999999999"
                            },
                            "deliveryAddress" : {
                                "addressLine1": "HNO 123",
                                "addressLine2": "Kukatpally",
                                "city": "Hyderabad",
                                "state": "Telangana",
                                "zipCode": "500072",
                                "country": "India"
                            },
                            "items": [
                                {
                                    "code": "P100",
                                    "name": "Product 1",
                                    "price": 25.50,
                                    "quantity": 1
                                }
                            ]
                        }
                    """;
        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("orderNumber", notNullValue());
    }

    @Test
    void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
        var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldGetOrdersSuccessfully() {
        List<OrderSummary> orderSummaries = given().when()
                .get("/api/orders")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<>() {});

        assertThat(orderSummaries).hasSize(2);
    }

        @Test
        void shouldGetOrderNumberSuccessfully() {
            String orderNumber = "order-123";
            given().when()
                    .get("/api/orders/{orderNumber}", "order-123")
                    .then()
                    .statusCode(200)
                    .body("orderNumber", is("order-123"))
                    .body("items.size()", is(0));
        }
}
