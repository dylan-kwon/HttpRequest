package dylan.kwon.httprequest

import java.nio.charset.Charset

interface HttpRequestConfig {

    fun doInput(): Boolean;

    fun useCache(): Boolean;

    fun getCharset(): Charset;

    fun getBufferSize(): Int;

    fun getReadTimeoutMillis(): Int;

    fun getConnectTimeoutMillis(): Int;

    fun getDefaultHeader(): MutableMap<String, String>;

}