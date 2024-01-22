package org.team2658.emotion.android.room.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import org.team2658.emotion.android.room.daos.MeetingDao
import org.team2658.emotion.android.room.entities.MeetingEntity


@Database(
    entities = [MeetingEntity::class],
    version = 2,
)abstract class AttendanceDB: RoomDatabase() {
    abstract val meetingDao: MeetingDao
}