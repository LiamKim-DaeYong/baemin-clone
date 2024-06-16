package com.demin.auth.adapter.incoming.web

import com.demin.auth.application.port.incoming.customer.FindCustomerUseCase
import com.demin.auth.application.port.incoming.customer.RegisterCustomerUseCase
import com.demin.auth.application.port.incoming.customer.UpdateCustomerUseCase
import com.demin.auth.application.port.incoming.customer.command.RegisterCustomerCommand
import com.demin.auth.application.port.incoming.customer.command.UpdateCustomerInfoCommand
import com.demin.auth.domain.Customer
import com.demin.core.hexagonal.annotations.WebAdapter
import com.demin.core.response.ApiResponse
import com.demin.core.util.UriUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebAdapter
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val findCustomerUseCase: FindCustomerUseCase,
    private val registerCustomerUseCase: RegisterCustomerUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase,
) {
    @GetMapping
    fun findAllCustomers(): ResponseEntity<ApiResponse<List<Customer>>> {
        val customers = findCustomerUseCase.findAllCustomers()
        return ResponseEntity.ok(ApiResponse.success(customers))
    }

    @GetMapping("/{customerId}")
    fun findCustomerById(
        @PathVariable customerId: String,
    ): ResponseEntity<ApiResponse<Customer>> {
        val customer = findCustomerUseCase.findCustomerById(customerId)
        return ResponseEntity.ok(ApiResponse.success(customer))
    }

    @PostMapping
    fun registerCustomer(
        @RequestBody @Valid command: RegisterCustomerCommand,
    ): ResponseEntity<ApiResponse<Customer>> {
        val customer = registerCustomerUseCase.registerCustomer(command)
        val location = UriUtil.createLocationUri("/{customerId}", customer.id.value)

        return ResponseEntity.created(location).body(ApiResponse.success(customer))
    }

    @PatchMapping("/{customerId}")
    fun updateCustomerInfo(
        @PathVariable customerId: String,
        @RequestBody @Valid command: UpdateCustomerInfoCommand,
    ): ResponseEntity<ApiResponse<Customer>> {
        val updatedCustomer = updateCustomerUseCase.updateCustomerInfo(customerId, command)
        return ResponseEntity.ok(ApiResponse.success(updatedCustomer))
    }
}
