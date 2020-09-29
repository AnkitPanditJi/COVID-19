package com.blavasgps.covidcheckup.commons.server;

/**
 * Created By Ravi Kant on 26/11/2019
 */
public class ServerConfig {

    public static final boolean IS_STAGING = false;

    public static final String BASE_URL_STAGING = "http://173.249.16.126/vts/mo_test_old/mobapi/covidcheckup/";
    public static final String BASE_URL_PRODUCTION = "http://173.249.16.126/vts/mo_test_old/mobapi/covidcheckup/";

    public static final String BASE_URL = IS_STAGING ? BASE_URL_STAGING : BASE_URL_PRODUCTION;

    public static final String Corona_Test_url = BASE_URL + "user_corona_test_api.php";
    public static final String Login_url = BASE_URL + "user_register_otp.php";
    public static final String Login_Otp_Verify_url = BASE_URL + "confirm_user_otp.php";
    public static final String Register = BASE_URL + "user_detail_register_api.php";
    public static final String INDIA_CENTER_URL = BASE_URL + "crona_virus_india_center.html";
    public static final String WORLD_WIDE_URL = BASE_URL + "crona_virus_world_details.html";



}
