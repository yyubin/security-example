package practice.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import practice.security.domain.Member;
import practice.security.dto.MemberDto;
import practice.security.repository.MemberRepository;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MockMvc mvc;


    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

        MemberDto memberDto = new MemberDto();
        memberDto.setUsername("yubin");
        memberDto.setPassword("123");

        memberRepository.save(memberDto.toEntity());
    }

    @Test
    @Transactional
    public void signup() {
        Optional<Member> user = memberRepository.findByUsername("yubin");
        Assertions.assertEquals("yubin", user.get().getUsername());
    }

    @Test
    @Transactional
    public void signin() throws Exception {

        MemberDto memberDto = new MemberDto();
        memberDto.setUsername("yubin");
        memberDto.setPassword("123");

        mvc.perform(formLogin("/member/login").user(memberDto.getUsername()).password(memberDto.getPassword()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());


    }


}
