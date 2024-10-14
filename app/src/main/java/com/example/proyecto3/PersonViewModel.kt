package com.example.proyecto3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonViewModel : ViewModel() {

    data class Person(val name: String, val photos: List<Int>)

    // Lista precargada de personas
    private val personList = mutableListOf(
        Person("Daira Rios 23años", listOf(R.drawable.photo1_1, R.drawable.photo1_2, R.drawable.photo1_3)),
        Person("Nicole Morales 22años", listOf(R.drawable.photo2_1, R.drawable.photo2_2, R.drawable.photo2_3)),
        Person("Camila Dabalos 19años", listOf(R.drawable.photo3_1, R.drawable.photo3_2, R.drawable.photo3_3)),
        Person("Sachi Vargas 28años", listOf(R.drawable.photo4_1, R.drawable.photo4_2, R.drawable.photo4_3)),
        Person("Nisa Ginger 23años", listOf(R.drawable.photo5_1, R.drawable.photo5_2, R.drawable.photo5_3)),
        Person("Ariane Chanebi 21años", listOf(R.drawable.photo6_1, R.drawable.photo6_2, R.drawable.photo6_3)),
        Person("Marcela Fernandes 20años", listOf(R.drawable.photo7_1, R.drawable.photo7_2, R.drawable.photo7_3)),
        Person("Yuliana Vaca 20años", listOf(R.drawable.photo8_1, R.drawable.photo8_2, R.drawable.photo8_3)),
        Person("Emi Banny 24años", listOf(R.drawable.photo9_1, R.drawable.photo9_2, R.drawable.photo9_3)),
        Person("Maria Rene 25años ", listOf(R.drawable.photo10_1, R.drawable.photo10_2, R.drawable.photo10_3)),
        Person("Andrea Zanches 21años", listOf(R.drawable.photo11_1, R.drawable.photo11_2, R.drawable.photo11_3))
    )

    private val _currentPersonIndex = MutableLiveData(0)
    val currentPersonIndex: LiveData<Int> = _currentPersonIndex

    private val _likedPersons = MutableLiveData<List<Person>>(emptyList())
    val likedPersons: LiveData<List<Person>> = _likedPersons

    // Obtener la persona actual
    fun getCurrentPerson(): Person {
        return personList[_currentPersonIndex.value ?: 0]
    }

    // Pasar a la siguiente persona
    fun nextPerson() {
        val newIndex = (_currentPersonIndex.value ?: 0) + 1
        if (newIndex < personList.size) {
            _currentPersonIndex.value = newIndex
        }
    }

    // Dar like a una persona
    fun likePerson() {
        val currentPerson = getCurrentPerson()
        _likedPersons.value = _likedPersons.value?.plus(currentPerson)
        nextPerson()
    }
}
