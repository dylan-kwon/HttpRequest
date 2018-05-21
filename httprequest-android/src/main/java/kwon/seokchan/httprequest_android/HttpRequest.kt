package kwon.seokchan.httprequest_android

class HttpRequest private constructor() {

    companion object {

        const val TAG: String = "HttpRequest";

        @JvmField
        var CONFIG: HttpRequestConfig? = null;

        @JvmStatic
        fun init(config: HttpRequestConfig = getHttpRequestConfig()) {
            CONFIG = config;
        }

        @JvmStatic
        fun getHttpRequestConfig(): HttpRequestConfig = CONFIG ?: synchronized(this) {
            CONFIG ?: SimpleHttpRequestConfig().also {
                CONFIG = it;
            }
        }

        @JvmStatic
        @JvmOverloads
        fun GET(url: String,
                header: MutableMap<String, String> = mutableMapOf(),
                query: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.GET, header, query);
        }

        @JvmStatic
        @JvmOverloads
        fun POST(url: String,
                 header: MutableMap<String, String> = mutableMapOf(),
                 field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.POST, header, field);
        }

        @JvmStatic
        @JvmOverloads
        fun PUT(url: String,
                header: MutableMap<String, String> = mutableMapOf(),
                field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.PUT, header, field);
        }

        @JvmStatic
        @JvmOverloads
        fun DELETE(url: String,
                   header: MutableMap<String, String> = mutableMapOf(),
                   field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return makeAsyncTask(url, Method.DELETE, header, field);
        }


        @JvmStatic
        private fun makeAsyncTask(url: String,
                                  method: String,
                                  header: MutableMap<String, String> = mutableMapOf(),
                                  field: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask {

            return HttpRequestAsyncTask(url, method, getHttpRequestConfig()).apply {
                this.mHeader = header;
                this.mField = field;
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