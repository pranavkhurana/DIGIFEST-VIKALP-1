package com.example.anurag.vistar.Login.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anurag.vistar.Actvities.MainActivityTemp;
import com.example.anurag.vistar.Actvities.SpalshScreen;
import com.example.anurag.vistar.Actvities.SpeechToText;
import com.example.anurag.vistar.Actvities.UserActivity;
import com.example.anurag.vistar.ExtraClasses.CustomToast;
import com.example.anurag.vistar.ExtraClasses.Utility;
import com.example.anurag.vistar.MainActivity;
import com.example.anurag.vistar.R;
import com.example.anurag.vistar.WebView.WebViewActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;



public class Login_Fragment extends Fragment implements OnClickListener ,LocationListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    public static final String MY_PREFS_NAME = "myPref";
    ProgressBar progressBar;
    LocationManager locationManager;
   // Button move,move2;
    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;

    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);
//        move= (Button) view.findViewById(R.id.move);
//        move.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainActivityTemp.class));
//            }
//        });

//        move2=(Button) view.findViewById(move2);
//          move2.setOnClickListener(new OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        startActivity(new Intent(getContext(), WebViewActivity.class));
//    }
//});
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                //checkValidation();
//                if (Utility.isConnected(getContext())) {
//                    getLocation();
//                } else {
////            MyAlert alert = new MyAlert();
////            alert.setCancelable(false);
////            alert.show(getFragmentManager(), "MyAlert");
//                }
                startActivity(new Intent(getContext(),MainActivityTemp.class));
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Utility.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utility.SignUp_Fragment).commit();
                break;
        }

    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            //locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);
        } catch (SecurityException e) {
            Log.d("karma", "Excpetion " + e);
            e.printStackTrace();
        }
    }


    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utility.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            // Else do login and do your stuff
        else
            Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
                    .show();

    }

    @Override
    public void onLocationChanged(Location location) {
        String longitude = location.getLongitude() + "";
        String latitude = location.getLatitude() + "";
        //Toast.makeText(this,"Current Location: \" "+"Lat: "+ location.getLatitude() + " Long :" +location.getLongitude(),Toast.LENGTH_LONG).show();
        //locationManager.removeUpdates(getContext());
        SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("currentLong", longitude);
        editor.putString("currentLat", latitude);
        Log.d("karma","lat is "+latitude +"long is "+longitude);
        editor.apply();
        Log.d("karma", "Location Changed");
        startActivity(new Intent(getContext(),MainActivityTemp.class));


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }
}