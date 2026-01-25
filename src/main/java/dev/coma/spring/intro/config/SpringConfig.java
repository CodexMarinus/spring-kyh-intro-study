package dev.coma.spring.intro.config;

import dev.coma.spring.intro.repository.MemberRepository;
import dev.coma.spring.intro.repository.MemoryMemberRepository;
import dev.coma.spring.intro.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
}
