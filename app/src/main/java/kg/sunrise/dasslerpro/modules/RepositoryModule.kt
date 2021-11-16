package kg.sunrise.dasslerpro.modules

import kg.sunrise.dasslerpro.data.repositories.ApplicationInfoRepository
import kg.sunrise.dasslerpro.data.repositories.AuthorizationRepository
import kg.sunrise.dasslerpro.data.repositories.InfoRepository
import kg.sunrise.dasslerpro.data.repositories.PromotionRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ApplicationInfoRepository(get()) }
    single { PromotionRepository(get()) }
    single { AuthorizationRepository(get()) }
    single { InfoRepository(get()) }
}