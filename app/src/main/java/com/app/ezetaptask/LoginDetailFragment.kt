package com.app.ezetaptask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.ezetaptask.databinding.FragmentDetailScreenBinding
import com.app.network.networkModule.models.LoginUIDetails

class LoginDetailFragment : Fragment(), LoginScreenAdapter.LoginScreenInterface {

    private lateinit var binding: FragmentDetailScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    private fun bindData() {
        arguments?.let { arguments ->
            if (arguments.containsKey("UI_DATA")) {
                var dataList: ArrayList<LoginUIDetails.UIData> =
                    arguments["UI_DATA"] as ArrayList<LoginUIDetails.UIData>
                manipulateData(dataList)
                binding.adapter = LoginScreenAdapter(dataList, this)

            }
        }

    }

    private fun manipulateData(dataList: ArrayList<LoginUIDetails.UIData>) {
        for (uiData in dataList) {
            if (uiData.uiType.equals("edittext"))
                uiData.uiType = "displayText"
            if (uiData.uiType.equals("button")) {
                uiData.value = "Looks good!"
            }
        }
    }

    override fun click(data: ArrayList<LoginUIDetails.UIData>) {
        sequenceOf(
            Toast.makeText(
                binding.root.context,
                "Signed up successfully",
                Toast.LENGTH_SHORT
            ).show()
        )
    }

}