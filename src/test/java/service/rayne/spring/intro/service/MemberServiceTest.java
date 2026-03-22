package service.rayne.spring.intro.service;

import service.rayne.spring.intro.domain.Member;
import service.rayne.spring.intro.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

  MemberService memberService;
  MemoryMemberRepository memberRepository;

  @BeforeEach
  public void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
  }

  @AfterEach
  public void afterEach() {
    memberRepository.clearStore();
  }

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

//    try {
//      memberService.join(member2);
//      fail();
//    } catch (IllegalStateException e) {
//      Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//    }

  }

  @Test
  void findMembers() {
  }

  @Test
  void findOne() {
  }
}