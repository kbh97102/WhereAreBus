package arakene.myapplication.feature

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class BusInfoRequestBuilder {

    private val getRouteAllHead = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"


    fun getAllRoute(routeID: String) {
        val builder = StringBuilder(getRouteAllHead).apply {
            append(
                "?" + URLEncoder.encode(
                    "ServiceKey",
                    "UTF-8"
                ) + "=PhdOlUsywigdA4q4sNpraxKbQ0HbVhjpxx5hRhd6R3Uz8bp8f7VgcxO0Hn9EP2kBsVnx2AFsvR75YNDgbY4Isg%3D%3D"
            )
            append(
                "&" + URLEncoder.encode("busRouteId", "UTF-8") + "="
                        + URLEncoder.encode(
                    routeID,
                    "UTF-8"
                )
            )
        }
        Log.e("URL", builder.toString())
        val url = URL(builder.toString())
        val connection = url.openConnection() as HttpURLConnection
        connection.apply {
            requestMethod = "GET"
            setRequestProperty("Content-type", "application/json")
        }
        Log.e("Response Code", connection.responseCode.toString())

        var rd:BufferedReader = if (connection.responseCode in 200..300) {
            BufferedReader(InputStreamReader(connection.inputStream))
        }else{
            BufferedReader(InputStreamReader(connection.errorStream))
        }
        builder.clear()

        rd.lines().forEach {
            Log.e("Datas", it)
            builder.append(it)
        }

        var line = ""
        try{
//            while (true) {
//                if(rd.readLine() != null){
//                   rd.
//                }
//                line = rd.readLine()
//                if (line == null){
//                    break
//                }
//                builder.append(line)
//            }

        }catch (e : IOException){

        }
        rd.close()
        connection.disconnect()

        Log.e("Request Body", builder.toString())
    }

}