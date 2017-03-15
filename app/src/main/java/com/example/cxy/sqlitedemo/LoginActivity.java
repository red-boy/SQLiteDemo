package com.example.cxy.sqlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cxy.sqlitedemo.db.DBHelp;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txName, txPassword;
    private Button mButton;
    private TextView tx_show;

    private DBHelp mDBHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txName = (EditText) findViewById(R.id.tx_name);
        txPassword = (EditText) findViewById(R.id.tx_password);
        tx_show = (TextView) findViewById(R.id.tx_show);

        mButton = (Button) findViewById(R.id.btn_login);

        mButton.setOnClickListener(this);

        mDBHelp = new DBHelp(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        String fromDB[] = mDBHelp.query3(txName.getText().toString());
        if (fromDB[0] != null) {
            if (txPassword.getText().toString().equals(fromDB[0])) {
                Toast.makeText(this, "密码正确，成功登录", Toast.LENGTH_SHORT).show();
                tx_show.setText(fromDB[1] + "/" + fromDB[2]);

            } else {
                Toast.makeText(this, "密码错误，登录失败:" + fromDB[0] + "/" + txName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "用户名不存在，请重新输入", Toast.LENGTH_SHORT).show();
        }

    }
}
