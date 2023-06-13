package com.market.entity;

import com.market.constant.Gender;
import com.market.constant.Role;
import com.market.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String address1;

    private String address2;

    private Integer birthYear;

    private Integer birthMonth;

    private Integer birthDay;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime regDate;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){

        memberFormDto.setRegDate(LocalDateTime.now());

        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPhone(memberFormDto.getPhone());
        member.setAddress1(memberFormDto.getAddress1());
        member.setAddress2(memberFormDto.getAddress2());
        member.setBirthYear(memberFormDto.getBirthYear());
        member.setBirthMonth(memberFormDto.getBirthMonth());
        member.setBirthDay(memberFormDto.getBirthDay());
        member.setGender(memberFormDto.getGender());
        member.setRegDate(memberFormDto.getRegDate());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }

}
