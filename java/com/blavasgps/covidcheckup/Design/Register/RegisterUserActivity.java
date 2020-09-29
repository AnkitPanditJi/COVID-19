package com.blavasgps.covidcheckup.Design.Register;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.blavasgps.covidcheckup.Design.MainActivity;
import com.blavasgps.covidcheckup.R;
import com.blavasgps.covidcheckup.commons.server.ServerConfig;
import com.blavasgps.covidcheckup.commons.server.ServerConnector;
import com.blavasgps.covidcheckup.commons.session.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup rbg;
    Button registerUser;
    private EditText UserAge, HomeAddress, CityAddress, StateAddress, CountryAddress;
    String locCityName, locStateName, locCoutryName;
    double locLatitude, loclongitude;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;

    ProgressDialog progressDialog;


    String address = ""; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city = "";
    String state = "";
    String country = "";
    String postalCode = "";
    String knownName = "";

    RadioButton gender;

    SessionManager sessionManager;
    HashMap<String, String> user;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();
        progressDialog = new ProgressDialog(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        rbg=(RadioGroup) findViewById(R.id.radioGrp);

        UserAge = (EditText)findViewById(R.id.user_age);
        HomeAddress = (EditText)findViewById(R.id.house_address);
        CityAddress = (EditText)findViewById(R.id.city);
        StateAddress = (EditText)findViewById(R.id.state);
        CountryAddress= (EditText)findViewById(R.id.country);

        registerUser=(Button) findViewById(R.id.register_user);

        registerUser.setOnClickListener(this);

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    locLatitude = location.getLatitude();
                                    loclongitude = location.getLongitude();

                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(RegisterUserActivity.this, Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to

                                        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        city = addresses.get(0).getLocality();
                                        state = addresses.get(0).getAdminArea();
                                        country = addresses.get(0).getCountryName();
                                        postalCode = addresses.get(0).getPostalCode();
                                        knownName = addresses.get(0).getFeatureName();

                                        HomeAddress.setText(address);
                                        CityAddress.setText(city);
                                        StateAddress.setText(state);
                                        CountryAddress.setText(country);
                                        locCityName = city;

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                  }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
          //  latTextView.setText(mLastLocation.getLatitude()+"");
         //   lonTextView.setText(mLastLocation.getLongitude()+"");

            locLatitude = mLastLocation.getLatitude();
            loclongitude = mLastLocation.getLongitude();

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(RegisterUserActivity.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to

             address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
             city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
             country = addresses.get(0).getCountryName();
             postalCode = addresses.get(0).getPostalCode();
             knownName = addresses.get(0).getFeatureName();

            HomeAddress.setText(address);
            CityAddress.setText(city);
            StateAddress.setText(state);
            CountryAddress.setText(country);
            locCityName = city;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_user:

                int selected=rbg.getCheckedRadioButtonId();
                gender=(RadioButton) findViewById(selected);
                if(gender == null)
                {
                    Toast.makeText(getApplicationContext(),"Please Select Gender.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(UserAge.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter your age.",Toast.LENGTH_LONG).show();
                    return;
                }
                doRegister(user.get(SessionManager.KEY_MOBILE));
                break;
                default:
                    break;
        }
    }

    public  void doRegister(String userPhone){
        String urlParameters = "";
        try {
            progressDialog.setMessage("Loading, Please wait..");
            progressDialog.show();
            //Called To Show Loading
            //   loginView.showLoading();
            //Call when user successfully login
            urlParameters =
                    "mobile=" + URLEncoder.encode(userPhone, "UTF-8")
                            + "&gender=" + URLEncoder.encode(gender.getText().toString(), "UTF-8")
                            + "&longitude=" + URLEncoder.encode(String.valueOf(loclongitude), "UTF-8")
                            + "&latitude=" + URLEncoder.encode(String.valueOf(locLatitude), "UTF-8")
                            + "&age=" + URLEncoder.encode(UserAge.getText().toString(), "UTF-8")
                            + "&address=" + URLEncoder.encode(address, "UTF-8")
                            + "&state=" + URLEncoder.encode(state, "UTF-8")
                            + "&city=" + URLEncoder.encode(city, "UTF-8");
            ServerConnector connector = new ServerConnector(urlParameters);
            connector.setDataDownloadListner(response -> {
                Log.e("Response", "Response : " + response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("success"))
                    {
                        String message = jsonObject.getString("message");
                        if(jsonObject.getBoolean("success") == true)
                        {
                            sessionManager.updateUserVerified(jsonObject.getString("verified"));
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                        }else
                        {
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    progressDialog.dismiss();
                }
            });
            connector.execute(ServerConfig.Register);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
