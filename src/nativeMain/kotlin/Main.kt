import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.curl.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.streams.*
import kotlinx.coroutines.runBlocking
import platform.posix.fopen

val client = HttpClient(Curl)

const val SDKMAN_DOWNLOAD_URL = "https://api.sdkman.io/2/broker/download"

fun main() {
//    runBlocking {
    val url = "$SDKMAN_DOWNLOAD_URL/java/11.0.11.9.1-amzn/Darwin"

    println("Downloading...")

    var i = 0
    Output(fopen("jdk", "w")!!).use {
        it.writeText("123")
    }

    runBlocking {
        val response = client.get<HttpStatement>(url) {
            onDownload { bytesSentTotal, contentLength -> print("D $bytesSentTotal / $contentLength\r") }
            onUpload { bytesSentTotal, contentLength -> print("U $bytesSentTotal / $contentLength\r") }
        }.execute()

        response.reader {
            val buf = ByteArray(4096)
            channel.readAvailable(buf)
        }
    }
}