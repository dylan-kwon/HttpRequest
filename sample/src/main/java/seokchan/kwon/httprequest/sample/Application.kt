package seokchan.kwon.httprequest.sample

import android.app.Application
import seokchan.kwon.httprequest.HttpRequest

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        HttpRequest.init();
    }

}