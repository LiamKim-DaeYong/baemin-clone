package com.demin.auth.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaMemberRepository : JpaRepository<MemberEntity, String> {
}