package kg.sunrise.dasslerpro.modules

import kg.sunrise.dasslerpro.App.Companion.context
import kg.sunrise.dasslerpro.data.api.AuthorizationApi
import kg.sunrise.dasslerpro.data.api.PromotionApi
import kg.sunrise.dasslerpro.data.api.InfoApi
import org.koin.dsl.module
import retrofit2.Retrofit

private val retrofit : Retrofit = createNetworkClient(context)

private val AUTHORIZATION_API : AuthorizationApi = retrofit.create(AuthorizationApi::class.java)
private val PROMOTION_API : PromotionApi = retrofit.create(PromotionApi::class.java)
private val INFO_API: InfoApi = retrofit.create(InfoApi::class.java)

val networkModule = module {
    single { AUTHORIZATION_API }
    single { PROMOTION_API }
    single { INFO_API }
}