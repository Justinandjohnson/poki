package com.example.myapplication.ui.screens.detail

import com.example.myapplication.ui.base.BaseViewModel
import com.example.myapplication.domain.model.MoveDomain
import com.example.myapplication.domain.model.PokemonDetailDomain

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getMoveDetailUseCase: GetMoveDetailUseCase
) : BaseViewModel() {
    private val _pokemon = MutableStateFlow<PokemonDetailDomain?>(null)
    val pokemon: StateFlow<PokemonDetailDomain?> = _pokemon.asStateFlow()

    private val _selectedMove = MutableStateFlow<MoveDomain?>(null)
    val selectedMove: StateFlow<MoveDomain?> = _selectedMove.asStateFlow()

    private val _selectedAbility = MutableStateFlow<AbilityDomain?>(null)
    val selectedAbility: StateFlow<AbilityDomain?> = _selectedAbility.asStateFlow()

    fun loadPokemonDetail(name: String) {
        launchWithLoadingState {
            getPokemonDetailUseCase(name)
                .onSuccess { pokemon ->
                    _pokemon.value = pokemon
                }
                .onFailure { throw it }
        }
    }

    fun loadMoveDetail(name: String) {
        viewModelScope.launch {
            getMoveDetailUseCase(name)
                .onSuccess { move ->
                    _selectedMove.value = move
                }
                .onFailure { /* Handle error */ }
        }
    }

    fun loadAbilityDetail(name: String) {
        viewModelScope.launch {
            getAbilityDetailUseCase(name)
                .onSuccess { ability ->
                    _selectedAbility.value = ability
                }
                .onFailure { /* Handle error */ }
        }
    }
} 