package com.demin.auth.application.port.incoming.storeowner

import com.demin.auth.application.port.incoming.storeowner.command.UpdateStoreOwnerInfoCommand
import com.demin.auth.domain.StoreOwner
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface UpdateStoreOwnerUseCase {
    fun updateStoreOwnerInfo(
        storeOwnerId: String,
        command: UpdateStoreOwnerInfoCommand,
    ): StoreOwner
}
