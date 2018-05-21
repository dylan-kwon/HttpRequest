package kwon.seokchan.httprequest

import android.app.Application
import kwon.seokchan.httprequest_android.HttpRequest

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        HttpRequest.init();
    }

}