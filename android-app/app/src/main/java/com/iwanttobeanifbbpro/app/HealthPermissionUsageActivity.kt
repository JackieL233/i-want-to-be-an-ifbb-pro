package com.iwanttobeanifbbpro.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class HealthPermissionUsageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Health data usage",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "I Want to be an IFBB PRO reads Health Connect records only after you grant permission. The app uses body weight, body fat, lean mass, steps, sleep, resting heart rate, and total calories burned to improve training, nutrition, recovery, and AI check-in recommendations."
                        )
                        Text(
                            text = "Imported health data stays on this device unless you choose to send a daily review to your configured AI API provider. You can revoke Health Connect permissions in Android settings at any time."
                        )
                    }
                }
            }
        }
    }
}
