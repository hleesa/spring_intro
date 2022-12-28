package com.ence.review.repository;

import com.ence.review.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    /**
     * optional
     * java 8에서 추가된 기능
     * find로 찾은 객체가 null일 수 있다.
     * null을 처리하는 방법중에 optional로 감싸서 반환하는 방법을 많이 사용한다.
     */
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    List<Member>findAll();

}
