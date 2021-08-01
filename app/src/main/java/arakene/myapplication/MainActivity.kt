package arakene.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import arakene.myapplication.feature.BusInfoRequestBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val busInfo = BusInfoRequestBuilder().getAllBusStopInfo("100100352")
        }



    }}