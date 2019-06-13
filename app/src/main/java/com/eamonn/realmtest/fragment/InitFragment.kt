package com.eamonn.realmtest.fragment


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eamonn.realmtest.R
import com.eamonn.realmtest.model.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_init.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class InitFragment : Fragment() {

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
        initPersonBaseInfo()
        showPersonInfo(loadPersonInfo())
    }

    private fun initView() {
        btn_update_person_info.setOnClickListener {
            if (!TextUtils.isEmpty(ev_new_name.text) && !TextUtils.isEmpty(ev_new_age.text)) {
                updatePersonInfo(ev_new_name.text.toString(), (ev_new_age.text.toString().toInt()))
                Toast.makeText(this@InitFragment.context, "update success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@InitFragment.context, "some is empty", Toast.LENGTH_SHORT).show()
            }
        }

        btn_load_person_info.setOnClickListener {
            showPersonInfo(loadPersonInfo())
        }
    }

    private fun initPersonBaseInfo() {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val person = Person("Eamonn", 12)
        realm.copyToRealm(person)
        realm.commitTransaction()
        realm.close()
    }

    private fun loadPersonInfo(): Person? {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        var person = realm.where(Person::class.java).findFirst()
        person = realm.copyFromRealm(person)
        realm.commitTransaction()
        realm.close()
        return person
    }

    private fun updatePersonInfo(name: String, age: Int) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val person = realm.where(Person::class.java).findFirst()
        person?.age = age
        person?.name = name
        realm.insertOrUpdate(person)
        realm.commitTransaction()
        realm.close()
    }

    private fun showPersonInfo(person: Person?) {
        tv_info.text = person.toString()
    }
}
