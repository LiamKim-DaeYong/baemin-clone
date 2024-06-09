package com.demin.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaConfig : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        // 추후 토큰에서 인증정보 추출
        return Optional.of("system")
    }
}