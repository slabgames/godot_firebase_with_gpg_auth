package com.boxgramer.boxfirebaselib

import android.app.Activity
import android.util.ArraySet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot
import java.util.Date


class BoxFirebase(godot: Godot?) : GodotPlugin(godot) {
//
    private var dbFirestore : FirebaseFirestore ? = null
    private var auth : FirebaseAuth ? = null

    private var currentUser : FirebaseUser?  = null
    private var TAG : String = "godot"

    override fun getPluginName(): String {
        return "BoxFirebase"
    }
//
    override fun onMainCreate(activity: Activity?): View? {
        return super.onMainCreate(activity)
    }


    override fun getPluginSignals(): MutableSet<SignalInfo> {
        val signals= mutableSetOf<SignalInfo>()
        signals.add(SignalInfo("onTestEmitSingal", String::class.java))
        signals.add(SignalInfo("onSuccessCheckAuth", String::class.java))
        signals.add(SignalInfo("onErrorCheckAuth", String::class.java))
        signals.add(SignalInfo("onStartSaveData"))
        signals.add(SignalInfo("onSuccessSaveData"))
        signals.add(SignalInfo("onErrorSaveData", String::class.java))
        signals.add(SignalInfo("onStartGetData"))
        signals.add(SignalInfo("onSuccessGetData", String::class.java))
        signals.add(SignalInfo("onErrorGetData", String::class.java))

        return signals

    }

    @UsedByGodot
    fun initialize()  {
        Log.d("godot", "last initialize boxfirebase")

        dbFirestore = Firebase.firestore
        auth = Firebase.auth
        Log.d("godot", "init auth: ${auth.toString()}, firestore : ${dbFirestore.toString()} ")
    }

    @UsedByGodot
    fun checkAuth() {
        Log.d("godot", "Start check Auth ")
        currentUser = auth?.currentUser
        Log.d("godot", currentUser.toString())
        if (currentUser == null) {
            auth?.signInAnonymously()
                ?.addOnCompleteListener {
                   Log.d("godot", "Sign-in anonymously: success")
                    currentUser = auth?.currentUser
                    emitSignal("onSuccessCheckAuth", currentUser?.uid.toString())
                }?.addOnFailureListener { e ->
                    Log.d("godot", "sing-in anonymously: failed ", e)
                    emitSignal("onErrorCheckAuth", e.message)
                }
        }

    }


    @UsedByGodot
    fun saveDataByUser(nameColletion:String, jsonSTR : String ) {
        Log.d(TAG, "start save data ${jsonSTR}")
        emitSignal("onStartSaveData")
        if (currentUser == null )  {
            Log.d(TAG , "User Not Found")
            emitSignal("onErrorSaveData", "User Not Found")
            return
        }
        val parser :Parser = Parser.default()
        val stringJson: StringBuilder =  StringBuilder(jsonSTR);
        val json= parser.parse(stringJson) as JsonObject
        val user = json.toMap()
        dbFirestore?.collection(nameColletion)
            ?.document(currentUser?.uid.toString())
            ?.set(user, SetOptions.merge())
            ?.addOnSuccessListener {
                Log.d(TAG, "Save Data success")
                emitSignal("onSuccessSaveData")

            }
            ?.addOnFailureListener { e ->
                Log.d(TAG, "Save data failed", e)
                emitSignal("onErrorSaveData", e.message)
            }

    }

    @UsedByGodot
    fun getDataByUser(nameColletion: String) {
        Log.d(TAG , "start get data")

        emitSignal("onStartGetData")
        if (currentUser == null )  {
            Log.d(TAG , "User Not Found")
//            emitSignal("onErrorGetData", "User Not Found")
            return
        }
        dbFirestore?.collection(nameColletion)
            ?.document(currentUser?.uid.toString())
            ?.get()
            ?.addOnSuccessListener { doc->
               if(doc != null) {
                   Log.d(TAG, "Data was Founded")
                   Log.d(TAG, doc.toString())
                    emitSignal("onSuccessGetData", doc.toString()  )
               }else {
                   val message = "Data Not found"
                   Log.d(TAG , "Data not found")
                   emitSignal("onErrorGetData", message)
               }
            }
            ?.addOnFailureListener { e ->
                Log.d(TAG, "failed retrive data ", e)
//                emitSignal("onErrorGetData", e.message)
            }

    }


    @UsedByGodot
    fun testLog()  {
        Log.d("godot","Test Log From $pluginName")
    }

    @UsedByGodot
    fun testLoadData() {
//        var db = Firebase.firestore
        Log.d("godot", "Test Load Data ")
        godot.runOnUiThread( Runnable {

            var db = Firebase.firestore
            Log.d("godot", "test firebase run thread, ${db.toString()} " )
            db.collection("data")
            .get()
            .addOnSuccessListener{
                result->
                for (document in result) {
                    Log.d("godot", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener{
                    exception ->
                    Log.w("godot" , "Error getting documents . ", exception)
            }
        })
    }
    @UsedByGodot
    fun testEmitSignal()  {
        Log.d(TAG , "Start test Emit Signal")

        emitSignal("onTestEmitSingal" , "hello from plugin android ")
    }


}