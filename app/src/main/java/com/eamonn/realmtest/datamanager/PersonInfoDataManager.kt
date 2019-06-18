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
        if (loadPersonInfo() == null) {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val person = Person("Eamonn", 12)
            person.addPet(Pet(0, "Eamonn" + "Pet", 12))
            realm.copyToRealm(person)
            realm.commitTransaction()
            realm.close()
        }
    }

    fun loadPersonInfo(): Person? {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        var person = realm.where(Person::class.java).findFirst()
        if (person != null) {
            person = realm.copyFromRealm(person)
        }
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

    private fun updatePersonInfo(person: Person?) {
        person.let {
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.insertOrUpdate(person)
            realm.commitTransaction()
            realm.close()
        }
    }

    fun addPet() {
        var index = 0
        val person = loadPersonInfo()
        if (person?.pets != null && !person.pets!!.isEmpty()) {
            index = person.pets!!.first()?.id!! + 1
        }
        val pet = Pet(index, "pet" + index++, 10 + index)
        person?.addPet(pet)
        updatePersonInfo(person)
    }

    fun removePet() {
//        val person = loadPersonInfo()
//        person?.pets?.first()?.let { person.removePet(it) }
//        updatePersonInfo(person)
    }
}