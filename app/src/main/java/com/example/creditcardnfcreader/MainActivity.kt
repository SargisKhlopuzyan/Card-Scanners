package com.example.creditcardnfcreader

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pro100svitlo.creditCardNfcReader.CardNfcAsyncTask
import com.pro100svitlo.creditCardNfcReader.utils.CardNfcUtils

class MainActivity : AppCompatActivity(), CardNfcAsyncTask.CardNfcInterface {

    private var mNfcAdapter: NfcAdapter? = null
    private lateinit var mCardNfcUtils: CardNfcUtils
    private lateinit var mCardNfcAsyncTask: CardNfcAsyncTask
    private var mIntentFromCreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter == null) {
            Log.e("LOG_TAG", "*** 1")
            //do something if there are no nfc module on device
        } else {

            Log.e("LOG_TAG", "*** 2")

            //do something if there are nfc module on device

            mCardNfcUtils = CardNfcUtils(this)
            //next few lines here needed in case you will scan credit card when app is closed
            mIntentFromCreate = true
            onNewIntent(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        mIntentFromCreate = false
        if (mNfcAdapter != null && !mNfcAdapter!!.isEnabled) {
            Log.e("LOG_TAG", "*** 3")
            //show some turn on nfc dialog here. take a look in the samle ;-)
        } else if (mNfcAdapter != null) {
            Log.e("LOG_TAG", "*** 4")
            mCardNfcUtils.enableDispatch()
        }
    }


    override fun onPause() {
        super.onPause()

        if (mNfcAdapter != null) {
            Log.e("LOG_TAG", "*** 5")
            mCardNfcUtils.disableDispatch()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (mNfcAdapter != null && mNfcAdapter!!.isEnabled) {
            Log.e("LOG_TAG", "*** 6")
            //this - interface for callbacks
            //intent = intent :)
            //mIntentFromCreate - boolean flag, for understanding if onNewIntent() was called from onCreate or not
            mCardNfcAsyncTask = CardNfcAsyncTask.Builder (this, intent, mIntentFromCreate)
            .build()
        }
    }

    override fun startNfcReadCard() {
        Log.e("LOG_TAG", "startNfcReadCard")
    }

    override fun cardIsReadyToRead() {
        Log.e("LOG_TAG", "cardIsReadyToRead")
    }

    override fun finishNfcReadCard() {
        Log.e("LOG_TAG", "finishNfcReadCard")
    }

    override fun cardWithLockedNfc() {
        Log.e("LOG_TAG", "cardWithLockedNfc")
    }

    override fun doNotMoveCardSoFast() {
        Log.e("LOG_TAG", "doNotMoveCardSoFast")
    }

    override fun unknownEmvCard() {
        Log.e("LOG_TAG", "unknownEmvCard")
    }
}
