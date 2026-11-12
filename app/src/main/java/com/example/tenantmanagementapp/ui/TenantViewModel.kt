package com.example.tenantmanagementapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tenantmanagementapp.data.local.TenantDatabase
import com.example.tenantmanagementapp.data.model.Tenant
import com.example.tenantmanagementapp.data.repository.TenantRepository

// AndroidViewModel: a ViewModel that receives the Application (safe to keep)
class TenantViewModel(application: Application) : AndroidViewModel(application) {

    // Build the data layer once: database -> DAO -> repository
    private val repository = TenantRepository(TenantDatabase.getDatabase(application).tenantDao())

    // 1. THE DATA: Room's LiveData, updated whenever the table changes
    val tenants: LiveData<List<Tenant>> = repository.tenants

    // 4. LIFECYCLE LOGS
    init {
        Log.d("TENANT_LIFECYCLE", "TenantViewModel: created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TENANT_LIFECYCLE", "TenantViewModel: onCleared")
    }
}