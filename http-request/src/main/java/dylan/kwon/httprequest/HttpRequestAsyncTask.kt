package dylan.kwon.httprequest

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class HttpRequestAsyncTask(
        private var mUrl: String,
        private var mMethod: String,
        private var mHttpRequestConfig: HttpRequestConfig = SimpleHttpRequestConfig()) : AsyncTask<Void, Void, HttpResponse?>() {

    public var mHeader: MutableMap<String, String> = mutableMapOf()
        set(value) {
            field.clear();
            field.putAll(value);
        }

    public var mField: MutableMap<String, String> = mutableMapOf()
        set(value) {
            field.clear();
            field.putAll(value);
        }

    private var mOnSuccess: ((response: HttpResponse) -> Unit)? = null;
    private var mOnFail: ((response: HttpResponse) -> Unit)? = null;

    companion object {
        const val TAG: String = "HttpRequestAsyncTask";
    }

    override fun doInBackground(vararg values: Void?): HttpResponse? {
        val value: Any = try {
            // QUERY
            if (this.mMethod == HttpRequest.Method.GET) {
                this.mUrl = makeQueryUrl(this.mField);
            }

            // OPEN CONNECTION
            val conn: HttpURLConnection? = openConnection(this.mUrl, this.mHeader, this.mField);
            conn?.let {

                // RESPONSE
                val httpResponse: HttpResponse = HttpResponse.create(it);
                httpResponse.print();

                return doInBackground@ httpResponse;

            } ?: let {
                return doInBackground@ null;
            }

        } catch (e1: IOException) {
            e1.printStackTrace();

        } catch (e2: IndexOutOfBoundsException) {
            e2.printStackTrace();
        }

        return doInBackground@ null;
    }

    override fun onPostExecute(response: HttpResponse?) {
        super.onPostExecute(response)
        response?.let {
            when (response.code) {
                HttpURLConnection.HTTP_OK -> {
                    this.mOnSuccess?.let {
                        it(response)
                    };
                }
                else -> {
                    this.mOnFail?.let {
                        it(response)
                    }
                }
            }

        } ?: let {
            Log.e(TAG, "Networking Error!!");
        }
        this.mOnSuccess = null;
        this.mOnFail = null;
    }

    fun makeQueryUrl(queryMap: MutableMap<String, String>): String {
        if (queryMap.isEmpty()) {
            return this.mUrl;
        }
        var query: String = "";

        for ((key, value) in queryMap) {
            query += "$key=$value&"
        }
        return "${this.mUrl}?${query.removeSuffix("&")}";
    }

    fun openConnection(url: String,
                       header: MutableMap<String, String>,
                       body: MutableMap<String, String>): HttpURLConnection? = this.openConnection(URL(url), header, body);

    fun openConnection(url: URL,
                       header: MutableMap<String, String>,
                       body: MutableMap<String, String>): HttpURLConnection? = (url.openConnection() as? HttpURLConnection)?.also {

        // OPTION
        it.requestMethod = this.mMethod;
        it.doOutput = this.mMethod != HttpRequest.Method.GET;
        it.doInput = this.mHttpRequestConfig.doInput();
        it.useCaches = this.mHttpRequestConfig.useCache();
        it.defaultUseCaches = this.mHttpRequestConfig.useCache();
        it.readTimeout = this.mHttpRequestConfig.getReadTimeoutMillis();
        it.connectTimeout = this.mHttpRequestConfig.getConnectTimeoutMillis();

        // HEADER
        val defaultHeader: MutableMap<String, String> = this.mHttpRequestConfig.getDefaultHeader();

        for ((key, value) in defaultHeader) {
            it.setRequestProperty(key, value);
        }

        for ((key, value) in header) {
            it.setRequestProperty(key, value)
        }

        // BODY
        if (this.mMethod != HttpRequest.Method.GET && body.isNotEmpty()) {

            val jsonBody: JSONObject = JSONObject(body);
            val os = it.outputStream;

            os.write(jsonBody.toString().toByteArray(this.mHttpRequestConfig.getCharset()));
            os.flush();
            os.close();
        }
    }

    @JvmOverloads
    fun request(onSuccess: ((HttpResponse) -> Unit)? = null,
                onFail: ((HttpResponse) -> Unit)? = null) {

        this.mOnSuccess = onSuccess;
        this.mOnFail = onFail;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}