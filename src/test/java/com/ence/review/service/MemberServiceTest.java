package com.ence.review.service;

import com.ence.review.domain.Member;
import com.ence.review.repository.MemberRepository;
import com.ence.review.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    /**
     * memberService 가 new MemoryMemberRepository() 해서 memberRepository를 갖고 있다.
     * 여기서 new MemoryMemberRepository()를 하면 두 개의 객체가 생성되서 이상해진다.
     * 두 개를 쓸 이유가 없다.
     * MemoryMemberRepository 에서 store 를 static 으로 선언해서, 인스턴스와 상관없이 클래스 레벨에 붙는다.
     * 그래서 크게 상관없지만, new 로 다른 객체 리포지토리를 생성하면, 다른 인스턴스여서 내용물이 바뀔 가능성이 있다.
     * <p>
     * static 이 아니라면 문제가 발생한다. 두 개의 객체이기 때문.
     * 같은 리포지토리를 테스트하게 맞다.
     * 두 개를 가지고 하면, 다른 리포지토리를 테스트 하는 것이다.
     */
    MemoryMemberRepository memberRepository;

    /**
     * memberService 입장에서보면, repository 를 직접 new 하지 않고, 외부에서 넣어준다.
     * 이런게 Dependency Injection (DI)라고 한다.
     */
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);

    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    /**
     * 테스트는 한글로 작성해도 된다.
     * 빌드할때 테스트 코드는 포함되지 않는다.
     */
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    /**
     * 테스트는 예외 케이스도 중요하다.
     */
    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
/*
        try {
            memberService.join(member2);
            fail("fail");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.1");
        }

 */
        //then

    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}