package com.example.cardscan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.getbouncer.cardscan.CreditCard
import com.getbouncer.cardscan.ScanActivity

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ScanActivity.apiKey = "0200JbVX9_aQtGi_z7Zw-fU8wYa9DX32"

        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        button.setOnClickListener {
            launchCardScan()
        }
    }

    private fun launchCardScan() {
        ScanActivity.start(this, false, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (ScanActivity.isScanResult(requestCode)) {
            if (resultCode == ScanActivity.RESULT_OK && data != null) {

                val scanResult: CreditCard? = ScanActivity.creditCardFromResult(data)

                println("last4: ${scanResult?.last4()}")
                println("numberForDisplay: ${scanResult?.numberForDisplay()}")
                println("expiryForDisplay: ${scanResult?.expiryForDisplay()}")
                println("scanResult: $scanResult")
                println("number: ${scanResult?.number}")
                println("expiryMonth: ${scanResult?.expiryMonth}")
                println("expiryYear: ${scanResult?.expiryYear}")

                textView.text = "Card Information:\n"
                scanResult?.let {
                    textView.append("Card Number: ${it.number}")

                    it.expiryMonth?.let { expiryMonth ->
                        textView.append("Expiry Date: ${it.expiryMonth}")
                        textView.append(expiryMonth)
                    }

                    it.expiryYear?.let { expiryYear ->
                        textView.append(expiryYear)
                    }

                }

            } else if (resultCode == ScanActivity.RESULT_CANCELED) {

                println("RESULT_CANCELED")

                if (data != null && data.getBooleanExtra(ScanActivity.RESULT_FATAL_ERROR, false)) {
                    // TODO: handle a fatal error with cardscan
                } else {
                    // TODO: the user pressed the back button
                }
            }
        }
    }
}
