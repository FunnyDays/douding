package com.ddgj.dd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ddgj.dd.R;
import com.ddgj.dd.bean.EnterpriseUser;
import com.ddgj.dd.bean.PersonalUser;
import com.ddgj.dd.fragment.EnterpriseFragment;
import com.ddgj.dd.fragment.PersonalFragment;
import com.ddgj.dd.util.user.UserHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 用户中心界面
 */
public class UserCenterActivity extends BaseActivity {

    private static final int PERSONAL_REQUEST_CODE = 2;
    private static final int ENTERPRISE_REQUEST_CODE = 3;
    private Object user = UserHelper.getInstance().getUser();
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initViews();
    }

    @Override
    public void initViews() {
        if (UserHelper.getInstance().getUser() instanceof PersonalUser) {//个人用户信息
            getFragmentManager().beginTransaction().add(R.id.content_container, new PersonalFragment()).commit();
        } else if (UserHelper.getInstance().getUser() instanceof EnterpriseUser) {//企业用信息
            getFragmentManager().beginTransaction().add(R.id.content_container, new EnterpriseFragment()).commit();
        }
    }

    public void backClick(View v) {
        finish();
    }

    public void updatePasswordClick(View v) {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        String userid = null;
        if (user instanceof PersonalUser) {
            userid = ((PersonalUser) user).getAccount();
        } else if (user instanceof EnterpriseUser) {
            userid = ((EnterpriseUser) user).getAccount();
        }
        intent.putExtra("user_id", userid);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == UpdatePasswordActivity.CHANGED) {//修改密码成功，关闭当前页面
            finish();
        } else if (requestCode == PERSONAL_REQUEST_CODE && resultCode == SUCCESS) {
            getFragmentManager().beginTransaction().replace(R.id.content_container, new PersonalFragment()).commit();
        } else if (requestCode == ENTERPRISE_REQUEST_CODE && resultCode == SUCCESS) {
            getFragmentManager().beginTransaction().add(R.id.content_container, new EnterpriseFragment()).commit();
        }
    }

    public void logoutClick(View v) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("您真的要退出登录吗？")
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmText("确认")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        MainActivity.update = true;
                        UserHelper.getInstance().logout();
                        sweetAlertDialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    public void editClick(View v) {
        if (user instanceof PersonalUser) {
            startActivityForResult(new Intent(this, EditPersonalUserInfoActivity.class), PERSONAL_REQUEST_CODE);
        } else if (user instanceof EnterpriseUser) {
            startActivityForResult(new Intent(this, EditEnterpriseUserInfoActivity.class), ENTERPRISE_REQUEST_CODE);
        }
    }
}
