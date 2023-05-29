package com.example.oliymahadkurslar.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oliymahadkurslar.db.SavedRep

class SavedVmf(private val rep: SavedRep) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedVm(rep = rep) as T
    }
}