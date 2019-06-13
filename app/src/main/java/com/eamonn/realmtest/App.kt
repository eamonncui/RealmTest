package com.eamonn.realmtest

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/13.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val builder = RealmConfiguration.Builder()
            .name("capp.realm")
            .deleteRealmIfMigrationNeeded()
        Realm.setDefaultConfiguration(builder.build())
    }
}