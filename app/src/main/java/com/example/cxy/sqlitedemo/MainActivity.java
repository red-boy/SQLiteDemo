package com.example.cxy.sqlitedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cxy.sqlitedemo.db.DBHelp;

/**
 * SQLite基础练习
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText btn_name, btn_password, btn_question, btn_answer;
    private Button btn_login, btn_register;
    private DBHelp mDBHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_name = (EditText) findViewById(R.id.tx_name);
        btn_password = (EditText) findViewById(R.id.tx_password);
        btn_question = (EditText) findViewById(R.id.tx_question);
        btn_answer = (EditText) findViewById(R.id.tx_answer);
        btn_register = (Button) findViewById(R.id.register);
        btn_login = (Button) findViewById(R.id.login);


        /**实现对多个EditText的文本监听*/
        TextChange textChange = new TextChange();
        btn_password.addTextChangedListener(textChange);
        btn_question.addTextChangedListener(textChange);
        btn_answer.addTextChangedListener(textChange);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        mDBHelp = new DBHelp(MainActivity.this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.register:
                if (mDBHelp.isExit(btn_name.getText().toString())) {
                    Toast.makeText(this, "该用户名已经注册过", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues cv = new ContentValues();
                cv.put("username", btn_name.getText().toString());
                cv.put("password", btn_password.getText().toString());
                cv.put("question", btn_question.getText().toString());
                cv.put("answer", btn_answer.getText().toString());
                mDBHelp.insert(cv);
                Toast.makeText(this, "注册完成", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    //实现接口自定义
    protected class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (btn_password.length() > 0 && btn_question.length() > 0 && btn_answer.length() > 0) {
                /**只有当所有信息填写上，按钮才有效*/
                btn_register.setEnabled(true);
            }

        }
    }

}
