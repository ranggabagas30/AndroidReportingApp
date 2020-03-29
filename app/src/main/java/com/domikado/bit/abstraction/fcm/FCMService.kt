package com.domikado.bit.abstraction.fcm

import android.os.Bundle
import android.widget.Toast
import com.github.ajalt.timberkt.Timber.d
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        d {"==== FIREBASE MESSAGE RECEIVED ====\n"}
        // [START_EXCLUDE]
        // There are two types of messages : data messages and notification messages. ImbasPetirData messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. ImbasPetirData
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        d {"From : " + remoteMessage.from}


        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) { // data can be processed in long-running mode (using Firebase Job Dispatcher)
            // or just handle it directly which is short-running mode (under 10 secs)
            d {"remote message: $remoteMessage"}
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            val messageType = remoteMessage.messageType
            val message = remoteMessage.notification!!.body
            d {"Message type : $messageType" }
            d {"Message Notification Body : $message"}
            val bundleMessage = Bundle()
            bundleMessage.putString("messageType", messageType)
            bundleMessage.putString("message", message)
            handleNotification(bundleMessage)
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        /**
         * once get new registration token
         * 1. save to shared pref
         * 2. send token to server
         *
         */
        d {"NEW TOKEN : $s"}
        //storeRegIdInpref(s)
    }

//    private fun storeRegIdInpref(token: String) {
//        PrefUtil.putStringPref(R.string.app_fcm_reg_id, token)
//    }

    private fun handleNotification(bundleMessage: Bundle) {
        Toast.makeText(
            applicationContext,
            "message notification : " + bundleMessage.getString("message"),
            Toast.LENGTH_SHORT
        ).show()
        d {"message notification : " + bundleMessage.getString("message")}
    }
}