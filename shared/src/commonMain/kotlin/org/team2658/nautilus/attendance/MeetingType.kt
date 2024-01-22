package org.team2658.nautilus.attendance

enum class MeetingType(val value: String) {
    MEETING("general"),
    LEADS("leads"),
    KICKOFF("kickoff"),
    WORKSHOP("workshop"),
    COMPETITION("competition"),
    OUTREACH("outreach"),
    MENTOR("mentor"),
    OTHER("other");
}