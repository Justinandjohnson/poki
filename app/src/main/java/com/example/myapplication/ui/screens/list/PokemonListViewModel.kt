package com.example.myapplication.ui.screens.list

import com.example.myapplication.domain.model.PokemonDomain
import com.example.myapplication.ui.base.BaseViewModel

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : BaseViewModel() {
    private val _pokemonList = MutableStateFlow<List<PokemonDomain>>(emptyList())
    val pokemonList: StateFlow<List<PokemonDomain>> = _pokemonList.asStateFlow()

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        launchWithLoadingState {
            getPokemonListUseCase()
                .onSuccess { pokemon ->
                    _pokemonList.value = pokemon
                }
                .onFailure { throw it }
        }
    }
} 