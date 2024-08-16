package com.interverse.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, ClubMemberId> {

}
