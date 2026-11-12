# Tenant Management System
An Android app for landlords to record tenants, see total rent, and call tenants.

## Features
- Register and log in
- Dashboard with number of tenants and total rent
- Add tenants and view all tenants (saved with Room)
- Call a tenant, options menu and navigation drawer

## Architecture
ui/ Activities, Fragments and TenantViewModel (UI layer)
data/ Tenant, TenantDao, TenantDatabase, TenantRepository (data layer)
Screens -> TenantViewModel (LiveData) -> TenantRepository -> Room
