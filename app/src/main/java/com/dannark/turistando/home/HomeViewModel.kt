package com.dannark.turistando.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.database.Place
import com.dannark.turistando.database.PlaceDao
import com.dannark.turistando.database.Post
import com.dannark.turistando.database.PostDao
import com.dannark.turistando.network.PostProperty
import com.dannark.turistando.network.TuristandoApi
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(val placeDao: PlaceDao, val postDao: PostDao, application: Application)
    : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _count = MutableLiveData<Int>() //readable and writable
    val count : LiveData<Int> //readable
        get() = _count

    private val _eventButtonPressed = MutableLiveData<Boolean>()
    val eventButtonPressed: LiveData<Boolean>
        get() = _eventButtonPressed


    val places = placeDao.getAll()
    val posts = postDao.getAll()

    init {
//        createRecommendedPlace()
//        createPost()
//        testRetrofit()
        _count.value = 0
    }

    private suspend fun getLastPlaceFromPlaceDao(): Place? {
        return withContext(Dispatchers.IO){
            val place = placeDao.getLast()
            place
        }
    }

    fun createRecommendedPlace(){
        uiScope.launch {

            insertPlace(Place(createdBy=1, city="Bom Jardin de Minas", contry = "Minas Gerais", img = "https://i.ibb.co/D1k39QM/landscape1.png"))
            insertPlace(Place(createdBy=1, city="Nova Iguaçu", contry = "Rio de Janeiro", img = "https://i.ibb.co/7y4mW2n/landscape2.png"))
            insertPlace(Place(createdBy=1, city="Rocinha", contry = "Rio de Janeiro", img = "https://i.ibb.co/DKxmV2K/landscape3.png"))

        }
    }

    private suspend fun insertPlace(place: Place) {
        withContext(Dispatchers.IO){
            placeDao.insert(place)
        }
    }

    fun clearAllPlaces(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        return withContext(Dispatchers.IO){
            placeDao.clear()
        }
    }

    fun createPost(){
        uiScope.launch {
            insertPost(Post(title = "Titulo da postagem",
                    description = "Foto tirada no sul de minas, pela manhã o sol estava bem forte, porém ao fim da tarde, pudemos ver esse lindo por do sol, enquanto preparava minha camera para fotografa-lo, algo inesperado aconteceu...",
                    likes = 1, img = "https://i.ibb.co/7y4mW2n/landscape2.png"))

            insertPost(Post(title = "Rota das Emoções e Lençóis Maranhenses, Maranhão",
                    description = "Rota das Emoções e Lençóis Maranhenses, Maranhão. Também conhecido como um dos três dos melhores lugares para viajar no Brasil num mesmo roteiro: Lençóis Maranhenses, Delta do Parnaíba e Jericoacoara.",
                    likes = 1, img = "https://i.ibb.co/NV3VM6q/lencois-maranhences.png"))

            insertPost(Post(title = "Pipa, Rio Grande do Norte",
                    description = "O litoral do Rio Grande do Norte assumiu, desde 2018, a liderança entre os posts mais acessados no Viagens Cine e continua sendo um dos melhores lugares para viajar no Brasil.",
                    likes = 79, img = "https://i.ibb.co/RYnY9Rx/pipa-rio-grande-do-norte.png"))
        }
    }
    
    private suspend fun insertPost(post: Post){
        withContext(Dispatchers.IO){
            postDao.insert(post)
        }
    }

    fun deletePost(postId: Long){
        uiScope.launch { _deletePost(postId) }
    }

    private suspend fun _deletePost(postId: Long){
        withContext(Dispatchers.IO){
            val post = postDao.get(postId)
            post.let {
                postDao.delete(it)
            }
        }
    }

    fun likePost(postId: Long){
        uiScope.launch { _likePost(postId) }
    }

    private suspend fun _likePost(postId: Long){
        withContext(Dispatchers.IO){
            postDao.likePost(postId, System.currentTimeMillis())
        }
    }

    fun add() {
        _count.value = _count.value?.plus(1)
    }

    fun nextValue() {
        _count.value = (_count.value?:0)+1

        if(count.value!! > 5){
            _eventButtonPressed.value = true
        }
    }

    fun onButtonPressed(){
        _eventButtonPressed.value = false
    }

    private val _navigateToPlaceDetails = MutableLiveData<Long>()
    val navigateToPlaceDetails
        get() = _navigateToPlaceDetails

    fun onRecommendedPlaceClicked(id: Long){
        _navigateToPlaceDetails.value = id
    }

    fun onPlaceDetailsNavigated(){
        _navigateToPlaceDetails.value = null
    }

    private fun testRetrofit(){
        TuristandoApi.retrofitServices.getPropreties().enqueue(object : Callback<List<PostProperty>>{
            override fun onResponse(call: Call<List<PostProperty>>, response: Response<List<PostProperty>>) {
                Log.e("HomeView","retrofit ${response.body()}")
            }

            override fun onFailure(call: Call<List<PostProperty>>, t: Throwable) {
                Log.e("HomeView","Faliure: ${t.message}")
            }
        })
    }
}