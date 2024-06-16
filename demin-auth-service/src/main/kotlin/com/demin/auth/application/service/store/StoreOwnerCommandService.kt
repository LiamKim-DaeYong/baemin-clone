package com.demin.auth.application.service.store

import com.demin.auth.application.port.incoming.storeowner.RegisterStoreOwnerUseCase
import com.demin.auth.application.port.incoming.storeowner.UpdateStoreOwnerUseCase
import com.demin.auth.application.port.incoming.storeowner.command.RegisterStoreOwnerCommand
import com.demin.auth.application.port.incoming.storeowner.command.UpdateStoreOwnerInfoCommand
import com.demin.auth.application.port.outgoing.storeowner.FindStoreOwnerPort
import com.demin.auth.application.port.outgoing.storeowner.SaveStoreOwnerPort
import com.demin.auth.domain.StoreOwner
import com.demin.auth.domain.UserAccount
import com.demin.core.exception.ResourceNotFoundException
import com.demin.core.util.SnowflakeIdGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreOwnerCommandService(
    private val saveStoreOwnerPort: SaveStoreOwnerPort,
    private val findStoreOwnerPort: FindStoreOwnerPort,
    private val passwordEncoder: PasswordEncoder,
    private val snowflakeIdGenerator: SnowflakeIdGenerator,
) : RegisterStoreOwnerUseCase,
    UpdateStoreOwnerUseCase {
    override fun registerStoreOwner(command: RegisterStoreOwnerCommand): StoreOwner {
        with(command) {
            // Encode the password
            val encodedPassword = passwordEncoder.encode(password)

            // Create UserAccount
            val userAccount =
                UserAccount.create(
                    id = UserAccount.UserAccountId(snowflakeIdGenerator.nextId()),
                    email = UserAccount.UserEmail(email),
                    password = UserAccount.UserPassword(encodedPassword),
                    role = UserAccount.UserRole(UserAccount.UserRole.Role.STORE_OWNER),
                )

            // Create Store Owner
            val storeOwner =
                StoreOwner.create(
                    storeOwnerName = StoreOwner.StoreOwnerName(name),
                    storeOwnerAddress = StoreOwner.StoreOwnerAddress(address),
                    storeOwnerPhoneNumber = StoreOwner.StoreOwnerPhoneNumber(phoneNumber),
                    userAccount = userAccount,
                )

            // Save and return the Store Owner
            return saveStoreOwnerPort.save(storeOwner)
        }
    }

    override fun updateStoreOwnerInfo(
        storeOwnerId: String,
        command: UpdateStoreOwnerInfoCommand,
    ): StoreOwner {
        with(command) {
            // Find the Store Owner
            val storeOwner =
                findStoreOwnerPort.findById(storeOwnerId)
                    ?: throw ResourceNotFoundException("Store Owner with ID $storeOwnerId not found")

            // Update Store Owner information
            val updatedStoreOwner =
                storeOwner.updateStoreOwnerInfo(
                    newName = name?.let { StoreOwner.StoreOwnerName(it) },
                    newAddress = address?.let { StoreOwner.StoreOwnerAddress(it) },
                    newPhoneNumber = phoneNumber?.let { StoreOwner.StoreOwnerPhoneNumber(it) },
                )

            // Save and return updated Store Owner
            return saveStoreOwnerPort.save(updatedStoreOwner)
        }
    }
}
