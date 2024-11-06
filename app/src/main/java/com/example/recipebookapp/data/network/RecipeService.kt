package com.example.recipebookapp.data.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AssetCallAdapterFactory(private val context: Context) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // Solo manejar tipos que sean Call
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // Obtener el tipo genérico del parámetro (el tipo de respuesta que se espera)
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)

        // Aquí pasamos el responseType y el context al constructor del AssetCallAdapter
        return AssetCallAdapter<Any>(context, responseType)
    }
}

class AssetCallAdapter<T>(private val context: Context, private val responseType: Type) : CallAdapter<T, Call<T>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<T> {
        return object : Call<T> {

            override fun enqueue(callback: Callback<T>) {
                // Cargar el archivo JSON desde los assets y parsearlo
                val jsonString = getJsonDataFromAsset("data.json") // Reemplaza con tu nombre de archivo JSON
                val gson = Gson()

                // Usamos TypeToken para manejar el tipo genérico
                val typeToken = TypeToken.get(responseType)
                val data = gson.fromJson<T>(jsonString, typeToken.type)

                // Pasar los datos obtenidos al callback, simulando una respuesta exitosa
                callback.onResponse(this, Response.success(data))
            }

            override fun isExecuted(): Boolean = false

            override fun cancel() {
                // Implementar cancelación si es necesario
            }

            override fun isCanceled(): Boolean = false

            override fun clone(): Call<T> = this

            override fun execute(): Response<T> {
                // Este ejemplo solo usa `enqueue`, pero si es necesario, implementa una ejecución síncrona
                throw UnsupportedOperationException("Not supported in this case.")
            }

            override fun request() = call.request() // Opcional si necesitas manejar la solicitud HTTP

            override fun timeout(): Timeout {
                // Implementar timeout si es necesario
                return Timeout.NONE
            }
        }
    }

    // Función para obtener el JSON desde los assets de la aplicación
    private fun getJsonDataFromAsset(fileName: String): String {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)  // Abrir el archivo en los assets
        val reader = BufferedReader(InputStreamReader(inputStream))  // Leer el archivo
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)  // Agregar cada línea al StringBuilder
        }
        reader.close()  // Cerrar el lector
        return stringBuilder.toString()  // Devolver el JSON como un string
    }
}
