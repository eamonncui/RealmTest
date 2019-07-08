package com.eamonn.realmtest.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/13.
 */
open class Person() : RealmObject() {

    @PrimaryKey
    var id: String? = null

    var name: String? = null
    var age: Int? = null
    var pet: Pet? = null
    var pets: RealmList<Pet>? = null

    constructor(name: String?, age: Int?) : this() {
        this.name = name
        this.age = age
        id = hashCode().toString()
    }

    fun addPet(pet: Pet) {
        this.pet = pet
        pets?.add(pet)
    }

    fun removePet() {
        pets?.remove(pet)
        pet = null
    }

    override fun toString(): String {
        var string = "Person(id=$id, name=$name, age=$age, pet=$pet, pets=$pets)"
        for (pet1 in this.pets!!) {
            string += pet1.toString()
        }
        return string
    }
}