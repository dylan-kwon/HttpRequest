package kwon.seokchan.httprequest.sample

import android.app.Application
import kwon.seokchan.httprequest.HttpRequest

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        HttpRequest.init();
    }

}