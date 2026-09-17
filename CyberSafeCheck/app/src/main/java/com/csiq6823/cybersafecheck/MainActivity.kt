package com.csiq6823.cybersafecheck

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.commit
import com.csiq6823.cybersafecheck.databinding.ActivityMainBinding
import com.csiq6823.cybersafecheck.ui.theme.CyberSafeCheckTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyberSafeCheckTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Hosts the XML FrameLayout (activity_main.xml) inside Compose.
                    AndroidViewBinding(
                        factory = ActivityMainBinding::inflate,
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    ) {
                        if (supportFragmentManager.findFragmentById(root.id) == null) {
                            supportFragmentManager.commit {
                                add(root.id, ChecklistFragment())
                            }
                        }
                    }
                }
            }
        }
    }
}
