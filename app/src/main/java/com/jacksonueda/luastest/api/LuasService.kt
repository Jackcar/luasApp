/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jacksonueda.luastest.api

import com.jacksonueda.luastest.model.StopInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface responsible to hold all the API endpoints
 */
interface LuasService {

    /**
     * Endpoint to get the tram forecast of a specific stop by the stop name abbreviation.
     * Use of @Query annotation in the function arguments to dynamically append
     * the stopAbv into the URL.
     */
    @GET("get.ashx?action=forecast&encrypt=false")
    fun loadForecast(@Query("stop") stopAbv: String): Single<StopInfo>

}
