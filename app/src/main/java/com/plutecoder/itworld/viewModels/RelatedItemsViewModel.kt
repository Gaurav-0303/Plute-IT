package com.plutecoder.itworld.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.plutecoder.itworld.models.CategoryItem

class RelatedItemsViewModel : ViewModel() {

    private val _categoryItems = MutableLiveData<List<CategoryItem>>()
    val categoryItems: LiveData<List<CategoryItem>> get() = _categoryItems

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchRelatedItems(itemUid: String) {
        _isLoading.value = true
        val relatedItemUids = mutableSetOf<String>()

        // Step 1: Fetch related item UIDs
        database.child("items").child(itemUid).child("relatedItemsByCategory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (category in snapshot.children) {
                            for (relatedItem in category.children) {
                                relatedItem.key?.let { relatedItemUids.add(it) }
                            }
                        }
                        fetchRelatedItemDetails(relatedItemUids)
                    } else {
                        _isLoading.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _isLoading.value = false
                }
            })
    }

    // Step 2: Fetch item details efficiently using indexed queries
    private fun fetchRelatedItemDetails(relatedItemUids: Set<String>) {
        val relatedItems = mutableListOf<CategoryItem>()
        var completedRequests = 0
        val totalRequests = relatedItemUids.size

        if (totalRequests == 0) {
            _categoryItems.postValue(emptyList())
            _isLoading.value = false
            return
        }

        // Query items using Firebase indexing
        for (itemUid in relatedItemUids) {
            database.child("items").orderByChild("uid").equalTo(itemUid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnapshot in snapshot.children) {
                            Log.d("Gaurav", itemSnapshot.toString())
                            val title = itemSnapshot.child("name").getValue(String::class.java)
                            val description = itemSnapshot.child("info").getValue(String::class.java)

                            if (title != null && description != null) {
                                val categoryItem = CategoryItem(title, description)
                                relatedItems.add(categoryItem)
                            }
                        }

                        completedRequests++
                        if (completedRequests == totalRequests) {
                            _categoryItems.postValue(relatedItems)
                            _isLoading.value = false
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        completedRequests++
                        if (completedRequests == totalRequests) {
                            _categoryItems.postValue(relatedItems)
                            _isLoading.value = false
                        }
                    }
                })
        }
    }
}
