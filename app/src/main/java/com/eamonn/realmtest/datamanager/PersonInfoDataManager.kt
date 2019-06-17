package com.eamonn.realmtest.datamanager

import com.eamonn.realmtest.model.Person
import com.eamonn.realmtest.model.Pet
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/14.
 */
@Singleton
class PersonInfoDataManager @Inject constructor() {

    fun initPersonBaseInfo() {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val person = Person("Eamonn", 12)
        realm.copyToRealm(person)
        realm.commitTransaction()
        realm.close()
    }

    fun loadPersonInfo(): Person? {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        var person = realm.where(Person::class.java).findFirst()
        person = realm.copyFromRealm(person)
        realm.commitTransaction()
        realm.close()
        return person
    }

    fun updatePersonInfo(name: String, age: Int) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val person = realm.where(Person::class.java).findFirst()
        person?.age = age
        person?.name = name
        realm.insertOrUpdate(person)
        realm.commitTransaction()
        realm.close()
    }

    fun updatePersonInfo(person: Person?) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val person = realm.where(Person::class.java).findFirst()
        realm.insertOrUpdate(person)
        realm.commitTransaction()
        realm.close()
    }

    fun addPet() {
        val pet = Pet("pet" + Math.random(), Math.random().toInt())
        updatePersonInfo(loadPersonInfo()?.addPet(pet))
    }

    fun removePet() {
        val pet = Pet("pet" + Math.random(), Math.random().toInt())
        updatePersonInfo(loadPersonInfo()?.removePet(pet))
    }
}