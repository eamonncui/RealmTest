package com.eamonn.realmtest.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmAsyncTask

abstract class BaseRealmFragment : Fragment() {

    val transactionsList: ArrayList<RealmAsyncTask> = ArrayList()

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onStop() {
        super.onStop()
        cancelAllPendingRealmAsyncTask()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    protected fun addRealmAsyncTask(task: RealmAsyncTask) {
        transactionsList.add(task)
    }

    /**
     * The RealmAsyncTask object can cancel any pending transaction if you need to quit the Activity/Fragment before the transaction is completed.
     * Forgetting to cancel a transaction can crash the app if the callback updates the UI!
     */
    private fun cancelAllPendingRealmAsyncTask() {
        for (transaction in transactionsList) {
            if (!transaction.isCancelled) {
                transaction.cancel();
            }
        }
    }
}
