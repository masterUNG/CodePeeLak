package com.student.codemobile.myauthendemo;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.pixplicity.easyprefs.library.Prefs;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginBtn;

    // Check Runtime Permission -- BEGIN
    public void checkRuntimPermission() {
        Nammu.init(this);
        // Check Runtime Permission
        Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                Toast.makeText(MainActivity.this, "Manifest.permission.WRITE_EXTERNAL_STORAGE - Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionRefused() {
                finish();
            }
        });
    }

    //Method ในการโหลดครั้งแรก onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load Layout Activity_main
        setContentView(R.layout.activity_main);
        //bind ตัวแปร
        bindWidgets();
        setEvent();
        //Call Method RuntimePermission
        checkRuntimPermission();
        showAdmob();

        // setup preference Remember Username Password
        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(ContextWrapper.MODE_PRIVATE) //เลือกแบบ Private เพราะ Username Password เป็นข้อมูลสำคัญ
                .setPrefsName(getPackageName()) //จดจำ Package
                .setUseDefaultSharedPreference(true) //ถ้ามี Technology ใหม่ๆ ให้มันเรียนรู้ ใช้ได้กำสิ่งใหม่่ๆ
                .build();

        mUsernameEditText.setText(Prefs.getString(UserBean.COLUMN_USERNAME, ""));
        mPasswordEditText.setText(Prefs.getString(UserBean.COLUMN_PASSWORD, ""));
    }

    private void setEvent() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mUsernameEditText.setText("555");
                //Toast.makeText(MainActivity.this, "666", Toast.LENGTH_SHORT).show(); //Bug Code

                //CMAssetBundle.copyAssetFile("Account.db",false,getApplicationContext());
                CMAssetBundle.copyAssetFile("UserPassword.db", false, getApplicationContext());

                DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
                String _username = mUsernameEditText.getText().toString();
                String _password = mPasswordEditText.getText().toString();
                UserBean userBean = new UserBean();
                userBean.username = _username;
                userBean.password = _password;

                UserBean result = dbAdapter.query(_username);
                //Toast สำหรับแสดงข้อความ เช่นเหมือนกับ Msg
                //Toast.makeText(MainActivity.this, result.username, Toast.LENGTH_SHORT).show();

                if (result == null) {
                    Toast.makeText(MainActivity.this, "Insert", Toast.LENGTH_SHORT).show();
                    dbAdapter.insert(userBean);
                } else if (result.password.equals(_password) == false) {
                    Toast.makeText(MainActivity.this, "Update", Toast.LENGTH_SHORT).show();
                    dbAdapter.update(userBean);
                } else {
                    //Toast.makeText(MainActivity.this, "select", Toast.LENGTH_SHORT).show();

                    //backup account into preference
                    Prefs.putString(UserBean.COLUMN_USERNAME, _username);
                    Prefs.putString(UserBean.COLUMN_PASSWORD, _password);

                    //เหมือนกับ ซองจดหมาย แล้วข้างในซองจดหมายอาจมีอะไรหลายอย่าง ใช้สำหรับสื่อสารข้าม object
                    Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
                    //ต้องการ Put Intent ข้อมูล ทั้ง result แต่ต้องไปทำที่หน้า UserBean ก่อน ไม่งั้น Error
                    i.putExtra(UserBean.TABLE_NAME, result);

                    startActivity(i);
                }


                /*DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
                UserBean userBean = new UserBean();
                userBean.username = "peeth";
                userBean.password = "Rueng";
                userBean.isPasswordRemembered = 1;

                //dbAdapter.insert(userBean);
                //dbAdapter.update(userBean);
                UserBean result = dbAdapter.query("peeth");
                if (result !=null){
                    Toast.makeText(MainActivity.this, result.password, Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
                startActivity(i);*/
            }
        });
    }

    private void bindWidgets() {
        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mLoginBtn = (Button) findViewById(R.id.LoginButton);
    }

    //ต้องเข้าเว็บ https://apps.admob.com/admob-signup/create-account
    private void showAdmob() {
        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        adView.loadAd(adRequestBuilder.build());
    }
}
