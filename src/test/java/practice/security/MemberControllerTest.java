package practice.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import practice.security.domain.Member;
import practice.security.dto.MemberDto;
import practice.security.repository.MemberRepository;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Transactional
    public void signup() {
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername("yubin");
        memberDto.setPassword("123");
        bCryptPasswordEncoder.encode(memberDto.getPassword());

        memberRepository.save(memberDto.toEntity());

        Optional<Member> user = memberRepository.findByUsername("yubin");
        Assertions.assertEquals("yubin", user.get().getUsername());
    }


}
