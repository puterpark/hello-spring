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

### 회원 리포지토리 테스트 케이스 작성
> 개발한 기능을 실행해서 테스트 할 때 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의
컨트롤러를 통해서 해당 기능을 실행한다.  
> 이러한 방법은 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어렵다는 단점이 있다.  
> 자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.

`@AfterEach` : 각 테스트가 종료될 때마다 수행
> 테스트는 각각 독립적으로 실행되어야 한다.  
> 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다.

### 회원 서비스 테스트
`@BeforeEach` : 각 테스트가 실행되기 전마다 수행
> 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다.

---

## 스프링 빈과 의존관계
### 컴포넌트 스캔과 자동 의존관계 설정
> 생성자에 `@Autowired`가 있으면 스프링이 연관된 객체를 스프링 컨테이너에 찾아서 넣어준다.  
> 객체 의존관계를 외부에서 넣어주는 것을 `의존성 주입`이라 한다.

스프링 빈을 등록하는 2가지 방법  
> 컴포넌트 스캔과 자동 의존관계 설정  
> 자바 코드로 직접 스프링 빈 등록하기

컴포넌트 스캔 원리
> `@Component`가 있으면 스프링 빈으로 자동 등록  
> `@Controller`가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문  
> `@Component`를 포함하는 다음 어노테이션도 스프링 빈으로 자동 등록됨  
>  `@Controller`, `@Service`, `@Repository`

> *[참고]*  
> 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다.  
> (유일하게 하나만 등록해서 공유한다)  
> 따라서 같은 스프링 빈이면 모두 같은 인스턴스다.   
> 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.

### 자바 코드로 직접 스프링 빈 등록하기
생성자 주입 (권장)
```
private final MemberService memberService;

@Autowired
public MemberController(MemberService memberService) {
    this.memberService = memberService;
}
```

필드 주입
```
@Autowired private MemberService memberService;
```

setter 주입
```
private MemberService memberService;

@Autowired
public void setMemberController(MemberService memberService) {
    this.memberService = memberService;
}
```

> *참고*  
> 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용  
> 정형화되지 않거나 상황에 따라 구현 클래스를 변경해야하면 설정을 통해 스프링 빈으로 등록

> *주의*  
> `@Autowired`를 통한 DI는 스프링이 관리하는 객체에서만 동작  
> 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.

---

## 회원 관리 예제 - 웹 MVC 개발
### 회원 웹 기능 - 홈 화면 추가

> *참고*  
> 컨트롤러가 정적 파일보다 우선순위가 높다.

### 회원 웹 기능 - 등록

* GET - 조회
* POST - 등록

---

## 스프링 DB 접근 기술
### H2 데이터베이스 설치

개발이나 테스트 용도로 가볍고 편리한 DB, 웹 화면 제공

다운로드 : https://h2database.com/h2-2019-10-14.zip

```
{dir}\h2\bin\h2.bat 실행  
test.mv.db 파일 생성 확인 (C:\Users\{user}에 있음)
이후 `jdbc:h2:tcp://localhost/~/test`로 연결
```

### 스프링 통합 테스트

`@SpringBootTest` : 스프링 컨테이너와 테스트를 함께 실행  
`@Transactional` : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
테스트 완료 후에 항상 롤백한다.

> 순수한 단위테스트가 좋은 테스트일 확률이 높다.

### 스프링 JdbcTemplate

> 클래스 내에 생성자가 한 개만 있으면 `@Autowired`의 생략이 가능하다.

### JPA

JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.  
JPA를 사용하면 SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다.  
JPA를 사용하면 개발 생산성을 크게 높일 수 있다.

#### application.properties
`show-sql` : JPA가 생성하는 SQL 출력  
`ddl-auto` : JPA는 테이블을 자동으로 생성 기능 제공 (none : 해당 기능 사용안함, create : 사용)

> *참고*  
> JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.  
> `@Transactional` 사용

### 스프링 데이터 JPA

스프링 데이터 JPA가 `SpringDataJpaMemberRepository`를 스프링 빈으로 자동 등록해준다.

#### 제공 기능
> 인터페이스를 통한 기본적인 CRUD  
> `findByName()`, `findByEmail()`처럼 메서드 이름 만으로 조회 기능 제공  
> 페이징 기능 자동 제공

> *참고*  
> 실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 Querydsl를 사용  
> Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성 가능  
> 위의 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나 스프링 JdbcTemplate을 사용

