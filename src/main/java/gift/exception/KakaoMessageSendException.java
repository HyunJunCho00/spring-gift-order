package gift.exception;

public class KakaoMessageSendException extends KakaoAuthenticationException {
    public KakaoMessageSendException(String message,Throwable cause) {
        super(message,cause);
    }
}
