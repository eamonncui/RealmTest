package com.eamonn.realmtest.model

import androidx.annotation.NonNull
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/14.
 */
open class Pet() : RealmObject() {

    @PrimaryKey
    @NonNull
    var id: Int? = null

    var name: String? = null
    var age: Int? = null

    constructor(id:Int? ,name: String?, age: Int?) : this() {
        this.id = id
        this.name = name
        this.age = age
    }

    override fun toString(): String {
        return "Pet(name=$name, age=$age)"
    }
}