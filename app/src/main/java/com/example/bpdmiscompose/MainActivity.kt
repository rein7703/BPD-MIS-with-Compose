package com.example.bpdmiscompose

//import androidx.compose.foundation.layout.RowScopeInstance.align
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.bpdmiscompose.ui.theme.BPDMISComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BPDMISComposeTheme {
                    Surface {
                        BPDMISApp()
                    }
                }
            }
        }
    }




