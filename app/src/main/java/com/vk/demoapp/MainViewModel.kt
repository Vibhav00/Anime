package com.vk.demoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.demoapp.data.Animes
import com.vk.demoapp.data.DescAnime
import com.vk.demoapp.repository.ParentRepository
import kotlinx.coroutines.launch

class MainViewModel(val parentRepository: ParentRepository) : ViewModel() {
    private var _allAnime = MutableLiveData<Resource<Animes>>()
    val allAnime: LiveData<Resource<Animes>> = _allAnime

    private var _perticularAnime = MutableLiveData<Resource<DescAnime>>()
    val perticularAnime: LiveData<Resource<DescAnime>> = _perticularAnime
    init {
          getAllAnimes()
    }

    fun getAllAnimes(){
        viewModelScope.launch {
            try{
              _allAnime.value = Resource.Loading()
                val k = parentRepository.getAllAnime()
                if(k.isSuccessful){
                    if(k.body()!=null){
                        _allAnime.value =   Resource.Success(k.body()!!)
                    }else{
                        _allAnime.value = Resource.Error(k.errorBody().toString());
                    }
                }
            }catch (e:Exception){
                _allAnime.value = Resource.Error(e.message.toString());
            }
        }
    }


    fun getSpecificAnime(id:Int){
        viewModelScope.launch {
            try{
                _perticularAnime.value = Resource.Loading()
                val k = parentRepository.getSA(id)
                if(k.isSuccessful){
                    if(k.body()!=null){
                        _perticularAnime.value =   Resource.Success(k.body()!!)
                    }else{
                        _perticularAnime.value = Resource.Error(k.errorBody().toString());
                    }
                }
            }catch (e:Exception){
                _perticularAnime.value = Resource.Error(e.message.toString());
            }
        }
    }
}


