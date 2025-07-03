package org.utl.testapp.network.wifi

import android.content.Context
import org.utl.testapp.ui.service.PrototypeInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var retrofit: Retrofit? = null
    private var api: PrototypeInterface? = null

    fun getApi(context: Context): PrototypeInterface? {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val ip = prefs.getString("IP_SERVIDOR", null) ?: return null

        val baseUrl = "http://$ip/"

        if (retrofit == null || api == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit!!.create(PrototypeInterface::class.java)
        }
        return api
    }
}