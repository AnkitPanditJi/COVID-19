package com.blavasgps.covidcheckup.commons.status;

/**
 * Created By Ravi Kant on 12/12/2019
 */
public class VehicleInfo {

    public static class VehicleType
    {
        public static String BIKE = "118";
        public static String TRACTOR = "146";
        public static String CAR = "7";
        public static String MAN = "106";
        public static String MINI_TRUCK = "125";
        public static String MINI_BUS = "117";
        public static String CONTAINER_TRUCK = "122";
    }

    public static class VehicleStatus
    {
        public static int IDLE_PARKED = 1;
        public static int RUNNING = 2;
        public static int OUT_OF_NETWORK = 3;
        public static int DEVICE_FAULT = 4;
        public static int STOP = 5;
        public static int ALL = 6;
    }

}
