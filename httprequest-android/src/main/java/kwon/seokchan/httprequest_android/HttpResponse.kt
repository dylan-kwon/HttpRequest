package kwon.seokchan.httprequest_android

import android.util.Log
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.nio.charset.Charset

class HttpResponse private constructor(
        val url: String,
        val method: String,
        val code: Int,
        val message: String,
        val json: JSONObject?) {

    companion object {
        fun create(conn: HttpURLConnection): HttpResponse {
            val responseCode: Int = conn.responseCode;
            val responseMsg: String = conn.responseMessage;
            val responseJson: JSONObject?;

            responseJson = when (responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    makeResponseJson(conn);
                }
                else -> null;
            }

            return HttpResponse(
                    conn.url.path,
                    conn.requestMethod,
                    responseCode,
                    responseMsg,
                    responseJson
            );
        }

        fun makeResponseJson(conn: HttpURLConnection): JSONObject {

            val bufferSize: Int;
            val charset: Charset;

            if (HttpRequest.CONFIG == null) {
                bufferSize = 1024;
                charset = charset("UTF-8");

            } else {
                bufferSize = HttpRequest.CONFIG!!.getBufferSize();
                charset = HttpRequest.CONFIG!!.getCharset();
            }

            val `is`: InputStream = conn.inputStream;
            val byteBuffer = ByteArray(bufferSize);
            var len: Int = `is`.read(byteBuffer, 0, byteBuffer.size);

            val baos: ByteArrayOutputStream = ByteArrayOutputStream();

            while (len != -1) {
                baos.write(byteBuffer, 0, len);
                len = `is`.read(byteBuffer, 0, byteBuffer.size);
            }

            val byteData: ByteArray = baos.toByteArray();
            val responseStr: String = String(byteData, charset);

            return JSONObject(responseStr);
        }
    }

    fun print() {
        Log.d(HttpRequestAsyncTask.TAG, "requestUrl = ${this.url}")
        Log.d(HttpRequestAsyncTask.TAG, "requestMethod = ${this.method}");
        Log.d(HttpRequestAsyncTask.TAG, "responseCode = ${this.code}");
        Log.d(HttpRequestAsyncTask.TAG, "responseMsg = ${this.message}");
        Log.d(HttpRequestAsyncTask.TAG, "responseJson = ${this.json?.toString(4) ?: "null"}")
    }

}