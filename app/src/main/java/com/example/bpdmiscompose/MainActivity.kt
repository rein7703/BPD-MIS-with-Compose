package com.example.bpdmiscompose

//import androidx.compose.foundation.layout.RowScopeInstance.align
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.ui.theme.BPDMISComposeTheme
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BPDMISComposeTheme {
                Surface {
                    BPDMISApp(viewModel)
                }
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        FirebaseDatabase.getInstance().goOffline()
    }
}



