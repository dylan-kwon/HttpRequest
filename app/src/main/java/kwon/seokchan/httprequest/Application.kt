package kwon.seokchan.httprequest

import android.app.Application
import kwon.seokchan.toyproject.http_connection.libs.HttpRequest

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        HttpRequest.init();
    }

}