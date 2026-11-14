package com.example.tenantmanagementapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.tenantmanagementapp.data.local.TenantDatabase
import com.example.tenantmanagementapp.data.model.Tenant
import com.example.tenantmanagementapp.data.repository.TenantRepository

class TenantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TenantRepository(TenantDatabase.getDatabase(application).tenantDao())
    val tenants: LiveData<List<Tenant>> = repository.tenants

    val tenantCountText: LiveData<String> = tenants.map { list -> "Tenants\n${list.size}" }
    val totalRentText: LiveData<String> = tenants.map { list ->
        val total = list.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
        "Total rent\nKSh %,.0f".format(total)
    }
    val allTenantsText: LiveData<String> = tenants.map { list ->
        if (list.isEmpty()) "No tenants yet. Add one from the Dashboard."
        else list.joinToString("\n\n") { it.summary() }
    }

    // 3. ACTIONS the screens can ask for
    fun addTenant(name: String, phone: String, rent: String): Tenant? {
        val cleanName = name.trim()
        val cleanPhone = phone.trim()
        val cleanRent = rent.trim()

        // Business rule: all three fields are required
        if (cleanName.isEmpty() || cleanPhone.isEmpty() || cleanRent.isEmpty()) {
            return null
        }

        return Tenant(cleanName, cleanPhone, cleanRent)
    }

    init { Log.d("TENANT_LIFECYCLE", "TenantViewModel: created") }
    override fun onCleared() { super.onCleared(); Log.d("TENANT_LIFECYCLE", "TenantViewModel: onCleared")
    }
}
