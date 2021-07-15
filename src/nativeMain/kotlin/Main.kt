import io.ktor.client.*
import io.ktor.client.engine.curl.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking

val client = HttpClient(Curl)

fun main() {
    println("Downloading...")
    runBlocking {
        val response =
            client.get<HttpStatement>("https://api.sdkman.io/2/broker/download/java/11.0.11.9.1-amzn/Darwin") {
                onDownload { bytesSentTotal, contentLength -> print("D $bytesSentTotal / $contentLength\r") }
            }.execute()

        response.reader {
            val buf = ByteArray(4096)
            channel.readAvailable(buf)
        }
    }
}