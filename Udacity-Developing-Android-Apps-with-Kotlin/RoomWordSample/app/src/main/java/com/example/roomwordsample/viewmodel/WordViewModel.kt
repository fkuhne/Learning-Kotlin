package com.example.roomwordsample.viewmodel

import androidx.lifecycle.*
import com.example.roomwordsample.repository.WordRepository
import com.example.roomwordsample.repository.model.Word
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    /* Using LiveData and caching what allWords returns has several benefits:
     *   - we can put an observer on the data (instead of pulling for changes)
     *     and only update the UI when the data actually changes;
     *   - repository is completely separated from the UI through the ViewModel. */
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    // Launching a coroutine to insert the data in a non-blocking way
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
