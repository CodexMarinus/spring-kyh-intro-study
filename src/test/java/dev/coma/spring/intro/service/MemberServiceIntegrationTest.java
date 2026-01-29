package dev.coma.spring.intro.service;

import dev.coma.spring.intro.domain.Member;
import dev.coma.spring.intro.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;

  @Test
  void join() {
    // given
    Member member = new Member();
    member.setName("정유니");

    // when
    Long saveId = memberService.join(member);

    // then
    Member result = memberService.findOne(saveId).get();
    Assertions.assertThat(member.getName()).isEqualTo(result.getName());
  }

  @Test
  void duplicateJoin() {
    // given
    Member member1 = new Member();
    member1.setName("정유니");

    Member member2 = new Member();
    member2.setName("정유니");

    // when
    memberService.join(member1);
    IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

    Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }

  @Test
  void findMembers() {
  }

  @Test
  void findOne() {
  }
}