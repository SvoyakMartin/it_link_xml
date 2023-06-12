package com.example.it_link_xml.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.it_link_xml.ERROR_TAG
import com.example.it_link_xml.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val _state = MutableStateFlow(arrayOf<String>())
    val state: StateFlow<Array<String>>
        get() = _state
    private val repository = Repository()
    var orientation = 0

    init {
        fetchImageUrlList()
    }

    private fun fetchImageUrlList() {
        viewModelScope.launch {
            repository.getImageList().conflate().collect { data ->
                when (data) {
                    is List<*> -> {
                        val stringArray = Array(data.size) { "" }

                        data.forEachIndexed { index, str ->
                            if (str is String) {
                                stringArray[index] = str
                            }
                        }
                        _state.value = stringArray
                    }

                    else -> Log.e(ERROR_TAG, "fetchImageUrlList: $data")
                }
            }
        }
    }

}