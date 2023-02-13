package com.example.bpdmiscompose

//import androidx.compose.foundation.layout.RowScopeInstance.align
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import com.example.bpdmiscompose.models.AuthViewModel
import com.example.bpdmiscompose.ui.theme.BPDMISComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private  val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // When running in debug mode, connect to the Firebase Emulator Suite
        // "10.0.2.2" is a special value which allows the Android emulator to
        // connect to "localhost" on the host computer. The port values are
        // defined in the firebase.json file.
        if (BuildConfig.DEBUG) {
            Firebase.database.useEmulator("10.0.2.2", 8801)
            Firebase.auth.useEmulator("10.0.2.2", 8800)
            Firebase.storage.useEmulator("10.0.2.2", 8802)
        }

        setContent {
            BPDMISComposeTheme {
                Surface {
                    BPDMISApp(viewModel)
                }
            }
        }
    }
}



