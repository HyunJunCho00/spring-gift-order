package gift.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserInfoResponse(
        Long id,
        Properties properties
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Properties(
            String nickname,
            @JsonProperty("profile_image")
            String profileImage
    ) {
    }
}