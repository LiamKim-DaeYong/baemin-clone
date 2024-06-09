package com.demin.auth.adapter.out.persistence

import com.demin.auth.application.port.out.FindMemberPort
import com.demin.auth.application.port.out.SaveMemberPort
import com.demin.auth.application.port.out.UpdateMemberPort
import com.demin.auth.domain.Member
import com.demin.auth.mapper.toEntity
import com.demin.auth.mapper.toMember
import com.demin.core.hexagonal.annotations.PersistenceAdapter

@PersistenceAdapter
class MemberPersistenceAdapter(
    private val jpaMemberRepository: JpaMemberRepository
): SaveMemberPort, UpdateMemberPort, FindMemberPort {
    override fun save(member: Member): Member {
        val memberEntity = member.toEntity()
        val savedEntity = jpaMemberRepository.save(memberEntity)
        return savedEntity.toMember()
    }

    override fun update(member: Member): Member {
        val memberEntity = member.toEntity()
        val updatedEntity = jpaMemberRepository.save(memberEntity)
        return updatedEntity.toMember()
    }

    override fun findById(memberId: String): Member? {
        return jpaMemberRepository.findById(memberId).orElse(null)?.toMember()
    }

    override fun findAll(): List<Member> {
        return jpaMemberRepository.findAll().map { it.toMember() }
    }
}