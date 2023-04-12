import android.content.Context
import com.example.splash.api.SessionManager
import okhttp3.*
import java.io.IOException

class AuthInterceptor : Interceptor {
    val mContext: Context
    constructor(context: Context) {
        mContext = context
    }
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val s = SessionManager(mContext)
        val token = s.fetchAuthToken().toString()
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        if(token.length > 1) {
            requestBuilder
                .removeHeader("Authorization")
                .addHeader(
                    "Authorization",
                    "Bearer $token"
                )
        }
        return chain.proceed(requestBuilder.build())
    }
}