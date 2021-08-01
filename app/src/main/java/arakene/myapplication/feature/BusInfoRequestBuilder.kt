package arakene.myapplication.feature

import android.util.Log
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.xml.parsers.DocumentBuilderFactory

class BusInfoRequestBuilder {

    companion object{
        const val STATION = "busStation"
        const val FIRSTBUS = "first"
        const val FIRSTTIME = "firstTime"
        const val SECONDBUS = "second"
        const val SECONDTIME = "secondTime"
    }

    private val getRouteAllHead = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"


    fun getAllBusStopInfo(routeID: String):HashMap<String, HashMap<String, String>> {
        val busInfo = HashMap<String, HashMap<String, String>>()
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

        var rd: BufferedReader = if (connection.responseCode in 200..300) {
            BufferedReader(InputStreamReader(connection.inputStream))
        } else {
            BufferedReader(InputStreamReader(connection.errorStream))
        }
        builder.clear()

        val factory = DocumentBuilderFactory.newInstance()
        val documentBuilder = factory.newDocumentBuilder()
        val doc = documentBuilder.parse(InputSource(url.openStream()))
        doc.documentElement.normalize()

        val nodeList = doc.getElementsByTagName("itemList")

        Log.e("Node list size", nodeList.length.toString())


        /**
         * TODO 버스 id , 도착시간이 0이면 예정이 없는거임 예외처리 할 것
         * TODO ord도 알아 낼 수 있음
         */
        for (i in 0 until nodeList.length-1){
            val node = nodeList.item(i)
            val element = node as Element

            val currentStationName = getInfo(element, "stNm")
            val firstBusID = getInfo(element, "vehId1")
            val firstBusTime = getInfo(element, "exps1")
            val secondBusID = getInfo(element, "vehId2")
            val secondBusTime = getInfo(element, "exps2")

            val info = HashMap<String, String>().apply {
                put(STATION, currentStationName)
                put(FIRSTBUS, firstBusID)
                put(FIRSTTIME, firstBusTime)
                put(SECONDBUS, secondBusID)
                put(SECONDTIME, secondBusTime)
            }

            busInfo.put(currentStationName, info)

            Log.e("Station Info", "Station Name $currentStationName, first Bus ID $firstBusID, first bus arrive time $firstBusTime" +
                    ", second bus ID $secondBusID, second bus arrive time $secondBusTime")
        }



        rd.close()
        connection.disconnect()

        Log.e("Request Body", builder.toString())
        return busInfo
    }

    private fun getInfo(element: Element, name:String):String{
        val list = element.getElementsByTagName(name)
        return list.item(0).childNodes.item(0).nodeValue
    }

}