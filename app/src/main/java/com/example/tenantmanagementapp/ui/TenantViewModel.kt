package com.example.tenantmanagementapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tenantmanagementapp.data.local.TenantDatabase
import com.example.tenantmanagementapp.data.model.Tenant
import com.example.tenantmanagementapp.data.repository.TenantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TenantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TenantRepository

    init {
        val tenantDao = TenantDatabase.getDatabase(application).tenantDao()
        repository = TenantRepository(tenantDao)
        Log.d("TENANT_LIFECYCLE", "TenantViewModel: created")
    }

    val tenants: LiveData<List<Tenant>> = repository.tenants

    val tenantCountText: LiveData<String> = tenants.map { list ->
        "Tenants\n${list.size}"
    }

    val totalRentText: LiveData<String> = tenants.map { list ->
        val total = list.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
        "Total rent\nKSh %,.0f".format(total)
    }

    val allTenantsText: LiveData<String> = tenants.map { list ->
        if (list.isEmpty()) {
            "No tenants yet. Add one from the Dashboard."
        } else {
            list.joinToString("\n\n") { it.summary() }
        }
    }

    fun addTenant(name: String, phone: String, rent: String): Tenant? {
        if (name.isBlank() || phone.isBlank() || rent.isBlank()) {
            return null
        }
        val tenant = Tenant(name = name, phone = phone, rent = rent)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTenant(tenant)
        }
        return tenant
    }

    fun addTenant(tenant: Tenant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTenant(tenant)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TENANT_LIFECYCLE", "TenantViewModel: onCleared")
    }
}
