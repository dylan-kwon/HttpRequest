package kwon.seokchan.httprequest.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kwon.seokchan.httprequest.HttpRequest
import kwon.seokchan.httprequest.HttpResponse

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "HttpConnectionActivity";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        initButtons();
    }

    private fun initButtons() {
        btGet.setOnClickListener(this);
        btPost.setOnClickListener(this);
        btPut.setOnClickListener(this);
        btDelete.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btGet -> {
                requestGet();
            }
            R.id.btPost -> {
                requestPost();
            }
            R.id.btPut -> {
                requestPut();
            }
            R.id.btDelete -> {
                requestDelete();
            }
        }
    }

    private fun requestGet() {
        val header: MutableMap<String, String> = mutableMapOf(
                "user-id" to "100",
                "user-type" to "1",
                "is-test" to "true",
                "method" to "GET"
        );
        val query: MutableMap<String, String> = mutableMapOf(
                "type" to "1",
                "page" to "3"
        );
        HttpRequest.GET("http://10.0.2.2:8000/api/kakao/channels/", header, query)
                .request(object : HttpRequest.Callback {
                    override fun onSuccess(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }

                    override fun onFail(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }
                })
    }

    private fun requestPost() {
        val header: MutableMap<String, String> = mutableMapOf(
                "user-id" to "300",
                "user-type" to "0",
                "is-test" to "false",
                "method" to "POST"
        );
        val body: MutableMap<String, String> = mutableMapOf(
                "type" to "2",
                "page" to "1"
        );
        HttpRequest.POST("http://10.0.2.2:8000/api/kakao/channels/", header, body)
                .request(object : HttpRequest.Callback {
                    override fun onSuccess(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }

                    override fun onFail(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }
                })
    }

    private fun requestPut() {
        val header: MutableMap<String, String> = mutableMapOf(
                "user-id" to "400",
                "user-type" to "10",
                "is-test" to "false",
                "method" to "PUT"
        );
        val body: MutableMap<String, String> = mutableMapOf(
                "type" to "3",
                "page" to "4"
        );
        HttpRequest.PUT("http://10.0.2.2:8000/api/kakao/channels/", header, body)
                .request(object : HttpRequest.Callback {
                    override fun onSuccess(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }

                    override fun onFail(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }
                })
    }

    private fun requestDelete() {
        val header: MutableMap<String, String> = mutableMapOf(
                "user-id" to "400",
                "user-type" to "20",
                "is-test" to "true",
                "method" to "Delete"
        );
        val body: MutableMap<String, String> = mutableMapOf(
                "type" to "5",
                "page" to "6"
        );
        HttpRequest.DELETE("http://10.0.2.2:8000/api/kakao/channels/", header, body)
                .request(object : HttpRequest.Callback {
                    override fun onSuccess(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }

                    override fun onFail(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }
                })
    }

}
