package com.blavasgps.covidcheckup.Design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blavasgps.covidcheckup.Design.generalinfo.GeneralInfo;
import com.blavasgps.covidcheckup.Design.map.MapViewActivity;
import com.blavasgps.covidcheckup.Design.profile.ProfileActivity;
import com.blavasgps.covidcheckup.R;
import com.blavasgps.covidcheckup.commons.log.L;
import com.blavasgps.covidcheckup.commons.server.ServerConfig;
import com.blavasgps.covidcheckup.commons.server.ServerConnector;
import com.blavasgps.covidcheckup.commons.session.SessionManager;
import com.blavasgps.covidcheckup.commons.utils.AppUtils;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    CardView TravelledCard, FeverCard, DryCoughCard, BreatingCard, TirenessCard, ConnectCoronaCard;
    Boolean cardTravelled=false, cardBreathing=false, cardFever=false, cardDryCough=false, cardTireness=false, cardConnectCorona=false;
    TextView UserName;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    HashMap<String, String> user;
    Button loginBtn;

    LinearLayout generalInfoll, mapViewll, profilell;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Requesting, Please wait...");

        TravelledCard = (CardView)findViewById(R.id.card_travelled);
        FeverCard = (CardView)findViewById(R.id.card_fever);
        DryCoughCard = (CardView)findViewById(R.id.card_dry_cough);
        BreatingCard = (CardView)findViewById(R.id.card_breathing);
        TirenessCard = (CardView)findViewById(R.id.card_tireness);
        ConnectCoronaCard = (CardView)findViewById(R.id.card_connect_corona);
        UserName = (TextView)findViewById(R.id.uname);
        UserName.setText("Welcome " + user.get(SessionManager.KEY_USERNAME_NAME));

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);        
        FeverCard.setOnClickListener(this);
        DryCoughCard.setOnClickListener(this);
        BreatingCard.setOnClickListener(this);
        TirenessCard.setOnClickListener(this);
        ConnectCoronaCard.setOnClickListener(this);
        TravelledCard.setOnClickListener(this);

        generalInfoll = findViewById(R.id.generalInfoll);
        mapViewll = findViewById(R.id.mapViewll);
        profilell = findViewById(R.id.profilell);

        generalInfoll.setOnClickListener(this);
        mapViewll.setOnClickListener(this);
        profilell.setOnClickListener(this);

        setupToolbar();
    }

    public void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  Covid-19 Checkup");
        setSupportActionBar(toolbar);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
     //   getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        toolbar.setNavigationOnClickListener(v -> { onBackPressed(); });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        L.e("item : " +item.getItemId());
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.language_menu) {
           /* Intent intent=new  Intent(getApplicationContext(),ChangePassword.class);
            startActivity(intent);*/
            //Change Application level locale
            return true;
        }
        if (id == R.id.english) {
            AppUtils.setAppLanguage(this, AppUtils.Language.ENGLISH);
            return true;
        }  if (id == R.id.hindi) {
            AppUtils.setAppLanguage(this, AppUtils.Language.HINDI);
            return true;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_travelled:
                if (!cardTravelled) {
                    cardTravelled = true;
                    TravelledCard.setBackgroundResource(R.drawable.customer_background_color);
                }else {
                    cardTravelled = false;
                    TravelledCard.setBackgroundResource(R.drawable.custom_background_unselect);
                }
                break;
            case R.id.card_fever:
                if (!cardFever) {
                    cardFever = true;
                    FeverCard.setBackgroundResource(R.drawable.customer_background_color);
                }else {
                    cardFever = false;
                    FeverCard.setBackgroundResource(R.drawable.custom_background_unselect);
                }
                break;
            case R.id.card_dry_cough:
                if (!cardDryCough) {
                    cardDryCough = true;
                    DryCoughCard.setBackgroundResource(R.drawable.customer_background_color);
                }else {
                    cardDryCough = false;
                    DryCoughCard.setBackgroundResource(R.drawable.custom_background_unselect);
                }
                break;
            case R.id.card_breathing:
                if (!cardBreathing) {
                    cardBreathing = true;
                    BreatingCard.setBackgroundResource(R.drawable.customer_background_color);
                }else {
                    cardBreathing = false;
                    BreatingCard.setBackgroundResource(R.drawable.custom_background_unselect);
                }
                break;
            case R.id.card_tireness:
                if (!cardTireness) {
                    cardTireness = true;
                    TirenessCard.setBackgroundResource(R.drawable.customer_background_color);
                }else {
                    cardTireness = false;
                    TirenessCard.setBackgroundResource(R.drawable.custom_background_unselect);
                }
                break;
            case R.id.card_connect_corona:
                if (!cardConnectCorona) {
                    cardConnectCorona = true;
                    ConnectCoronaCard.setBackgroundResource(R.drawable.customer_background_color);
                }else {
                    cardConnectCorona = false;
                    ConnectCoronaCard.setBackgroundResource(R.drawable.custom_background_unselect);
                }
                break;
            case R.id.loginBtn:
                if(!cardTravelled && !cardFever && !cardDryCough && !cardBreathing && !cardTireness && !cardConnectCorona)
                {
                    Toast.makeText(this, "Please select at least one symptoms.", Toast.LENGTH_SHORT).show();
                }else
                {
                    doTest();
                }
                break;
            case R.id.generalInfoll:
                startActivity(new Intent(this, GeneralInfo.class));
                break;
            case R.id.mapViewll:
                startActivity(new Intent(this, MapViewActivity.class));
                break;
            case R.id.profilell:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            default:
                    break;
        }
    }

public void doTest() {

        String urlParameters = "";
        try {
            progressDialog.show();
            urlParameters = "id=" + URLEncoder.encode(user.get(SessionManager.KEY_USER_ID), "UTF-8")
                            + "&recentvisit=" + URLEncoder.encode(cardTravelled ? "1" : "0", "UTF-8")
                            + "&fever=" + URLEncoder.encode(cardFever ? "1" : "0", "UTF-8")
                            + "&drycough=" + URLEncoder.encode(cardDryCough ? "1" : "0", "UTF-8")
                            + "&brathproblem=" + URLEncoder.encode(cardBreathing ? "1" : "0", "UTF-8")
                            + "&tireness=" + URLEncoder.encode(cardTireness ? "1" : "0", "UTF-8")
                            + "&coronatouch=" + URLEncoder.encode(cardConnectCorona ? "1" : "0", "UTF-8");
            ServerConnector connector = new ServerConnector(urlParameters);
         //   connector.setHeaderTypeAppJson(true);
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
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                        }else
                        {
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        Toast.makeText(this, "No Result Found", Toast.LENGTH_LONG).show();                      
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    progressDialog.dismiss();
                }
            });
            connector.execute(ServerConfig.Corona_Test_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
