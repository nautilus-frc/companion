package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.composables.indicators.InfoIndicator
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.attendance.MeetingLog
import org.nautilusapp.nautilus.attendance.UserAttendance

@Composable
fun UserAttendanceView(userAttendance: Map<String, UserAttendance>) {
    val attendanceKeys = userAttendance.keys.toList()
    var selectedAttendancePeriod by remember {
        mutableStateOf(
            attendanceKeys.lastOrNull() ?: "none"
        )
    }
    TextDropDown(label = "Time Period:", value = selectedAttendancePeriod, items = attendanceKeys) {
        selectedAttendancePeriod = it
    }
    LaunchedEffect(userAttendance) {
        selectedAttendancePeriod = attendanceKeys.lastOrNull() ?: "none"
    }
    if (userAttendance.isNotEmpty()) {
        val hoursLogged = userAttendance[selectedAttendancePeriod]?.totalHoursLogged?.toFloat()
            ?: 0f
        val progress = (hoursLogged / 36.0f).coerceAtMost(1.0f)
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "${hoursLogged.toInt()} / 36 \nhours", modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary,
                    softWrap = false,
                )
            }
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.aspectRatio(1f),
                strokeWidth = 12.dp,
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
                strokeCap = StrokeCap.Round,
            )
        }
    } else {
        Text("No attendance data found", style = MaterialTheme.typography.titleLarge)
    }


}

@Composable
fun AttendanceNfcUI(tagData: MeetingLog?, onLogAttendance: (MeetingLog) -> Unit) {
    val tagScanned = tagData?.meetingId?.isNotBlank() == true
    Button(onClick = {
        tagData?.let { onLogAttendance(it) }
    }, enabled = tagData != null) {
        Text("Log Attendance")
    }
    Spacer(modifier = Modifier.size(16.dp))
    if (tagScanned) {
        Text(
            text = "Tag scanned!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

    } else {
        InfoIndicator(text = "Scan a tag to log attendance")
    }
}

@Preview()
@Composable
fun AttendanceNfcUIPreview() {
    val space = Modifier.size(48.dp)
    var mock by remember { mutableStateOf<MeetingLog?>(null) }
    PreviewTheme(preference = ColorTheme.NAUTILUS_DARK) {
        Screen {
            UserAttendanceView(userAttendance = emptyMap())
            Spacer(space)
            AttendanceNfcUI(mock) {
                mock = if (mock == null) MeetingLog("2024spring", "unknown") else null
            }

            Spacer(modifier = Modifier.size(64.dp))

            UserAttendanceView(
                userAttendance = mapOf(
                    "2024spring" to
                            UserAttendance(10, emptyList())
                )
            )
            Spacer(space)
            AttendanceNfcUI(if (mock == null) MeetingLog("2024spring", "unknown") else null) {
                mock = if (mock != null) null else MeetingLog("2024spring", "unknown")
            }
        }
    }
}