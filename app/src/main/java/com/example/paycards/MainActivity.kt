package com.example.paycards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_SCAN_CARD = 1
    private val TAG = "LOG_TAG"

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
    }

    fun scanCard(v: View) {
        val intent = ScanCardIntent.Builder(this).build()
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val card: Card? = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)

                card?.let {
                    val cardData = """
                    
                    Card number: ${card.cardNumber}
                    Card holder: ${card.cardHolderName.toString()}
                    Card expiration date: ${card.expirationDate}
                    """.trimIndent()

                    textView.text = cardData
                    Log.i(TAG, "Card info: $cardData")
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "Scan canceled")
            } else {
                Log.i(TAG, "Scan failed")
            }
        }
    }

}
