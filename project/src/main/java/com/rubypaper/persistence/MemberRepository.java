package com.rubypaper.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubypaper.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserid(String userid);
}
