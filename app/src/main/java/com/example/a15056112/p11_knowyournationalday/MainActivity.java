package com.example.a15056112.p11_knowyournationalday;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aa;
    ArrayList<String> al;

    String getAccessCode;
    EditText etaccessCode;
    int score = 0;
    boolean key = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS},
                1);

        lv = (ListView)findViewById(R.id.lv);

        al = new ArrayList<String>();

        al.add("Singapore National Day is on 9 Aug");
        al.add("Singapore is 52 year old");
        al.add("Theme is '#OneNationTogether'");

        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);

        lv.setAdapter(aa);
        aa.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_friend) {

            String [] list = new String[] { "Email", "SMS"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend");

            builder.setItems(list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        String message = " ";
                        for (int i = 0; i < al.size(); i++) {
                            message += (i + 1) + ": " + al.get(i) + "\n";
                        }
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL,
                                new String[]{"15017608@rp.edu.sg"});
                        email.putExtra(Intent.EXTRA_SUBJECT,
                                "Test Email from C347");
                        email.putExtra(Intent.EXTRA_TEXT,
                                message);

                        email.setType("message/rfc822");

                        startActivity(Intent.createChooser(email,
                                "Choose an Email client :"));

                    } else if (which == 1) {

                        try{
                            String message = " ";
                            for (int i = 0; i < al.size(); i++) {
                                message += (i + 1) + ": " + al.get(i) + "\n";
                            }

                            /*
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.setData(Uri.parse("sms:")); */

                            SmsManager.getDefault().sendTextMessage("94566308",null,message,null,null);

                            /*
                            sendIntent.putExtra("sms_body", message);
                            startActivity(sendIntent); */

                            Toast.makeText(MainActivity.this, "SMS has been sent",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "SMS has not been sent",
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if(item.getItemId() == R.id.action_quiz) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout accessCode = (LinearLayout) inflater.inflate(R.layout.quiz, null);

            final RadioGroup rg = (RadioGroup) accessCode.findViewById(R.id.rg1);
            final RadioGroup rg2 = (RadioGroup) accessCode.findViewById(R.id.rg2);
            final RadioGroup rg3 = (RadioGroup) accessCode.findViewById(R.id.rg3);





            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Test Yourself!");
            builder.setView(accessCode);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int selectedButtonId = rg.getCheckedRadioButtonId();
                    int selectedButtonId2 = rg2.getCheckedRadioButtonId();
                    int selectedButtonId3 = rg3.getCheckedRadioButtonId();

                    /*
                    final RadioButton rb = (RadioButton)findViewById(selectedButtonId);
                    final RadioButton rb2 = (RadioButton)findViewById(selectedButtonId2);
                    final RadioButton rb3 = (RadioButton)findViewById(selectedButtonId3); */

                    if (selectedButtonId == R.id.radioButton2 &&
                           selectedButtonId2 == R.id.radioButton3 &&
                            selectedButtonId3 == R.id.radioButton5) {
                        score = 3;
                        Toast.makeText(MainActivity.this, "You score " + score, Toast.LENGTH_SHORT).show();
                    } else if (selectedButtonId == R.id.radioButton &&
                            selectedButtonId2 == R.id.radioButton3 &&
                            selectedButtonId3 == R.id.radioButton5) {
                        score = 2;
                        Toast.makeText(MainActivity.this, "You score " + score, Toast.LENGTH_SHORT).show();
                    } else if (selectedButtonId == R.id.radioButton2 &&
                            selectedButtonId2 == R.id.radioButton4 &&
                            selectedButtonId3 == R.id.radioButton5) {
                        score = 2;
                        Toast.makeText(MainActivity.this, "You score " + score, Toast.LENGTH_SHORT).show();
                    } else if (selectedButtonId == R.id.radioButton2 &&
                            selectedButtonId2 == R.id.radioButton3 &&
                            selectedButtonId3 == R.id.radioButton6) {
                        score = 2;
                        Toast.makeText(MainActivity.this, "You score " + score, Toast.LENGTH_SHORT).show();
                    } else if (selectedButtonId == R.id.radioButton &&
                            selectedButtonId2 == R.id.radioButton4 &&
                            selectedButtonId3 == R.id.radioButton5) {
                        score = 1;
                        Toast.makeText(MainActivity.this, "You score " + score, Toast.LENGTH_SHORT).show();
                    } else if (selectedButtonId == R.id.radioButton &&
                            selectedButtonId2 == R.id.radioButton4 &&
                            selectedButtonId3 == R.id.radioButton6) {
                        score = 0;
                        Toast.makeText(MainActivity.this, "You score " + score, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            builder.setNegativeButton("Dont know lah", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog alertDialog = builder.create();
                    alertDialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if(item.getItemId() == R.id.action_quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?");
            builder.setCancelable(false);
            builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            builder.setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.SEND_SMS);
                    if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    } else {

                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
        key = preferences.getBoolean("key", false);
        if (!key) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout accessCode = (LinearLayout) inflater.inflate(R.layout.access_code, null);

            etaccessCode = (EditText) accessCode.findViewById(R.id.editTextAccessCode);

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please login");
            builder.setView(accessCode);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (etaccessCode.getText().toString().equals("738964")) {
//                        lv = (ListView) findViewById(R.id.lv);
//
//                        al = new ArrayList<String>();
//
//                        al.add("Singapore National Day is on 9 Aug");
//                        al.add("Singapore is 52 year old");
//                        al.add("Theme is '#OneNationTogether'");
//
//                        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
//
//                        lv.setAdapter(aa);
//                        aa.notifyDataSetChanged();
                        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("key", true);
                        editor.apply();

                    } else {
                        finish();
                        Toast.makeText(MainActivity.this, "Incorrect Access Code", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
}
