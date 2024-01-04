package com.wss.eat_space_iz.repository.blogsTab

import com.wss.eat_space_iz.data.networkModels.searchUser.SearchUserResponse
import com.wss.eat_space_iz.data.networkModels.speer.FollowersResponce
import com.wss.eat_space_iz.data.networkModels.speer.FollowersResponceItem
import com.wss.eat_space_iz.data.networkModels.speer.FollowingResponse
import com.wss.eat_space_iz.network.ApiService
import com.wss.eat_space_iz.repository.base.ApiResult
import com.wss.eat_space_iz.repository.base.BaseRepo
import javax.inject.Inject

class BlogTabRepository
@Inject constructor(private val apiCall: ApiService):BaseRepo(){

    suspend fun callFollowers(
        onError: ((ApiResult<Any>) -> Unit)?,
    ): FollowersResponce? {
        return with(apiCall(executable = {
            apiCall.callFollowers()
        })) {
            if (data == null)
                onError?.invoke(ApiResult(null, resultType, error, resCode = resCode))
            data
        }
    }
    suspend fun searchUser(
        userName : String,
        onError: ((ApiResult<Any>) -> Unit)?,
    ): SearchUserResponse? {
        return with(apiCall(executable = {
            apiCall.searchUser(userName =userName)
        })) {
            if (data == null)
                onError?.invoke(ApiResult(null, resultType, error, resCode = resCode))
            data
        }
    }
    suspend fun callFollowing(
        onError: ((ApiResult<Any>) -> Unit)?,
    ): FollowingResponse? {
        return with(apiCall(executable = {
            apiCall.callFollowing()
        })) {
            if (data == null)
                onError?.invoke(ApiResult(null, resultType, error, resCode = resCode))
            data
        }
    }
}