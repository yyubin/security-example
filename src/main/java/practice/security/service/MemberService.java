package practice.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.security.domain.Member;
import practice.security.dto.MemberDto;
import practice.security.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberWrapper = memberRepository.findByUsername(username);
        Member member = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
