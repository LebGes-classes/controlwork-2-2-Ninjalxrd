package org.example;

import java.util.Calendar;

public class BroadcastsTime implements Comparable<BroadcastsTime> {
    private byte hour;
    private byte minute;
    public BroadcastsTime(byte hour, byte minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public byte hour() {
        return hour;
    }

    public byte minutes() {
        return minute;
    }
    public boolean after(BroadcastsTime t) {
        return this.compareTo(t) > 0;
    }

    public boolean before(BroadcastsTime t) {
        return this.compareTo(t) < 0;
    }

    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return this.after(t1) && this.before(t2);
    }
    public static BroadcastsTime parse(String time) {
        String[] parts = time.split(":");
        byte hour = Byte.parseByte(parts[0]);
        byte minute = Byte.parseByte(parts[1]);
        return new BroadcastsTime(hour, minute);
    }
    public BroadcastsTime addMinutes(int minutes) {
        int totalMinutes = this.hour() * 60 + this.minutes() + minutes;
        int newHours = totalMinutes / 60;
        int newMinutes = totalMinutes % 60;
        return new BroadcastsTime((byte) newHours, (byte) newMinutes);
    }
    public static BroadcastsTime now() {
        Calendar calendar = Calendar.getInstance();
        byte hour = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        byte minute = (byte) calendar.get(Calendar.MINUTE);
        return new BroadcastsTime(hour, minute);
    }
    @Override
    public int compareTo(BroadcastsTime t) {
        if (this.hour != t.hour) {
            return this.hour - t.hour;
        } else {
            return this.minute - t.minute;
        }
    }
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

}