package com.example.proyecto3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class PersonListFragment : Fragment() {

    private lateinit var personViewModel: PersonViewModel
    private lateinit var textViewName: TextView
    private lateinit var imageViewPhoto: ImageView
    private lateinit var btnLike: Button
    private lateinit var btnDislike: Button
    private lateinit var btnPerfil: Button
    private var currentPhotoIndex = 0 // Índice de la foto actual de la persona
    private var startX: Float = 0f // Para almacenar la coordenada X del toque inicial

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)

        // Inicializar las vistas con findViewById
        textViewName = view.findViewById(R.id.textViewName)
        imageViewPhoto = view.findViewById(R.id.imageViewPhoto)
        btnLike = view.findViewById(R.id.btnLike)
        btnDislike = view.findViewById(R.id.btnDislike)
        btnPerfil = view.findViewById(R.id.btnPerfil)

        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        // Observa los cambios en la persona actual
        personViewModel.currentPersonIndex.observe(viewLifecycleOwner) {
            val person = personViewModel.getCurrentPerson()
            textViewName.text = person.name
            currentPhotoIndex = 0 // Reinicia la foto a la primera cuando cambie de persona
            imageViewPhoto.setImageResource(person.photos[currentPhotoIndex])
        }

        // Detecta los eventos de toque para deslizar
        imageViewPhoto.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x // Captura la coordenada X del toque inicial
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val endX = event.x // Captura la coordenada X del toque final
                    if (startX < endX) {
                        // Si el movimiento es hacia la derecha
                        showPreviousPhoto()
                    } else if (startX > endX) {
                        // Si el movimiento es hacia la izquierda
                        showNextPhoto()
                    }
                    true
                }
                else -> false
            }
        }

        // Botón de like
        btnLike.setOnClickListener {
            personViewModel.likePerson()
        }

        // Botón de dislike
        btnDislike.setOnClickListener {
            personViewModel.nextPerson()
        }

        // Botón de perfil
        btnPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_personListFragment_to_profileFragment)
        }

        return view
    }

    // Mostrar la siguiente foto
    private fun showNextPhoto() {
        val person = personViewModel.getCurrentPerson()
        if (currentPhotoIndex < person.photos.size - 1) {
            currentPhotoIndex++
        } else {
            currentPhotoIndex = 0 // Si es la última foto, vuelve a la primera
        }
        imageViewPhoto.setImageResource(person.photos[currentPhotoIndex])
    }

    // Mostrar la foto anterior
    private fun showPreviousPhoto() {
        val person = personViewModel.getCurrentPerson()
        if (currentPhotoIndex > 0) {
            currentPhotoIndex--
        } else {
            currentPhotoIndex = person.photos.size - 1 // Si es la primera foto, vuelve a la última
        }
        imageViewPhoto.setImageResource(person.photos[currentPhotoIndex])
    }
}
