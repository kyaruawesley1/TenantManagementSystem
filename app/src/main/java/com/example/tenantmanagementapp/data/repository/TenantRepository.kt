package com.example.tenantmanagementapp.data.repository

import androidx.lifecycle.LiveData
import com.example.tenantmanagementapp.data.local.TenantDao
import com.example.tenantmanagementapp.data.model.Tenant

class TenantRepository(private val tenantDao: TenantDao) {
    val tenants: LiveData<List<Tenant>> = tenantDao.getAllTenants()

    suspend fun addTenant(tenant: Tenant) {
        tenantDao.insertTenant(tenant)
    }
}