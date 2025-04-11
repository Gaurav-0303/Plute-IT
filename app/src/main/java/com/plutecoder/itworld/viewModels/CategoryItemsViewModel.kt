package com.plutecoder.itworld.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.models.Uses

class CategoryItemsViewModel : ViewModel() {

    private val _categoryItems = MutableLiveData<List<CategoryItem>>()
    val categoryItems: LiveData<List<CategoryItem>> get() = _categoryItems

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("items")

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchCategoryItems(context: Context, categoryUid: String) {
        _isLoading.value = true

        // Enable local caching
        database.keepSynced(true)

        // Querying items where categoryUid matches the given categoryUid
        database.orderByChild("categoryUid").equalTo(categoryUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val categoryItemList = mutableListOf<CategoryItem>()

                        for (itemSnapshot in snapshot.children) {
                            val uid = itemSnapshot.key // item UID
                            val categoryUid = itemSnapshot.child("categoryUid").getValue(String::class.java)
                            val basicRoadmap = itemSnapshot.child("basicRoadmap").getValue(String::class.java)
                            val info = itemSnapshot.child("info").getValue(String::class.java)
                            val logo = itemSnapshot.child("logo").getValue(String::class.java)
                            val name = itemSnapshot.child("name").getValue(String::class.java)
                            val roadmaps = mutableListOf<String>()
                            val uses = mutableListOf<Uses>()

                            itemSnapshot.child("uses").children.forEach {
                                val title = it.child("title").getValue(String::class.java) ?: ""
                                val description = it.child("description").getValue(String::class.java) ?: ""
                                uses.add(Uses(title, description))
                            }

                            itemSnapshot.child("roadmaps").children.forEach {
                                val roadmapItem = it.getValue(String::class.java)
                                if (roadmapItem != null) {
                                    roadmaps.add(roadmapItem)
                                }
                            }

                            categoryItemList.add(
                                CategoryItem(
                                    uid = uid,
                                    categoryUid = categoryUid,
                                    basicRoadmap = basicRoadmap,
                                    info = info,
                                    logo = logo,
                                    name = name,
                                    roadmaps = ArrayList(roadmaps),
                                    uses = ArrayList(uses)
                                )
                            )
                        }

                        _categoryItems.postValue(categoryItemList)
                    } else {
                        Toast.makeText(context, "No items found for this category", Toast.LENGTH_SHORT).show()
                        Log.e("Firebase", "No items found for categoryUid: $categoryUid")
                    }
                    _isLoading.value = false
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Database error: ${error.message}")
                    _isLoading.value = false
                }
            })
    }
}
