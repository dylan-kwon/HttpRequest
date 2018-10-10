package dylan.kwon.httprequest.sample

import android.app.Application
import dylan.kwon.httprequest.HttpRequest

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        HttpRequest.init();
    }

}