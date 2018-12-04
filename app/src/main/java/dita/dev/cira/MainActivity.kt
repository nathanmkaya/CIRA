package dita.dev.cira

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.microsoft.graph.authentication.IAuthenticationAdapter
import com.microsoft.graph.authentication.MSAAuthAndroidAdapter
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.core.ClientException
import com.microsoft.graph.core.DefaultClientConfig
import com.microsoft.graph.core.IClientConfig
import com.microsoft.graph.extensions.GraphServiceClient
import com.microsoft.graph.extensions.IGraphServiceClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var authenticationAdapter: IAuthenticationAdapter
    val CLIENT_ID = "27fce8be-91fa-46ee-81f5-7c28b16da04a"
    private lateinit var clientConfig: IClientConfig
    private lateinit var client: IGraphServiceClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_btn.setOnClickListener {
            login()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        authenticationAdapter = object : MSAAuthAndroidAdapter(application) {
            override fun getClientId(): String {
                return CLIENT_ID
            }

            override fun getScopes(): Array<String> {
                return arrayOf(
                        // An example set of scopes your application could use
                        "https://graph.microsoft.com/Calendars.ReadWrite",
                        "https://graph.microsoft.com/Contacts.ReadWrite",
                        "https://graph.microsoft.com/Files.ReadWrite",
                        "https://graph.microsoft.com/Mail.ReadWrite",
                        "https://graph.microsoft.com/Mail.Send",
                        "https://graph.microsoft.com/User.ReadBasic.All",
                        "https://graph.microsoft.com/User.ReadWrite",
                        "offline_access",
                        "openid")
            }
        }

        clientConfig  = DefaultClientConfig.createWithAuthenticationProvider(authenticationAdapter)
        client  = GraphServiceClient.Builder().fromConfig(clientConfig).buildClient()
    }

    fun login() {
        authenticationAdapter.login(this, object:ICallback<Void> {
            override fun success(result: Void?) {
                Log.i("Login", "Success")
                Thread(Runnable {
                    val organization = client.organization.buildRequest().get()
                    organization.rawObject["value"].asJsonArray.forEach {
                        println(it.toString())
                    }
                    println()
//                    Log.i("Client", )
                }).start()
            }

            override fun failure(ex: ClientException?) {
                Log.i("Login", "Fail", ex)
            }

        })
    }


}
