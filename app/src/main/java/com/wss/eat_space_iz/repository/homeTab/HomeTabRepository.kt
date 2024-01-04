package com.wss.eat_space_iz.repository.homeTab


import com.wss.eat_space_iz.data.networkModels.tummoc.ShoppingResponse
import com.wss.eat_space_iz.network.ApiService
import com.wss.eat_space_iz.repository.base.ApiResult
import com.wss.eat_space_iz.repository.base.BaseRepo
import javax.inject.Inject

class HomeTabRepository
@Inject constructor(private val apiCall: ApiService): BaseRepo(){

    suspend fun tummoc(
        onError: ((ApiResult<Any>) -> Unit)?,
    ): ShoppingResponse? {
        return with(apiCall(executable = {
            apiCall.tummoc()
        })) {
            if (data == null)
                onError?.invoke(ApiResult(null, resultType, error, resCode = resCode))
            data
        }
    }



}