package com.eamonn.realmtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eamonn.realmtest.R
import com.eamonn.realmtest.model.Person
import com.eamonn.realmtest.model.Pet
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.fragment_write.*

/**
 * A WriteFragment
 *
 * Write transactions block each other.
 * This can cause ANR errors if you create write transactions on both the UI and background threads at the same time.
 * To avoid this, use async transactions when creating write transactions on the UI thread.
 */
class WriteFragment : BaseRealmFragment() {

    private var mainThreadId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainThreadId = Thread.currentThread().id
        return inflater.inflate(R.layout.fragment_write, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtnListener()
    }

    private fun initBtnListener() {
        writefrag_btn_executeTransactionAsync.setOnClickListener { asynchronousTransactions() }
        writefrag_btn_addpets.setOnClickListener { addPets() }
        writefrag_btn_batchupdates.setOnClickListener { batchUpdate() }
        writefrag_btn_showpersoninfo.setOnClickListener { showPersonInfo(realm) }
    }

    fun createObject() {
        realm.beginTransaction()
        val person = realm.createObject(Person::class.java)
        person.name = "Eamonn"
        person.age = 22
        person.id = "10010"
        realm.commitTransaction()
    }

    /**
     * Remember, Realm only manages the returned object , not the object originally copied .
     * To make changes to the object in the database, make changes to the returned copy, not the original.
     * person = realm.copyToRealmOrUpdate(person)
     */
    fun updateObject() {
        realm.beginTransaction()
        val person = Person("Eamonn", 22)
        realm.copyToRealmOrUpdate(person)
        realm.commitTransaction()
    }

    /**
     * If you are only interestered in inserting the object and not using the managed copy right away, it is possible to use insert instead.
     * This works in a similar way to copyToRealm but is much faster as not returning the object makes it possible to optimize it more.
     *
     * If you are inserting many objects, the recommend approach is to use insert or insertOrUpdate.
     */
    fun insertObject() {
        val users = listOf(Person("John", 33), Person("Mia", 44))
        realm.beginTransaction()
        realm.insert(users)
        realm.commitTransaction()
    }

    /**
     * use the executeTransaction method, which will automatically handle begin/commit, and cancel if an error happens.
     */
    fun transactionBlocks() {

    }

    /**
     * CallBack it is in MainThread, you can update UI
     */
    private fun asynchronousTransactions() {
        addRealmAsyncTask(
            realm.executeTransactionAsync(
                {
                    val person = Person("Eamonn", 99)
                    it.copyToRealmOrUpdate(person)
                },
                {
                    tv_info.text = "OnSuccess, ${isMainThread()}"
                },
                {
                    tv_info.text = "OnError, ${isMainThread()}"
                })
        )
    }

    private fun addPets() {
        realm.executeTransaction {
            val person = findPersonInfo(it)
            val petList: RealmList<Pet> = RealmList()
            petList.add(Pet(1, "1", 100))
            petList.add(Pet(2, "2", 100))
            petList.add(Pet(3, "3", 100))
            person?.pets?.clear()
            person?.pets?.addAll(petList)
        }
    }

    private fun batchUpdate() {
        realm.executeTransaction { realm ->
            val persons = realm.where(Pet::class.java).equalTo("age", 100L).findAll()
            persons.setValue("age", 128L)
        }
    }

    private fun findPersonInfo(realm: Realm): Person? {
        return realm.where(Person::class.java).equalTo("age", 99L).findFirst()
    }

    private fun showPersonInfo(realm: Realm) {
        tv_info.text = findPersonInfo(realm)?.toString()
    }

    private fun isMainThread(): Boolean {
        return Thread.currentThread().id == mainThreadId
    }
}
