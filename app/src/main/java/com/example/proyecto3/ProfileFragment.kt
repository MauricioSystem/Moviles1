package com.example.proyecto3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class ProfileFragment : Fragment() {

    private lateinit var personViewModel: PersonViewModel
    private lateinit var textViewProfileName: TextView
    private lateinit var likedPersonsList: LinearLayout
    private lateinit var imageViewProfilePhoto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inicializar las vistas con findViewById
        textViewProfileName = view.findViewById(R.id.textViewProfileName)
        likedPersonsList = view.findViewById(R.id.likedPersonsList)
        imageViewProfilePhoto = view.findViewById(R.id.imageViewProfilePhoto)

        // Configurar el ViewModel
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        // Establecer el nombre del perfil
        textViewProfileName.text = "Mauricio Paz Cabrera 22años"

        // Configurar la foto de perfil (puedes cambiar R.drawable.profile_photo por la imagen que prefieras)
        imageViewProfilePhoto.setImageResource(R.drawable.my_photo1)

        // Observar los likes y agregar las personas a la lista
        personViewModel.likedPersons.observe(viewLifecycleOwner) { likedPersons ->
            likedPersonsList.removeAllViews() // Limpiar la lista antes de agregar
            likedPersons.forEach { person ->
                val textView = TextView(requireContext())
                textView.text = person.name
                likedPersonsList.addView(textView) // Agregar la persona a la lista
            }
        }

        return view
    }
}
