package com.example.tenantmanagementapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tenantmanagementapp.databinding.FragmentDashboardBinding
import com.example.tenantmanagementapp.ui.viewmodel.TenantViewModel

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // The SHARED ViewModel
    private val viewModel: TenantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allTenantsText.observe(viewLifecycleOwner) { text ->
            // Observes tenant data changes on the dashboard
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}