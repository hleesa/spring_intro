package com.ence.review.service;

import com.ence.review.domain.Member;
import com.ence.review.repository.MemberRepository;
import com.ence.review.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * repository 클래스의 메소드는 단순히 저장소에 넣었다 빼는 느낌.
 * service 클래스의 메소드는 네이밍이 좀 더 비즈니스에 가깝다.
 * 보통 service 클래스는 비즈니스에 가까운 용어를 사용해야한다.
 *
 * 서비스는 비즈니스에 의존적으로 설계한다.
 * 리포지토리는 기계적으로 개발적으로 용어를 선택한다.
 */


@Transactional
public class MemberService {

    /**
     * 하나의 리포지토리를 사용하기 위해
     * memberRepository 를 직접 new 해서 사용하는 게 아니라
     * 외부에서 넣어주도록 바꾼다.
     */
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        /**
         * optional 값이 null이 아니고 존재한다면
         * optional 로 감싸면, optional 안에 멤버 객체가 있는 것.
         * optional 안에 여러 메소드를 사용할 수 있다.
         * get()으로 바로 꺼낼 수 있지만, 권장하지 않는다.
         * orElseGet() 등을 사용한다.
         *
         * optional 로 바로 반환하는건 (보가에?)좋지 않다.
         */
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
