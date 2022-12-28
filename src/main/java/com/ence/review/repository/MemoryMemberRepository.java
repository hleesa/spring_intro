package com.ence.review.repository;

import com.ence.review.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    /**
     * 어딘가에 저장해야하니까 (MemoryRepository) Map을 사용하겠다.
     * 실무에서는 동시성 문제가 발생할 수 있다.
     * 공유되는 변수는 concurrent HashMap을 써야한다.
     * 예제니까 단순하게 HashMap을 사용.
     */
    private static Map<Long, Member> store = new HashMap<>();
    /**
     * 키값을 생성해주는 변수
     * 실무에서는 동시성 문제를 고려해서 atom long 등을 사용해야 한다.
     * 예제에서 단순하게 long으로 사용.
     */
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /**
         * store에서 꺼낸 결과가 없을 수 있다.
         * null이 반환될 가능성이 있으면  optional로 감싼다.
         */
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        /**
         * java 8 람다
         * loop를 돌리면서
         * 필터의 내용이 같으면
         * findAny : 하나라도 찾는 것. 결과가 optional로 반환된다.
         */
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        /**
         * 실무에서 List를 많이 사용한다.
         */
        return new ArrayList<>(store.values());
    }

    /**
     * 동작을 검증하기 위해 테스트 케이스를 작성한다.
     */

    /**
     * afterEach()에서 사용할 메소드
     */
    public void clearStore() {
        store.clear();
    }
}
