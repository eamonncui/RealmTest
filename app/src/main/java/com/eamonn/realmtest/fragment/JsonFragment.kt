package com.eamonn.realmtest.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eamonn.realmtest.R
import io.realm.Realm
import com.eamonn.realmtest.model.Pet
import io.realm.RealmChangeListener
import kotlinx.android.synthetic.main.fragment_json.*
import org.json.JSONObject

/**
 * A Fragment for Json to RealmObject.
 *
 */
class JsonFragment : Fragment() {

    private lateinit var realm: Realm
    private lateinit var realmChangeListener: RealmChangeListener<Realm>
    private lateinit var pet: Pet

    private val petId = 100
    private val petName = "zhupi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_json, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPetWithNormalWay()
        initPetInfo()
        initBtnListener()
    }

    private fun initBtnListener() {
        jsonfrag_btn_jsonString_to_pet.setOnClickListener { initPetWithJsonString() }
        jsonfrag_btn_jsonObject_to_pet.setOnClickListener { initPetWithJsonObject() }
    }

    private fun initPetInfo() {
        realm.executeTransaction { pet = realm.where(Pet::class.java).equalTo("id", petId).findFirst()!! }
        realmChangeListener = RealmChangeListener { updatePetInfo() }
        realm.addChangeListener(realmChangeListener)
        updatePetInfo()
    }

    private fun initPetWithNormalWay() {
        realm.executeTransaction { realm ->
            realm.insertOrUpdate(Pet(petId, petName, 1))
        }
    }

    private fun initPetWithJsonString() {
        realm.executeTransaction { realm ->
            realm.createOrUpdateObjectFromJson(
                Pet::class.java,
                "{ id: 100, name: \"$petName\", age: 2 }"
            )
        }
    }

    private fun initPetWithJsonObject() {
        val jsonObject = JSONObject()
        jsonObject.put("id", petId)
        jsonObject.put("name", petName)
        jsonObject.put("age", 3)
        realm.executeTransaction { realm ->
            realm.createOrUpdateObjectFromJson(
                Pet::class.java,
                jsonObject
            )
        }
    }

    private fun updatePetInfo() {
        jsonfrag_tv_petinfo.text = pet.toString()
    }
}
