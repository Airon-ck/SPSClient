package com.nbxuanma.spsclient.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nbxuanma.spsclient.R;
import com.nbxuanma.spsclient.statusbar.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.Re_title)
    RelativeLayout ReTitle;
    @BindView(R.id.et_license)
    EditText etLicense;
    @BindView(R.id.View_2)
    View View2;
    @BindView(R.id.txt_title_1)
    TextView txtTitle1;
    @BindView(R.id.txt_admission_time)
    TextView txtAdmissionTime;
    @BindView(R.id.txt_title_2)
    TextView txtTitle2;
    @BindView(R.id.txt_playing_time)
    TextView txtPlayingTime;
    @BindView(R.id.txt_title_3)
    TextView txtTitle3;
    @BindView(R.id.txt_parking_time)
    TextView txtParkingTime;
    @BindView(R.id.txt_title_4)
    TextView txtTitle4;
    @BindView(R.id.txt_order_amount)
    TextView txtOrderAmount;
    @BindView(R.id.txt_title_5)
    TextView txtTitle5;
    @BindView(R.id.txt_paid)
    TextView txtPaid;
    @BindView(R.id.btn_free)
    Button btnFree;
    @BindView(R.id.btn_toll)
    Button btnToll;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.iv_car)
    ImageView ivCar;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.Re_right)
    RelativeLayout ReRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    @OnClick({R.id.btn_free, R.id.btn_toll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_free:

                break;
            case R.id.btn_toll:

                break;
        }
    }

}
