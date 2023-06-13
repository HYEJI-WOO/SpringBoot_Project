package com.market.service;

import com.market.constant.Gender;
import com.market.dto.MemberFormDto;
import com.market.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress1("서울시 마포구 합정동");
        memberFormDto.setAddress2("서울아파트 101동 512호");
        memberFormDto.setPhone("01012345678");
        memberFormDto.setBirthYear(1999);
        memberFormDto.setBirthMonth(10);
        memberFormDto.setBirthDay(30);
        memberFormDto.setGender(Gender.FEMALE);
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getAddress1(), savedMember.getAddress1());
        assertEquals(member.getRole(), savedMember.getRole());
        assertEquals(member.getBirthDay(), savedMember.getBirthDay());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    public void updateMemberTest() {
        // Given
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        // When
        savedMember.setName("이순신");
        savedMember.setPhone("01098765432");
        memberService.update(savedMember);

        // Then
        Member updatedMember = memberService.getMemberByUsername(savedMember.getEmail());
        assertEquals(savedMember.getName(), updatedMember.getName());
        assertEquals(savedMember.getPhone(), updatedMember.getPhone());
    }

}