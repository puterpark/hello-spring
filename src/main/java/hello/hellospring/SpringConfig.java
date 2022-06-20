package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

	private DataSource dataSource;

	@Autowired
	public SpringConfig(DataSource dataSource) {
		/**
		 * DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체다.
		 * 스프링 부트는 데이터베이스 커넥션 정보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다.
		 * 그래서 DI를 받을 수 있다.
		 */
		this.dataSource = dataSource;
	}

	/* 자바 코드로 직접 스프링 빈 등록하기 (서비스와 리포지토리를 등록) */

	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository() {
//		return new MemoryMemberRepository();
		/**
		 * 해당 부분만 바꿔서 H2 DB와 연결되도록 할 수 있다.
		 * 기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경할 수 있다.
		 */
		return new JdbcMemberRepository(dataSource);
	}

}
