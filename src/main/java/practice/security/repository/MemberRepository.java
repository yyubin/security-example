package practice.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.security.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

}
