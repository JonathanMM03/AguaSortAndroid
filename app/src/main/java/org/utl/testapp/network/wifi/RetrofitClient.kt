package org.utl.testapp.network.wifi

import android.content.Context
import retrofit2.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.utl.testapp.ui.entity.SensorData
import org.utl.testapp.ui.entity.SetNivelResponse
import org.utl.testapp.ui.service.PrototypeInterface

object RetrofitClient {

    private fun getApi(context: Context) = RetrofitInstance.getApi(context)

    suspend fun getSensorData(context: Context): Response<SensorData>? {
        return withContext(Dispatchers.IO) {
            try {
                getApi(context)?.getSensorData()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } as Response<SensorData>?
        }
    }

    suspend fun setNivel(context: Context, nivel: Int): Response<SetNivelResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                getApi(context)?.setNivel(nivel)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } as Response<SetNivelResponse>?
        }
    }
}