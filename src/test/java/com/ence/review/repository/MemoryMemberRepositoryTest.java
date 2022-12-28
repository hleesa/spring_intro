package com.ence.review.repository;

import com.ence.review.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * 클래스 단위에서 테스트를 모두 실행할때,
 * 테스트 메소드 순서가 보장되지 않는다.
 * 모든 테스트는 순서와 상관없이 따로 동작하게 만들어야 한다.
 * 순서에 의존적으로 설계하면 절대 안된다.
 *
 * 테스트가 하나 끝나면 데이터를 클리어해야 한다.
 *
 * 테스트는 서로 의존관계 없이 설계되야 한다.
 * 그래서 저장소나 공용데이터 등을 지워야한다.
 *
 * 검증할 수 있는 테스트 코드를 먼저 작성하고
 * 구현 클래스를 만들면, TDD가 된다.
 */
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /**
     * 아래 메소드의 실행이 끝날때마다 동작하는 콜백 메소드다.
     */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        /**
         * optional 에서 값을 꺼낼 때 get으로 꺼낼 수 있다.
         * get()으로 바로 꺼내는게 좋은 방법은 아니다.
         * 테스트 코드에서는 꺼낼 수도 있다.
         */
        Member result = repository.findById(member.getId()).get();

        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);


    }
}
