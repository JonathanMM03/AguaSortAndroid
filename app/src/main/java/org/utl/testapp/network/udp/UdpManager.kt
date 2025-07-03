package org.utl.testapp.network.udp

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

object UdpClient {
    private const val UDP_PORT = 81
    private const val TIMEOUT_MS = 2000
    private const val BROADCAST_IP = "255.255.255.255"
    private const val TAG = "UdpClient"

    suspend fun discoverEsp32(context: Context): String? = withContext(Dispatchers.IO) {
        try {
            val socket = DatagramSocket()
            socket.broadcast = true
            socket.soTimeout = TIMEOUT_MS

            val message = "GET_ESP32_IP"
            val buffer = message.toByteArray()
            val address = InetAddress.getByName(BROADCAST_IP)
            val packet = DatagramPacket(buffer, buffer.size, address, UDP_PORT)
            socket.send(packet)
            Log.d(TAG, "📤 UDP enviado: $message")

            val receiveBuffer = ByteArray(1024)
            val responsePacket = DatagramPacket(receiveBuffer, receiveBuffer.size)
            socket.receive(responsePacket)

            val response = String(responsePacket.data, 0, responsePacket.length).trim()
            Log.d(TAG, "📥 UDP recibido: $response")

            if (response.startsWith("ESP32_IP=")) {
                val ip = response.removePrefix("ESP32_IP=")
                guardarIP(context, ip)
                socket.close()

                // Mostrar Toast en hilo principal
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "IP ESP32: $ip", Toast.LENGTH_LONG).show()
                }

                return@withContext ip
            }

            socket.close()
            null
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error al descubrir ESP32", e)
            null
        }
    }

    private fun guardarIP(context: Context, ip: String) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("IP_SERVIDOR", ip).apply()
    }

    fun obtenerIP(context: Context): String? {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getString("IP_SERVIDOR", null)
    }

    // NUEVA función para llamar desde el hilo principal y hacer todo
    fun discoverEsp32AndShowToast(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            discoverEsp32(context)
        }
    }
}