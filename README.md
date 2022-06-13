## 프로젝트 환경설정

### 빌드하고 실행하기
```
-- 디렉토리 이동
D:
cd D:\workspace\hello-spring
```
```
-- 프로젝트 빌드
./gradlew build
```
```
-- 빌드 파일 확인 후 실행
cd build/libs
java -jar hello-spring-0.0.1-SNAPSHOT.jar
```

---
  
## 스프링 웹 개발 기초

### 정적 컨텐츠
> 파일을 그대로 웹 브라우저에서 전달  
> 어떤 프로그래밍은 할 수 없음

resources/static/hello-static.html → http://localhost:8080/hello-static.html  
hello-static 관련 컨트롤러가 없기 때문에 리소스에 있는 hello-static.html을 반환

### MVC와 템플릿 엔진
> 서버에서 변형하여 HTML에 내려주는 방식

MVC : Model, View, Controller  
View : 화면을 그리는데만 집중  
Controller : 비즈니스 로직 수행  
http://localhost:8080/hello-mvc?name=Puter

### API
> 앱 클라이언트와 JSON으로 통신
> 서버끼리 통신할 때

`@ResponseBody`를 사용하면 `viewResolver`를 사용하지 않고 직접 반환함   
http://localhost:8080/hello-string?name=Puter

`@ResponseBody`를 사용하고, 객체를 반환하면 객체가 `JSON`으로 변환됨  
`viewResolver`대신 `HttpMessageConverter`가 동작됨    
http://localhost:8080/hello-api?name=Puter

---

## 회원 관리 예제 - 백엔드 개발

### 비즈니스 요구사항 정리
#### 요구사항
> * 데이터 : 회원ID, 이름  
> * 기능 : 회원 등록, 조회  
> * 아직 데이터 저장소가 선정되지 않음 (가상의 시나리오)

#### 일반적인 웹 애플리케이션 계층 구조
> * 컨트롤러 : 웹 MVC의 컨트롤러 역할  
> * 서비스 : 핵심 비즈니스 로직 구현  
> * 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리  
> * 도메인 : 비즈니스 도메인 객체 예) 회원, 주문, 쿠폰 등 주로 데이터베이스에 저장하고 관리됨

#### 클래스 의존관계
> * 아직 데이터 저장소가 선정되지 않아서 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계
> * 데이터 저장소는 RDB, NoSQL 등 다양한 저장소를 고민중인 상황으로 가정
> * 개발을 진행하기 위해서 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

