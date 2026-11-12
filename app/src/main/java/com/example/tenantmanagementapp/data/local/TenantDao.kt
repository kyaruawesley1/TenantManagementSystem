package com.example.tenantmanagementapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tenantmanagementapp.data.model.Tenant

@Dao
interface TenantDao {
    // suspend: can pause while the database works, without freezing the screen
    @Insert
    suspend fun insertTenant(tenant: Tenant)

    // LiveData: Room updates it automatically whenever the table changes
    @Query("SELECT * FROM tenants ORDER BY name")
    fun getAllTenants(): LiveData<List<Tenant>>
}
