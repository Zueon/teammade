package com.mztm.teammade.persistence;

import com.mztm.teammade.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);
    Member findByEmailAndPassword(String email, String password);
}
