package com.demin.auth.application.port.incoming.storeowner

import com.demin.auth.application.port.incoming.storeowner.command.RegisterStoreOwnerCommand
import com.demin.auth.domain.StoreOwner
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface RegisterStoreOwnerUseCase {
    fun registerStoreOwner(command: RegisterStoreOwnerCommand): StoreOwner
}
