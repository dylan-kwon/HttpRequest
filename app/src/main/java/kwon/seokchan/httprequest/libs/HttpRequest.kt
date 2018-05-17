package kwon.seokchan.toyproject.http_connection.libs

import kwon.seokchan.httprequest.libs.HttpRequestAsyncTask
import kwon.seokchan.httprequest.libs.HttpRequestConfig
import kwon.seokchan.httprequest.libs.HttpResponse
import kwon.seokchan.httprequest.libs.SimpleHttpRequestConfig

class HttpRequest private constructor() {

    companion object {

        const val TAG: String = "HttpRequest";
        var CONFIG: HttpRequestConfig? = null;

        fun init(config: HttpRequestConfig = SimpleHttpRequestConfig()) {
            this.CONFIG = config;
        }

        fun GET(url: String,
                header: MutableMap<String, String> = mutableMapOf(),
                query: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.GET, header, query);
        }

        fun POST(url: String,
                 header: MutableMap<String, String> = mutableMapOf(),
                 field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.POST, header, field);
        }


        fun PUT(url: String,
                header: MutableMap<String, String> = mutableMapOf(),
                field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.PUT, header, field);
        }


        fun DELETE(url: String,
                   header: MutableMap<String, String> = mutableMapOf(),
                   field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.DELETE, header, field);
        }


        private fun makeAsyncTask(url: String,
                                  method: String,
                                  header: MutableMap<String, String> = mutableMapOf(),
                                  field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return HttpRequestAsyncTask(url, method).apply {
                this.mHeader = header;
                this.mField = field;
                this.mHttpRequestConfig = CONFIG;
            };
        }
    }

    object Method {
        const val GET: String = "GET";
        const val POST: String = "POST";
        const val PUT: String = "PUT";
        const val DELETE: String = "DELETE";
    }

    interface Callback {
        fun onSuccess(response: HttpResponse);

        fun onFail(response: HttpResponse);
    }

}