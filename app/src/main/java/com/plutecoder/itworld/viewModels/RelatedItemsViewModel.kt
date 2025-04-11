package com.plutecoder.itworld.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.models.Uses

class RelatedItemsViewModel : ViewModel() {

    private val _categoryItems = MutableLiveData<List<CategoryItem>>()
    val categoryItems: LiveData<List<CategoryItem>> get() = _categoryItems

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchRelatedItems(itemUid: String) {
        _isLoading.value = true
        val relatedItemUids = mutableSetOf<String>()

        // Enable local caching
        database.keepSynced(true)

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

                            relatedItems.add(
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
