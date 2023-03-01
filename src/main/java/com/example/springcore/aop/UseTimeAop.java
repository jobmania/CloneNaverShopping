package com.example.springcore.aop;

import com.example.springcore.apiusetime.ApiUseTime;
import com.example.springcore.apiusetime.ApiUseTimeRepository;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.user.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect  // Aop
@Component  // 빈 클래스만 가능 .
public class UseTimeAop {
    private final ApiUseTimeRepository apiUseTimeRepository;

    public UseTimeAop(ApiUseTimeRepository apiUseTimeRepository) {
        this.apiUseTimeRepository = apiUseTimeRepository;
    }

    @Around("execution(public * com.example.springcore.controller..*(..))" )  //적용대상 설정..(패키지명)
                    //  퍼블릭, 모든 리턴타입(*), 클래스타입(패키지명 필요 com.example.) ,
                    // .* 모든 클래스적용,    .. 하위 패키지 모든 클래스 ㅈ거용
                    // (..) 파라미터 패턴  () 인수없음, (*) 인수1개 , (..) 인수 0 ~ N개
    // 포인트 컷 문법 : excution
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
// 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
// 핵심기능 수행
            Object output = joinPoint.proceed();  // 여기서 첫번째로 들어오고
            return output; // controller단 리턴ㅇ 이 일루온드ㅏ,
        } finally {
// 측정 종료 시간
            long endTime = System.currentTimeMillis();
// 수행시간 = 종료 시간 - 시작 시간
            long runTime = endTime - startTime;

// 로그인 회원이 없는 경우, 수행시간 기록하지 않음
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
// 로그인 회원 정보
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User loginUser = userDetails.getUser();

// API 사용시간 및 DB 에 기록
                ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser)
                        .orElse(null);
                if (apiUseTime == null) {
// 로그인 회원의 기록이 없으면
                    long count = 1;
                    apiUseTime = new ApiUseTime(loginUser, runTime, count);
                } else {
// 로그인 회원의 기록이 이미 있으면
                    apiUseTime.addUseTime(runTime);
                    apiUseTime.addUseCount();
                }

                System.out.println("[API Use Time] Username: " + loginUser.getUsername() + ", Total Time: " + apiUseTime.getTotalTime() + " ms");
                apiUseTimeRepository.save(apiUseTime);
            }
        }
    }
}