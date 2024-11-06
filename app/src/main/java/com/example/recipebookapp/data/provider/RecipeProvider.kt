package com.example.recipebookapp.data.provider

import android.content.Context
import androidx.room.Room
import com.example.recipebookapp.data.database.RecipeDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeProvider {
    private val RECIPE_DATABASE_NAME = "recipe-db"
    private val ENDPOINT = "https://curso-android-56-1.vercel.app/"

    fun providerRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun providerRoom(context: Context): RecipeDatabase {
        return Room.databaseBuilder(context, RecipeDatabase::class.java, RECIPE_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}