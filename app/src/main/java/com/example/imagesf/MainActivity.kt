package com.example.imagesf
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.imagesf.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnUpload.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type= "image/*"
            imageLauncher.launch(intent)
        }
    }
    val imageLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
            if(it.data!=null){
                val reference = Firebase.storage.reference.child("photo")
                reference.putFile(it.data!!.data!!).addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        binding.ivAvatar.setImageURI(it)
                        Picasso.get().load(it.toString()).into(binding.ivAvatar)
                    }
                }
            }
        }
    }
}