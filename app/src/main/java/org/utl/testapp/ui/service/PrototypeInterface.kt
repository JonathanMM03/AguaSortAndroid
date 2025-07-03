package org.utl.testapp.ui.service

import org.utl.testapp.ui.entity.SensorData
import org.utl.testapp.ui.entity.SetNivelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PrototypeInterface {

    @GET("/api/data")
    suspend fun getSensorData(): Response<SensorData>

    @POST("/api/setNivel")
    suspend fun setNivel(
        @Query("nivel") nivel: Int
    ): Response<SetNivelResponse>
}