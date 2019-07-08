package com.eamonn.realmtest.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/21.
 */
open class Contact(name: String?) : RealmObject() {

    @PrimaryKey
    var name: String? = name
    var tels: RealmList<String> = RealmList()

    constructor() : this(null) {}

    fun addTels(tel: String) {
        tels.add(tel)
    }
}