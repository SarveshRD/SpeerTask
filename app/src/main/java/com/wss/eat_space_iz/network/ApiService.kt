package com.wss.eat_space_iz.network


import com.wss.eat_space_iz.data.networkModels.cart.CartResponse
import com.wss.eat_space_iz.data.networkModels.favitems.FavItemsResponse
import com.wss.eat_space_iz.data.networkModels.searchUser.SearchUserResponse
import com.wss.eat_space_iz.data.networkModels.speer.FollowersResponce
import com.wss.eat_space_iz.data.networkModels.speer.FollowersResponceItem
import com.wss.eat_space_iz.data.networkModels.speer.FollowingResponse
import com.wss.eat_space_iz.data.networkModels.tummoc.ShoppingResponse
import com.wss.eat_space_iz.utils.constants.AppConstants
import retrofit2.http.*


interface ApiService {


//Testing purpose
 /*   @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
    @GET(AppConstants.Api.UserEndUrl.LISTED)
    suspend fun topLink(): ListedResponse*/

    @GET(AppConstants.Api.UserEndUrl.FOLLOWERS)
    suspend fun callFollowers(): FollowersResponce

    @GET(AppConstants.Api.UserEndUrl.FOLLOWING)
    suspend fun callFollowing(): FollowingResponse

    @GET(AppConstants.Api.UserEndUrl.TUMMOC)
    suspend fun tummoc(): ShoppingResponse

    @GET(AppConstants.Api.UserEndUrl.FAV_ITEM)
    suspend fun favitem(): FavItemsResponse

    @GET(AppConstants.Api.UserEndUrl.CART)
    suspend fun cart(): CartResponse

    @GET(AppConstants.Api.UserEndUrl.SEARCH_USER)
    suspend fun searchUser(
        @Query("q") userName: String
    ): SearchUserResponse


}