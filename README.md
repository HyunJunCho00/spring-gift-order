# spring-gift-order

🎁 주요 구현 내용
카카오 소셜 로그인 기능 구현
OAuth 2.0 기반 인증: 카카오의 OAuth 2.0 인증 서버를 연동하여, Authorization Code Grant Type 기반의 안전한 소셜 로그인 기능을 구현했다.

역할 분리 설계:

KakaoAuthService: 인가 코드를 받아 카카오 서버로부터 액세스 토큰을 발급받는 외부 통신 및 인증 로직을 전담하도록 설계했다.

AuthController: 사용자의 로그인 요청 및 카카오 서버로부터의 콜백을 처리하는 엔드포인트 역할을 수행하도록 구현했다.

Type-Safe 설정 관리: @ConfigurationProperties를 활용한 KakaoProperties 클래스를 도입하여, application.yml에 정의된 설정 정보를 타입에 안전한 방식으로 객체에 바인딩하여 관리하도록 개선했다.

전역 예외 처리 및 커스텀 예외 정의
중앙 집중식 예외 처리: @RestControllerAdvice를 사용한 ApiExceptionHandler를 통해, API 계층에서 발생하는 예외를 일관된 형식으로 처리하도록 구현했다.

커스텀 예외 도입:

KakaoAuthenticationException: 카카오 인증 과정에서 토큰 발급 실패 등 외부 서버와의 통신 오류가 발생했을 때, 이를 명확하게 표현하기 위한 전용 예외를 정의했다. 해당 예외 발생 시 502 Bad Gateway 상태 코드를 반환하여 문제의 원인을 명확히 하도록 했다.

테스트 전략 및 외부 API 모킹
외부 API 의존성 분리: @RestClientTest를 사용하여 KakaoAuthService와 같이 외부 API와 통신하는 계층을 독립적으로 테스트하도록 구성했다.

MockRestServiceServer 활용: 실제 네트워크 요청 없이도, 카카오 인증 서버의 응답을 모의 객체로 만들어 외부 API 호출 로직의 정확성을 안정적으로 검증했다.

📂 프로젝트 구조
카카오 로그인 기능 추가 이후, 역할과 책임에 따라 패키지 구조가 더욱 명확해졌다.

└── src
├── main
│   ├── java
│   │   └── gift
│   │       ├── config
│   │       │   ├── AppConfig.java      // RestTemplate 등 공용 Bean 설정
│   │       │   └── kakao               // 카카오 관련 설정 클래스
│   │       │       └── KakaoProperties.java
│   │       ├── controller
│   │       │   ├── AuthController.java // 로그인/인증 관련 엔드포인트
│   │       │   └── api
│   │       │       └── ApiExceptionHandler.java
│   │       ├── dto
│   │       │   └── kakao               // 카카오 API 응답 DTO
│   │       ├── exception
│   │       │   └── KakaoAuthenticationException.java // 커스텀 예외
│   │       └── service
│   │           └── KakaoAuthService.java // 카카오 인증 비즈니스 로직
│   └── resources
│       ├── templates
│       │   └── auth-success.html     // 인증 성공 시 토큰 전달 뷰
│       └── application.yml           // 전체 애플리케이션 설정
└── test
└── java
└── gift
└── service
└── KakaoAuthServiceTest.java // @RestClientTest 활용
