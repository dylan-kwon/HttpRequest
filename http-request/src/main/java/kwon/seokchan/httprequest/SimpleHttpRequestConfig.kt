package kwon.seokchan.httprequest

import java.nio.charset.Charset

class SimpleHttpRequestConfig : HttpRequestConfig {

    override fun doInput(): Boolean = true;

    override fun useCache(): Boolean = true;

    override fun getCharset(): Charset = Charsets.UTF_8;

    override fun getBufferSize(): Int = 1024;

    override fun getReadTimeoutMillis(): Int = 10_000;

    override fun getConnectTimeoutMillis(): Int = 10_000;

    override fun getDefaultHeader(): MutableMap<String, String> = mutableMapOf(
            "accept" to "application/json",
            "content-type" to "application/json"
    );

}