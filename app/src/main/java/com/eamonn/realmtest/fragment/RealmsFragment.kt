package com.eamonn.realmtest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eamonn.realmtest.R
import io.realm.Realm
import io.realm.RealmConfiguration
import android.os.Looper
import com.eamonn.realmtest.model.Pet

/**
 * Realms
 *
 */
class RealmsFragment : Fragment() {

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = configureRealm()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_realms, container, false)
    }

    private fun configureRealm(): Realm {

        // The RealmConfiguration is created using the builder pattern.
        // The Realm file will be located in Context.getFilesDir() with name "myrealm.realm"
        val config = RealmConfiguration.Builder()
            .name("realmstest.realm")
            .encryptionKey(getKey())
            .deleteRealmIfMigrationNeeded()
            //  .schemaVersion(42)
            //  .modules(MySchemaModule())
            //  .migration(MyMigration())
            .build()

        //   Use the config
        realm = Realm.getInstance(config)

        return realm
    }

    private fun getKey(): ByteArray = "lsfajlkdfjlajflajjafsljflasjflaskjflsajflksadflsjflajklfjflksdaf".toByteArray()


    // readOnly is only enforced in the current process!!!
    // It is still possible for other processes or devices to write to readOnly Realms.
    // Also, any write transaction against a read-only Realm will throw an IllegalStateException.
    // This includes trying to write the schema, so that must be provided initially by some other source.
    private fun readOnlyRealm() {
        //It’s sometimes useful to ship a prepared Realm file with your app—you may want to bundle some shared data with your application.
        val config = RealmConfiguration.Builder()
            .assetFile("my.realm")
            .readOnly()
            // It is optional, but recommended to create a module that describes the classes
            // found in your bundled file. Otherwise if your app contains other classes
            // than those found in the file, it will crash when opening the Realm as the
            // schema cannot be updated in read-only mode.
            //.modules(BundledRealmModule())
            .build()
    }

    // With an inMemory configuration, you can create a Realm that runs entirely in memory without being persisted to disk.
    private fun InMemoryRealm() {
        val myConfig = RealmConfiguration.Builder()
            .name("myrealm.realm")
            .inMemory()
            .build()
    }

    //Realm instances are reference counted—if you call getInstance twice in a thread, you need to call close twice as well.
    // This allows you to implement Runnable classes without having to worry about which thread will execute them:
    // simply start it with getInstance and end it with close.
    private fun closeRealms() {
        //For the UI thread, the easiest way is to execute realm.close in the owning component’s onDestroy method.
        // If you need to create a Looper thread other than UI, you can use this pattern:
        class MyThread : Thread() {

            private var realm: Realm? = null

            override fun run() {
                Looper.prepare()
                realm = Realm.getDefaultInstance()
                try {
                    //... Setup the handlers using the Realm instance ...
                    Looper.loop()
                } finally {
                    realm!!.close()
                }
            }
        }

        // Run a non-Looper thread with a Realm instance.
        val thread = Thread(Runnable {
            Realm.getDefaultInstance().use { realm ->
                // No need to close the Realm instance manually
            }
        })

        thread.start()
    }
}
