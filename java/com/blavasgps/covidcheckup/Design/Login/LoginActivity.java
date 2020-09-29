package com.blavasgps.covidcheckup.Design.Login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blavasgps.covidcheckup.Design.MainActivity;
import com.blavasgps.covidcheckup.Design.Register.RegisterUserActivity;
import com.blavasgps.covidcheckup.R;
import com.blavasgps.covidcheckup.commons.server.ServerConfig;
import com.blavasgps.covidcheckup.commons.server.ServerConnector;
import com.blavasgps.covidcheckup.commons.session.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private TextView topText,textU;
    private EditText userName, userPhone;
    private ConstraintLayout first, second;
    public static ProgressDialog progressDialog;
    public static String phonenumber;
    static SessionManager sessionManager;
    static Context contect;
    //Volley RequestQueue
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);
        contect = this;
        init();
    }

    public void init()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login, Please wait...");

        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.mobile_number);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }


    public void doLogin(String userName, String userPhone) {

        String urlParameters = "";
        try {
            progressDialog.show();
            urlParameters = "username=" + URLEncoder.encode(userName, "UTF-8")
                            + "&mobile=" + URLEncoder.encode("91" + userPhone, "UTF-8")
                            + "&device_id=" + URLEncoder.encode("1001", "UTF-8")
                            + "&device_type=" + URLEncoder.encode("android", "UTF-8")
                            + "&token_id=" + URLEncoder.encode("1003", "UTF-8");
            ServerConnector connector = new ServerConnector(urlParameters);
         //   connector.setHeaderTypeAppJson(true);
            connector.setDataDownloadListner(response -> {
                Log.e("Response", "Response : " + response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("success"))
                    {
                        if(jsonObject.getBoolean("success") == true)
                        {

                            String message = jsonObject.getString("message");
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                            phonenumber = userPhone;
                            OTPVerifyDialogFragment bottomSheetDialog = new OTPVerifyDialogFragment();
                            bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

                           /* SessionManager sessionManager = new SessionManager(LoginActivity.this);
                            sessionManager.createLoginSession(jsonObject.getString("user_id"),
                                    jsonObject.getString("logo"),
                                    jsonObject.getString("company_name")); */
                        }else
                        {
                            Toast.makeText(this, "Please Insert correct number", Toast.LENGTH_LONG).show();
//                            loginView.showErrorMessage(jsonObject.getString("message"));
                        }
                    }else
                    {
                        Toast.makeText(this, "No Result Found", Toast.LENGTH_LONG).show();
  //                      loginView.showErrorMessage(jsonObject.getString("message"));
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    progressDialog.dismiss();
                }
            });
            connector.execute(ServerConfig.Login_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verifyOTP(String otp, String userPhone){
        String urlParameters = "";
        try {
            progressDialog.show();
            //Called To Show Loading
            //   loginView.showLoading();
            //Call when user successfully login
            urlParameters =
                    "mobile=" + URLEncoder.encode("91" + userPhone, "UTF-8")
                            + "&opt_verifyed=" + URLEncoder.encode(otp, "UTF-8");
            ServerConnector connector = new ServerConnector(urlParameters);
            connector.setDataDownloadListner(response -> {
                Log.e("Response", "Response : " + response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("success"))
                    {
                        if(jsonObject.getBoolean("success") == true)
                        {

                            sessionManager.createLoginSession(jsonObject.getString("id"),
                                    jsonObject.getString("mobile"),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("userverified"));

                            String message = jsonObject.getString("message");
                            Toast.makeText(contect, message, Toast.LENGTH_LONG).show();
                            if (jsonObject.getString("userverified").equalsIgnoreCase("2")) {

                                Intent intent = new Intent(contect, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                contect.startActivity(intent);
                            }else {
                                Intent intent = new Intent(contect, RegisterUserActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                contect.startActivity(intent);
                            }


                        }else
                        {
                            String message = jsonObject.getString("message");
                            Toast.makeText(contect, message, Toast.LENGTH_LONG).show();
//                            loginView.showErrorMessage(jsonObject.getString("message"));
                        }
                    }else
                    {
                        //                      loginView.showErrorMessage(jsonObject.getString("message"));
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    progressDialog.dismiss();
                }
            });
            connector.execute(ServerConfig.Login_Otp_Verify_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginBtn:

                if(userName.getText().toString().isEmpty())
                {
                    userName.setError("Please enter Full Name");
                }else if(userPhone.getText().toString().isEmpty())
                {
                    userPhone.setError("Please enter Mobile Number");
                }else if(userPhone.getText().toString().length() != 10)
                {
                  userPhone.setError("Please enter 10 digit Mobile Number only ");
                }else {

                    doLogin(userName.getText().toString().trim(), userPhone.getText().toString().trim());
                }
                break;
        }
    }

    public static class OTPVerifyDialogFragment extends BottomSheetDialogFragment {

        @Override
        public void setupDialog(Dialog dialog, int style) {
            View contentView = View.inflate(getContext(), R.layout.layout_otp_verify, null);

            Button verifyOTPBtn = contentView.findViewById(R.id.verifyOTPBtn);
            EditText otpET = contentView.findViewById(R.id.otpET);
            verifyOTPBtn.setOnClickListener(v -> {
                if(otpET.getText().toString().isEmpty())
                {
                    otpET.setError("Please enter OTP for Verification");
                }else {
                    verifyOTP(otpET.getText().toString(), phonenumber);
                }
            });

            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
            bottomSheetDialog.setContentView(contentView);
            bottomSheetDialog.setCancelable(false);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            try {
                Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
                behaviorField.setAccessible(true);
                final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING){
                            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
