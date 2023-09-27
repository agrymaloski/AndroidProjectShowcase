package com.example.testapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException


class MainViewModel () : ViewModel() {

    private val token = "676Rw8kE2Xfeejx2Fb0U5w==wjHGdYTjIrAjIUrk"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: MyAPIService = retrofit.create(MyAPIService::class.java)


    //mutable data stores result for rand image request
    val randImageResponse: MutableLiveData<ByteArray> = MutableLiveData()
    val calInfoResponse: MutableLiveData<List<calInfo>> = MutableLiveData()

    /*
    * This function will execute when the user inputs a request for a random image type
    */
    fun randomImageRequest(randInput : String){
        Log.d("MainViewModel", "Requesting for: $randInput")

        viewModelScope.launch {
            try {
                val response = apiService.fetchRandImage(token, "image/jpg", randInput)
                val inputStream = response.body()?.bytes()
                randImageResponse.postValue(inputStream)
                Log.d("MainViewModel", "Input stream is $inputStream")
            } catch (e: UnknownHostException) {
                Log.e("MainViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("MainViewModel", "An error occurred", e)
            }
        }
    }

    /*
   * This function will execute when the user inputs a request for a random image type
   */
    fun getCalorieInfoRequest(activity : String){
        Log.d("MainViewModel", "Requesting calorie info for: $activity")

        viewModelScope.launch {
            try {
                val response = apiService.fetchCalorieInfo(token,"application/json", activity)
                val data = response.body()
                calInfoResponse.postValue(data)
                Log.d("MainViewModel", "Resulting data is $data")
            } catch (e: UnknownHostException) {
                Log.e("MainViewModel", "Network error", e)
            } catch (e: Exception) {
                Log.e("MainViewModel", "An error occurred", e)
            }
        }
    }
}