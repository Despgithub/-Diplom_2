package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponse {
    private Boolean success;
    private List<Object> orders;
    private Integer total;
    private Integer totalToday;
}
