# HttpRequest (Kotlin)
[ ![Download](https://api.bintray.com/packages/seokchan-kwon/android/http-request/images/download.svg?version=1.0.0) ](https://bintray.com/seokchan-kwon/android/http-request/1.0.0/link)

JAVA에서 연동 가능.

</br>

## 1. 사용법
### build.gradle (Project)
```groovy
allprojects {
    repositories {
        jcenter()
        maven {
            url  "https://seokchan-kwon.bintray.com/android"
        }
        ...
    }
}
```

### build.gradle (Module)
```groovy
dependencies {
    ...
    implementation 'seokchan.kwon:http-request:1.0.0@aar'
}

```

### Application.kt
```kotlin
// 초기화
override fun onCreate() {
    super.onCreate()
    HttpRequest.init();
}
```

### MainActivity.kt
```kotlin
// GET API 호출 및 콜백 정의
private fun requestGet() {
        val header: MutableMap<String, String> = mutableMapOf(
                "param1" to "1",
                "param2" to "2",
                "param3" to "3"
        );
        val query: MutableMap<String, String> = mutableMapOf(
                "paramA" to "A",
                "paramB" to "B",
                "paramC" to "C"
        );
        HttpRequest.GET(API_URL, header, query)
                .request(object : HttpRequest.Callback {
                    override fun onSuccess(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }

                    override fun onFail(response: HttpResponse) {
                        Log.d(TAG, "responseCode = ${response.code}");
                    }
                });
    }
```

### AndroidManifest.xml
```xml
<!-- 퍼미션 추가 -->
<uses-permission android:name="android.permission.INTERNET" />
```

</br>

## 2. 제공 클래스

### HttpRequest.kt
```kotlin
fun init(config: HttpRequestConfig = getHttpRequestConfig());

fun getHttpRequestConfig(): HttpRequestConfig;

fun GET(url: String,
    header: MutableMap<String, String> = mutableMapOf(),
    query: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask;
    
fun POST(url: String,
    header: MutableMap<String, String> = mutableMapOf(),
    body: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask;

fun PUT(url: String,
    header: MutableMap<String, String> = mutableMapOf(),
    body: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask;

fun DELETE(url: String,
    header: MutableMap<String, String> = mutableMapOf(),
    body: MutableMap<String, String> = mutableMapOf()): HttpRequestAsyncTask;
```

### HttpRequestAsyncTask.kt
```kotlin
fun request(callback: HttpRequest.Callback? = null);
```

### HttpRequest.Callback (Interface)
```kotlin
interface Callback {
    fun onSuccess(response: HttpResponse);
    fun onFail(response: HttpResponse);
}
```

### HttpRequestConfig.kt & SimpleHttpRequestConfig.kt
`HttpRequest.init(HttpRequestConfit);`를 통해 사용자 정의 구성을 사용할 수 있으며, `SimpleHttpRequestConfig.kt`가 기본값으로 설정되어 있음.
```kotlin
interface HttpRequestConfig {
    fun doInput(): Boolean;                                 // default: true
    fun useCache(): Boolean;                                // default: true
    fun getCharset(): Charset;                              // default: UTF-8
    fun getBufferSize(): Int;                               // default: 1024
    fun getReadTimeoutMillis(): Int;                        // default: 10_000
    fun getConnectTimeoutMillis(): Int;                     // default: 10_000
    fun getDefaultHeader(): MutableMap<String, String>;     // default: empty (not null)
}
```
