package com.app.ezetaptask

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.ezetaptask.databinding.ButtonLayoutBinding
import com.app.ezetaptask.databinding.DisplayTextLayoutBinding
import com.app.ezetaptask.databinding.EditTextLayoutBinding
import com.app.ezetaptask.databinding.LabelTextViewLayoutBinding
import com.app.network.networkModule.models.LoginUIDetails

class LoginScreenAdapter(
    var data: ArrayList<LoginUIDetails.UIData>,
    var listener: LoginScreenAdapter.LoginScreenInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val EDIT_VIEW_TYPE = 123;
    private val LABEL_VIEW_TYPE = 125;
    private val BUTTON_VIEW_TYPE = 122;
    private val TEXT_VIEW_TYPE = 127;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            EDIT_VIEW_TYPE -> {
                val binding: EditTextLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.edit_text_layout, parent,
                    false
                )
                return EditTypeViewHolder(binding)
            }
            LABEL_VIEW_TYPE -> {
                val binding: LabelTextViewLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.label_text_view_layout, parent,
                    false
                )
                return TextViewHolder(binding)
            }
            BUTTON_VIEW_TYPE -> {
                val binding: ButtonLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.button_layout, parent,
                    false
                )
                return ButtonViewHolder(binding)
            }
            TEXT_VIEW_TYPE -> {
                val binding: DisplayTextLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.display_text_layout, parent,
                    false
                )
                return DisplayTextViewHolder(binding)
            }
            else -> {
                return super.createViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EditTypeViewHolder -> {
                holder.bindData(position, data)
            }
            is TextViewHolder -> {
                holder.bindData(position, data)
            }
            is ButtonViewHolder -> {
                holder.bindData(position, data, listener)
            }
            is DisplayTextViewHolder -> {
                holder.bindData(position, data)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position].uiType) {
            "label" -> LABEL_VIEW_TYPE
            "edittext" -> EDIT_VIEW_TYPE
            "button" -> BUTTON_VIEW_TYPE
            "displayText" -> TEXT_VIEW_TYPE
            else -> super.getItemViewType(position)
        }
    }

    class EditTypeViewHolder(var binding: EditTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int, data: ArrayList<LoginUIDetails.UIData>) {
            binding.uiData = data[position]
            binding.position = position
            binding.editText.addTextChangedListener(object : CustomTextWatcher(position) {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    data[itemPosition].value = s.toString().trim()
                }

            })
        }

    }

    class TextViewHolder(var binding: LabelTextViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int, data: ArrayList<LoginUIDetails.UIData>) {
            binding.uiData = data[position]
        }

    }

    class ButtonViewHolder(var binding: ButtonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            position: Int,
            data: ArrayList<LoginUIDetails.UIData>,
            listener: LoginScreenInterface
        ) {
            binding.uiData = data[position]
            binding.button.setOnClickListener {
                listener.click(data)
            }
        }
    }

    class DisplayTextViewHolder(var binding: DisplayTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            position: Int,
            data: ArrayList<LoginUIDetails.UIData>
        ) {
            binding.uiData = data[position]

        }
    }


    interface LoginScreenInterface {
        fun click(data: ArrayList<LoginUIDetails.UIData>)
    }


}