package com.market.repository;

import com.market.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Member findByNameAndPhone(String name, String phone);

    Member findByNameAndEmail(String name, String email);
}
