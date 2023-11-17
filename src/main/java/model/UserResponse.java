package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String email;
        private String name;
    }

}
