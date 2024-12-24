package code.with.vanilson.util;

import code.with.vanilson.util.constants.TimeZoneConstant;

import java.util.Random;

@SuppressWarnings("all")
public class TimeZoneUtil {

    // Array to hold all the time zone constants
    private static final String[] TIME_ZONES = {
            TimeZoneConstant.ZONE_LISBON,
            TimeZoneConstant.ZONE_NEW_YORK,
            TimeZoneConstant.ZONE_LONDON,
            TimeZoneConstant.ZONE_PARIS,
            TimeZoneConstant.ZONE_TOKYO,
            TimeZoneConstant.ZONE_SYDNEY,
            TimeZoneConstant.ZONE_DUBAI,
            TimeZoneConstant.ZONE_SAO_PAULO,
            TimeZoneConstant.ZONE_MOSCOW,
            TimeZoneConstant.ZONE_HONG_KONG
            // Add more zones here if needed
    };

    // Random instance to generate random indices
    private static final Random RANDOM = new Random();

    // Method to get a random time zone
    public static String getRandomTimeZone() {
        int index = RANDOM.nextInt(TIME_ZONES.length);
        return TIME_ZONES[index];
    }

    // Private constructor to prevent instantiation
    private TimeZoneUtil() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

}
