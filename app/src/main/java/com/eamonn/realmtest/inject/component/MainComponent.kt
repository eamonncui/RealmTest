package com.eamonn.realmtest.inject.component

import com.eamonn.realmtest.fragment.RelationsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/17.
 */
@Singleton
@Component
interface MainComponent {
    fun inject(fragment: RelationsFragment)
}