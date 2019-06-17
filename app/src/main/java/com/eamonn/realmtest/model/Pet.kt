package com.eamonn.realmtest.model

import io.realm.RealmObject

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/14.
 */
open class Pet : RealmObject {

    var name: String? = null
    var age: Int? = null

    constructor()

    constructor(name: String?, age: Int?) {
        this.name = name
        this.age = age
    }

}