package br.com.gabrielmarcos.git_consult.services.helpers

import android.content.Context
import br.com.gabrielmarcos.git_consult.BuildConfig
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.io.File


/**
 * Created by Gabriel Marcos on 17/08/2018
 */
class RetrofitHelper {
    companion object {
        fun getRetrofit(context: Context): Retrofit {

            val httpCacheDirectory = File(context.cacheDir, "httpCache")
            val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

            val httpClient = OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor { chain ->
                        try {
                            return@addInterceptor chain.proceed(chain.request())
                        } catch (e: Exception) {
                            val offlineRequest = chain.request().newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24)
                                    .build()
                            return@addInterceptor chain.proceed(offlineRequest)
                        }
                    }
                    .build()

            return Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .client(httpClient)
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()
        }
    }
}