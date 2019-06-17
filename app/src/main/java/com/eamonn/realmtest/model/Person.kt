package com.eamonn.realmtest.model

import io.realm.RealmList
import io.realm.RealmObject

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/13.
 */
open class Person(name: String?, age: Int?) : RealmObject() {
    var name: String? = name
    var age: Int? = age
    var pets: RealmList<Pet> = RealmList()

    constructor () : this(null, null) {
    }

    fun addPet(pet: Pet):Person {
        pets.add(pet)
        return this
    }

    fun removePet(pet: Pet):Person {
        pets.remove(pet)
        return this
    }

    override fun toString(): String {
        return "Person(name=$name, age=$age)"
    }


}