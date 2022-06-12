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
