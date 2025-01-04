package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.*
import com.example.myapplication.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _pokemonList = mutableStateOf<List<Pokemon>>(emptyList())
    val pokemonList: State<List<Pokemon>> = _pokemonList

    private val _pokemonDetails = mutableStateOf<PokemonDetail?>(null)
    val pokemonDetails: State<PokemonDetail?> = _pokemonDetails

    private val _selectedMove = mutableStateOf<MoveDetail?>(null)
    val selectedMove: State<MoveDetail?> = _selectedMove

    private val _selectedAbility = mutableStateOf<AbilityDetail?>(null)
    val selectedAbility: State<AbilityDetail?> = _selectedAbility

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val response = repository.getPokemonList(151).getOrThrow()
                _pokemonList.value = response.results
            } catch (e: Exception) {
                _error.value = "Failed to load Pokemon list: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val pokemon = repository.getPokemonDetails(name).getOrThrow()
                _pokemonDetails.value = pokemon
            } catch (e: Exception) {
                _error.value = "Failed to load Pokemon details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchMoveDetails(name: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                val move = repository.getMoveDetails(name).getOrThrow()
                _selectedMove.value = move
            } catch (e: Exception) {
                _error.value = "Failed to load move details: ${e.message}"
            }
        }
    }

    fun fetchAbilityDetails(name: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                val ability = repository.getAbilityDetails(name).getOrThrow()
                _selectedAbility.value = ability
            } catch (e: Exception) {
                _error.value = "Failed to load ability details: ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
} 