package com.example.study.componant;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        return Optional.of("AdminServer"); //로그인 시스템이 없어서 일단 기본값설정
    }
}
