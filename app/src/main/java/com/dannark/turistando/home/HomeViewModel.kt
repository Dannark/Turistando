package com.dannark.turistando.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dannark.turistando.database.Place
import com.dannark.turistando.database.PlaceDao
import com.dannark.turistando.database.Post
import com.dannark.turistando.database.PostDao
import kotlinx.coroutines.*


class HomeViewModel(val placeDao: PlaceDao, val postDao: PostDao, application: Application)
    : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val places = placeDao.getAll()
    val posts = postDao.getAll()

    init {
//        createRecommendedPlace()
//        createPost()
    }

    private suspend fun getLastPlaceFromPlaceDao(): Place? {
        return withContext(Dispatchers.IO){
            val place = placeDao.getLast()
            place
        }
    }

    fun createRecommendedPlace(){
        val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
        uiScope.launch {
            insertPlace(Place(createdBy=1, placeName = "Bom Jardin de Minas", city="Bom Jardin de Minas", state = "Minas Gerais", img = "https://i.ibb.co/D1k39QM/landscape1.png", description = lorem))
            insertPlace(Place(createdBy=1, placeName="Nova Iguaçu", city="Nova Iguaçu", state = "Rio de Janeiro", img = "https://i.ibb.co/7y4mW2n/landscape2.png", description = lorem))
            insertPlace(Place(createdBy=1, placeName="Rocinha", city="Rocinha", state = "Rio de Janeiro", img = "https://i.ibb.co/DKxmV2K/landscape3.png", description = lorem))

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

    private val _navigateToPlaceDetails = MutableLiveData<Place>()
    val navigateToPlaceDetails: LiveData<Place>
        get() = _navigateToPlaceDetails

    fun displayPlaceDetails(place: Place){
        _navigateToPlaceDetails.value = place
    }

    fun onPlaceDetailsCompleted(){
        _navigateToPlaceDetails.value = null
    }

}