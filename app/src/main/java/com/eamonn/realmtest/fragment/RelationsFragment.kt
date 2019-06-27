package com.eamonn.realmtest.fragment


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eamonn.realmtest.R
import com.eamonn.realmtest.datamanager.PersonInfoDataManager
import com.eamonn.realmtest.inject.component.DaggerMainComponent
import com.eamonn.realmtest.model.Person
import kotlinx.android.synthetic.main.fragment_init.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class RelationsFragment : Fragment() {

    @Inject
    lateinit var dataManager: PersonInfoDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerMainComponent.builder().build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_init, container, false)
    }

    override fun onResume() {
        super.onResume()
        initView()
        dataManager.initPersonBaseInfo()
        showPersonInfo(dataManager.loadPersonInfo())
    }

    private fun initView() {
        btn_update_person_info.setOnClickListener {
            if (!TextUtils.isEmpty(ev_new_name.text) && !TextUtils.isEmpty(ev_new_age.text)) {
                try {
                    dataManager.updatePersonInfo(ev_new_name.text.toString(), (ev_new_age.text.toString().toInt()))
                    Toast.makeText(this@RelationsFragment.context, "update success", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@RelationsFragment.context, "update fail," + e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@RelationsFragment.context, "some is empty", Toast.LENGTH_SHORT).show()
            }
        }

        btn_load_person_info.setOnClickListener {
            showPersonInfo(dataManager.loadPersonInfo())
        }

        btn_add_pet.setOnClickListener { dataManager.addPet() }

        btn_remove_pet.setOnClickListener { dataManager.removePet() }
    }


    private fun showPersonInfo(person: Person?) {
        tv_info.text = person.toString()
    }
}
