package com.app.ezetaptask.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.app.ezetaptask.*
import com.app.ezetaptask.databinding.FragmentLoginScreenBinding
import com.app.ezetaptask.genericInterfaces.GenericCallback
import com.app.ezetaptask.viewModels.LoginRepository
import com.app.ezetaptask.ui.adapters.LoginScreenAdapter
import com.app.ezetaptask.utils.LocalUtils
import com.app.network.networkModule.models.LoginUIDetails
import com.app.network.networkModule.models.NetworkResult


class LoginScreenFragment : Fragment(), LoginScreenAdapter.LoginScreenInterface {

    private lateinit var binding: FragmentLoginScreenBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_screen, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        fetchLoginScreenData()

    }

    private fun fetchLoginScreenData() {
        activity?.let { activity ->
            if (LocalUtils.isOnline(activity)) {
                showLoadingIndicator()
                val networkResult = LoginRepository.getInstance(activity).getLoginScreenDetails()
                networkResult.observe(activity, { netResult ->
                    netResult?.let { result ->
                        when (result.status) {
                            NetworkResult.Status.LOADING -> {
                                showLoadingIndicator()
                            }
                            NetworkResult.Status.SUCCESS -> {
                                hideLoadingIndicator()
                                bindData(netResult.data)
                            }
                            NetworkResult.Status.ERROR -> {
                                hideLoadingIndicator()
                                handleErrorState(result.message)
                            }
                        }
                    }
                })
            } else {
                handleErrorState(getString(R.string.internet_connection_error))
            }
        }
    }

    private fun handleErrorState(message: String?) {
        message?.let {
            LocalUtils.showAlertDialog(
                binding.progressBar.context, "Error!",
                message,
                object : GenericCallback<String> {
                    override fun callback(data: String) {
                        fetchLoginScreenData()
                    }
                })
        }

    }


    private fun bindData(data: LoginUIDetails?) {
        binding.uiDetails = data
        binding.adapter = LoginScreenAdapter(data?.uiDataList ?: ArrayList(), this)

    }

    private fun showLoadingIndicator() {
        binding.progressBar.visibility = View.VISIBLE

    }

    private fun hideLoadingIndicator() {
        binding.progressBar.visibility = View.INVISIBLE

    }

    override fun click(data: ArrayList<LoginUIDetails.UIData>) {
        if (verifyFields(data)) {
            val bundle = Bundle().apply {
                putSerializable("UI_DATA", data)
            }
            navController.navigate(R.id.action_loginScreenFragment_to_loginDetailFragment, bundle)

        } else {
            Toast.makeText(
                binding.root.context,
                getString(R.string.error_edit_fields),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun verifyFields(data: ArrayList<LoginUIDetails.UIData>): Boolean {
        for (uiData in data) {
            if (uiData.uiType.equals("edittext")) {
                if (TextUtils.isEmpty(uiData.value)) {
                    return false;
                }
            }
        }
        return true
    }
}