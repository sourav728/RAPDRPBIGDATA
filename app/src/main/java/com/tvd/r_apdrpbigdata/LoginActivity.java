package com.tvd.r_apdrpbigdata;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.tvd.r_apdrpbigdata.values.FunctionCall;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText name, password;
    ConstraintLayout root_layout;
    FunctionCall functionCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        login = findViewById(R.id.btn_login);
        initialize();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(name.getText().toString())) {
                    if (!TextUtils.isEmpty(password.getText().toString())) {
                        if (name.getText().toString().equals("Zoola Kapasi") && password.getText().toString().equals("1")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            functionCall.setSnackBar(LoginActivity.this, root_layout, "Please Enter Valid Credentials!!");
                    } else
                        functionCall.setSnackBar(LoginActivity.this, root_layout, "Please Enter Password!!");
                } else
                    functionCall.setSnackBar(LoginActivity.this, root_layout, "Please Enter Name!!");
            }
        });
    }

    private void initialize() {
        name = findViewById(R.id.reg_name_et);
        password = findViewById(R.id.reg_password_et);
        root_layout = findViewById(R.id.root_layout);
        functionCall = new FunctionCall();
    }

}
