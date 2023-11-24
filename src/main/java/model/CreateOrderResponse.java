package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {
    private Boolean success;
    private String name;
    private Order order;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Order {
        private Integer number;
        private Integer price;
    }

}
