package com.plutecoder.itworld.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.plutecoder.itworld.models.Category

class CategoryViewModel : ViewModel() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")

    // MutableLiveData to hold categories
    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>> get() = _categoriesList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        _isLoading.value = true

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = ArrayList<Category>()

                for (categorySnapshot in snapshot.children) {
                    val uid = categorySnapshot.key
                    val uiType = categorySnapshot.child("Ui_type").getValue(String::class.java)
                    val image = categorySnapshot.child("image").getValue(String::class.java)
                    val title = categorySnapshot.child("title").getValue(String::class.java)
                    val subtitle = categorySnapshot.child("subtitle").getValue(String::class.java)
                    tempList.add(Category(uid, uiType, image, title, subtitle))
                }

                _categoriesList.value = tempList
                _isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _isLoading.value = false
            }
        })
    }
}
