package com.eamonn.realmtest.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.eamonn.realmtest.R
import kotlinx.android.synthetic.main.fragment_navigate.*

/**
 * A NavigateFragment for all App.
 *
 */
class NavigateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtnListener()
    }

    private fun initBtnListener() {
        navigatefrag_btn_to_init.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigateFragment_to_initFragment))
        navigatefrag_btn_to_json.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigateFragment_to_jsonFragment))
        navigatefrag_btn_to_realms.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigateFragment_to_realmsFragment))
        navigatefrag_btn_to_write.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigateFragment_to_writeFragment))
    }

}
