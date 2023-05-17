package com.example.gettoken

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gettoken.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var flag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SetListener()
    }

    fun SetListener() {
        binding.btnSend.setOnClickListener()
        {
            SendEmail()
        }
        binding.btnSendCode.setOnClickListener()
        {
            SendCode()
        }
    }

    fun SendEmail() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://medic.madskill.ru")
            .client(httpClient)
            .build()
        val requestApi = retrofit.create(ApiRequest::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    requestApi.postEmail(binding.txtEmail.text.toString()).awaitResponse()
                Log.d("Response", "success")
                flag = true
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString())
            }
        }
    }

    fun SendCode() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://medic.madskill.ru")
            .client(httpClient)
            .build()
        val requestApi = retrofit.create(ApiRequest::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var code = binding.txtCode.text.toString()
                var email = binding.txtEmail.text.toString()
                val response = requestApi.postCode(
                    code,
                    email
                ).awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Log.d(TAG, data.toString())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }

    }
}