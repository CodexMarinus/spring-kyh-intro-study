package dev.coma.spring.intro.config;

import dev.coma.spring.intro.repository.*;
import dev.coma.spring.intro.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
  private final MemberRepository memberRepository;

  @Autowired
  public SpringConfig(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository);
  }

//  @Bean
//  public MemberRepository memberRepository() {
//    // return new MemoryMemberRepository();
//    // return new JdbcMemberRepository(dataSource);
//    // return new JdbcTemplateMemberRepository(dataSource);
//    // return new JpaMemberRepository(entityManager);
//  }
}
