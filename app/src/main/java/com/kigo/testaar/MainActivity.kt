package com.kigo.testaar

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kigo.androidsdk.config.Kigo
import com.kigo.androidsdk.data.repository.OnboardingListener
import com.kigo.androidsdk.domain.model.KigoError
import com.kigo.androidsdk.domain.model.KigoPaymentType
import com.kigo.androidsdk.domain.model.QRScanResult
import com.kigo.androidsdk.domain.model.UserKigo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userKigo: UserKigo = UserKigo.Builder()
            .setCountryCode("57")
            .setMobilePhone("31927891465")
            .setUserName("Daniel Marquez")
            .setUserEmail("user@gmail.com")
            .build()
        val init : Button = findViewById(R.id.init_sdk)
        val onboardingListener: OnboardingListener = object : OnboardingListener {


            override fun onQRScanCompleted(qrScanResult: QRScanResult) {
                Log.d("TAG", "# Ticket: " + qrScanResult.ticket.id)
                Log.d("TAG", "Ticket checkInDate: " + qrScanResult.ticket.checkInDate)
                Log.d("TAG", "Ticket checkInGateId: " + qrScanResult.ticket.checkInGateId)
                Log.d("TAG", "Parking Lot ID: " + qrScanResult.ticket.parkingLot.id)
                Log.d("TAG", "Parking Lot Name: " + qrScanResult.ticket.parkingLot.parkingLotName)

                if(qrScanResult.type == KigoPaymentType.CheckIn){
                    Log.d("TAG", "Bienvenido al Estacionamiento " + qrScanResult.ticket.parkingLot.parkingLotName)
                    Toast.makeText(this@MainActivity,"# Ticket: " + qrScanResult.ticket.id,Toast.LENGTH_LONG).show()

                }else if(qrScanResult.type == KigoPaymentType.CheckOut){
                    Log.d("TAG", "Gracias por tu Visita ")
                    Log.d("TAG", "Hora de Salida " + qrScanResult.ticket.checkOutDate)
                    Log.d("TAG", "Tiempo transcurrido dentro del ED: " + qrScanResult.ticket.totalTime)
                    Log.d("TAG", "Currency: " + qrScanResult.ticket.currency)
                    Log.d("TAG", "Monto a Cobrar: " + qrScanResult.ticket.totalAmount)
                    Toast.makeText(this@MainActivity,"# Ticket: " + qrScanResult.ticket.id,Toast.LENGTH_LONG).show()
                }

            }


            override fun onError(error: KigoError) {
                // Onboarding flow was aborted due to error
                Log.d("TAG", error.message)
                Toast.makeText(this@MainActivity,error.message,Toast.LENGTH_LONG).show()


            }

        }
        init.setOnClickListener{
            Kigo.getInstance().scanQrCode(this, "", userKigo, onboardingListener)

        }
    }
}