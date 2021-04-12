package com.dannark.turistando.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dannark.turistando.R
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

    private val _count = MutableLiveData<Int>() //readable and writable
    val count : LiveData<Int> //readable
        get() = _count

    private val _eventButtonPressed = MutableLiveData<Boolean>()
    val eventButtonPressed: LiveData<Boolean>
        get() = _eventButtonPressed


    private var lastPlace = MutableLiveData<Place?>()
    val places = placeDao.getAll()
    val posts = postDao.getAll()

    init {
        initializeLastPlace()
        //createRecommendedPlace()
        Log.i("HomeViewModel", "PlaceViewModel created!")
        _count.value = 0
    }

    private fun initializeLastPlace() {
        uiScope.launch {
            lastPlace.value = getLastPlaceFromplaceDao()
        }
    }

    private suspend fun getLastPlaceFromplaceDao(): Place? {
        return withContext(Dispatchers.IO){
            val place = placeDao.getLast()
            place
        }
    }

    fun createRecommendedPlace(){
        uiScope.launch {

            insertPlace(Place(createdBy=1, city="Bom Jardin de Minas", contry = "Minas Gerais", img = R.drawable.landscape1))
            insertPlace(Place(createdBy=1, city="Nova Iguaçu", contry = "Rio de Janeiro", img = R.drawable.landscape2))
            insertPlace(Place(createdBy=1, city="Rocinha", contry = "Rio de Janeiro", img = R.drawable.landscape3))

            lastPlace.value = getLastPlaceFromplaceDao()
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
            insertPost(Post(title = "Pipa, Rio Grande do Norte",
                description = "O litoral do Rio Grande do Norte assumiu, desde 2018, a liderança entre os posts mais acessados no Viagens Cine e continua sendo um dos melhores lugares para viajar no Brasil.", likes = 79, img = R.drawable.pipa_rio_grande_do_norte))
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
            post?.let {
                postDao.delete(it)
            }
        }
    }

    fun likePost(postId: Long){
        uiScope.launch { _likePost(postId) }
    }

    private suspend fun _likePost(postId: Long){
        withContext(Dispatchers.IO){
            val post = postDao.get(postId)
            post.likes += 1
            post.lastUpdateDate = System.currentTimeMillis()
            postDao.update(post)
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
}