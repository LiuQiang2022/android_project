package com.sdmu.smartcampusapp_sdmu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdmu.myapplication.pojo.Student;
import com.sdmu.myapplication.utils.ToastUtils;
import com.sdmu.myapplication.utils.timeUtil;
import com.sdmu.myapplication.webapi.webAPI;
import com.sdmu.smartcampusapp_sdmu.R;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    private String str_uid, str_pwd;
    private SharedPreferences sp;
    private String isWeekend;
    @BindView(R.id.et_uid)
    EditText et_uid;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.bt_login2)
    Button bt_login2;
    @BindView(R.id.rb_student)
    RadioButton rb_student;
    @BindView(R.id.rb_teacher)
    RadioButton rb_teacher;
    @BindView(R.id.rb_admin)
    RadioButton rb_admin;
    @BindView(R.id.rg_login_identity)
    RadioGroup radioGroup;
    @BindView(R.id.cb_rempwd)
    CheckBox cb_rempwd;
    @BindView(R.id.cb_autologin)
    CheckBox cb_autologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = getSharedPreferences("sp_login", MODE_PRIVATE);//获取SharedPreferences对象
//        if(sp.getBoolean("is_pwd", false))
//        {
////            cb_rempwd.setChecked(true);
//            et_uid.setText(sp.getString("uid", ""));
//            et_pwd.setText(sp.getString("pwd", ""));
//            //判断自动登陆多选框状态
//            if(sp.getBoolean("is_auto", false))
//            {
////                cb_autologin.setChecked(true);
////                LoginByNetwork(sp.getString("uid",""),sp.getString("pwd",""));
//            }
//        }

    }


//    @OnCheckedChanged({R.id.cb_rempwd,R.id.cb_autologin})
//    public void onViewCheckedChanged(CompoundButton compoundButton,boolean isChecked) {
//        switch (compoundButton.getId()) {
//
//            case R.id.cb_rempwd:
//                str_uid = et_uid.getText().toString();
//                str_pwd = et_pwd.getText().toString();
//                if (isChecked) {
//                    cb_rempwd.setChecked(true);
//                    SharedPreferences.Editor editor = sp.edit();//获取editor对象
//                    editor.putString("uid",str_uid);//把UID和password放到SharedPreferences中
//                    editor.putString("pwd",str_pwd);
//                    editor.putBoolean("is_pwd",true);
//                    editor.apply();
//                }else{
//                    cb_rempwd.setChecked(false);
//                    SharedPreferences.Editor editor = sp.edit();//获取editor对象
//                    editor.putString("uid","");//把UID和password放到SharedPreferences中
//                    editor.putString("pwd","");
//                    editor.putBoolean("is_pwd",false);
//                    editor.apply();
//                }
//                break;
//            case R.id.cb_autologin:
//                if (isChecked) {
//                    cb_autologin.setChecked(true);
//                    SharedPreferences.Editor editor = sp.edit();//获取editor对象
//                    editor.putBoolean("is_auto",true);
//                    editor.apply();
//                }else{
//                    cb_autologin.setChecked(false);
//                    SharedPreferences.Editor editor = sp.edit();//获取editor对象
//                    editor.putBoolean("is_auto",false);
//                    editor.apply();
//                }
//                break;
//        }
//    }

    @OnClick({R.id.bt_login, R.id.bt_login2})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                str_uid = et_uid.getText().toString();
                str_pwd = et_pwd.getText().toString();
                if (TextUtils.isEmpty(str_uid) || TextUtils.isEmpty(str_pwd)) {
                    ToastUtils.showToast(this, "账号或密码为空，请重试！");
                } else {

                    LoginByNetwork(str_uid, str_pwd);

                }

                break;
            case R.id.bt_login2:
                break;
        }

    }

    public void LoginByNetwork(String str_uid, String str_pwd) {
        OkGo.<String>get(webAPI.mainHost + webAPI.stuloginURL)
                .params("sid", str_uid)
                .params("spwd", str_pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Student student = gson.fromJson(response.body(), Student.class);
                        if (student.getLogin_status().equals("查询成功")) {
                            try {
                                isWeekend = timeUtil.isWeekend();
                                System.out.println(isWeekend);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (isWeekend.equals("OK")) {
                                ToastUtils.showToast(LoginActivity.this, student.getSname() + "同学，周末愉快！");

                            } else {
                                ToastUtils.showToast(LoginActivity.this, student.getSname() + "同学，欢迎使用智慧校园！");
                            }

                            SharedPreferences.Editor editor = sp.edit();//获取editor对象
                            editor.putString("uid", student.getSid());//把UID和password放到SharedPreferences中
                            editor.putString("pwd", student.getSpwd());
                            editor.putString("name", student.getSname());

                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, com.sdmu.myapplication.MainActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(LoginActivity.this, "账号或密码错误，请重新输入！");
                            et_uid.setText("");
                            et_pwd.setText("");
                        }
                    }
                });

    }

}