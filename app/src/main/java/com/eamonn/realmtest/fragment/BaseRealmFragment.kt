package com.eamonn.realmtest.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import io.realm.Realm

abstract class BaseRealmFragment : Fragment() {

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
