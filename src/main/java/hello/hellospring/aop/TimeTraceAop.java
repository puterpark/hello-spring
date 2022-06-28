package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

	/**
	 * hello.hellospring 하위 패키지 모두 적용
	 *
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
//	@Around("execution(* hello.hellospring..*(..))")
	@Around("execution(* hello.hellospring.service..*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		System.out.println("START : " + joinPoint.toString());

		try {
			return joinPoint.proceed();// 다음 메서드로 진행
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
		}

	}
}
