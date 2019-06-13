package com.eamonn.realmtest.model

import io.realm.RealmObject

/**
 * RealmTest
 *
 * @author Eamonn.Cui
 * Created by Eamonn.Cui on 2019/06/13.
 */
open class Person(name:String?, age:Int?) : RealmObject() {
     var name: String? = name
     var age: Int? = age

     constructor () : this(null, null) {
     }

     override fun toString(): String {
          return "Person(name=$name, age=$age)"
     }


}