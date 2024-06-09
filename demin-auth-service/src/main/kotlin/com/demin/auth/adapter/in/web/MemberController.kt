package com.demin.auth.adapter.`in`.web

import com.demin.auth.application.port.`in`.FindMemberUseCase
import com.demin.auth.application.port.`in`.RegisterMemberUseCase
import com.demin.auth.application.port.`in`.UpdateMemberUseCase
import com.demin.auth.application.port.`in`.command.RegisterMemberCommand
import com.demin.auth.application.port.`in`.command.UpdateMemberCommand
import com.demin.auth.domain.Member
import com.demin.common.hexagonal.annotations.WebAdapter
import com.demin.common.response.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebAdapter
@RequestMapping("/api/v1/members")
class MemberController(
    private val findMemberUseCase: FindMemberUseCase,
    private val registerMemberUseCase: RegisterMemberUseCase,
    private val updateMemberUseCase: UpdateMemberUseCase
) {
    @GetMapping
    fun findAllMembers(): ResponseEntity<ApiResponse<List<Member>>> {
        val members = findMemberUseCase.findAllMembers()
        return ResponseEntity.ok(ApiResponse.success(members))
    }

    @GetMapping("/{memberId}")
    fun findMemberById(@PathVariable memberId: String): ResponseEntity<ApiResponse<Member>> {
        val member = findMemberUseCase.findMemberById(memberId)
        return ResponseEntity.ok(ApiResponse.success(member))
    }

    @PostMapping
    fun registerMember(@RequestBody @Valid command: RegisterMemberCommand): ResponseEntity<ApiResponse<Member>> {
        val member = registerMemberUseCase.registerMember(command)
        return ResponseEntity.ok(ApiResponse.success(member))
    }

    @PutMapping("/{memberId}")
    fun updateMember(
        @PathVariable memberId: String,
        @RequestBody @Valid command: UpdateMemberCommand
    ): ResponseEntity<ApiResponse<Member>> {
        val updatedMember = updateMemberUseCase.updateMember(command.copy(memberId = memberId))
        return ResponseEntity.ok(ApiResponse.success(updatedMember))
    }
}