package com.nbxuanma.spsclient.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nbxuanma.spsclient.R;
import com.nbxuanma.spsclient.client.ClientThread;
import com.nbxuanma.spsclient.entity.CarBean;
import com.nbxuanma.spsclient.server.MyServer;
import com.nbxuanma.spsclient.statusbar.StatusBarUtil;
import com.nbxuanma.spsclient.utils.Config;
import com.nbxuanma.spsclient.utils.MyEventBus;
import com.nbxuanma.spsclient.utils.PerfectClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.txt_setting)
    TextView txtSetting;
    @BindView(R.id.iv_modify)
    ImageView ivModify;
    @BindView(R.id.Re_license)
    RelativeLayout ReLicense;

    private static final String TAG = "TAG";
    private Activity activity;
    private ClientThread clientThread;
    private MyServer server;
    private boolean IsFree = false, IsToll = false, IsModify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this);
        EventBus.getDefault().register(this);
        activity = this;
        btnFree.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                IsFree = true;
                Send("浙B11111", 0);
            }
        });
        btnToll.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                IsToll = true;
                Send("浙B11111", 1);
            }
        });
        ivModify.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View View) {
                Send("浙B11111", 2);
            }
        });
        txtSetting.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        server = new MyServer();
    }

    //用于发送接收到的服务端的消息，显示在界面上
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("TAG", msg.obj.toString());
                    if (msg.obj.toString().equals("OK")) {
                        if (IsFree) {
                            btnFree.setBackgroundColor(getResources().getColor(R.color.colorE6E6E6));
                        }
                        if (IsToll) {
                            btnToll.setBackgroundColor(getResources().getColor(R.color.colorE6E6E6));
                        }
//                        btnFree.setClickable(false);
//                        btnToll.setClickable(false);
                    }
                    break;
            }
        }
    };

    public void Send(final String str, final int type) {
        clientThread = new ClientThread(activity, handler);
        new Thread(clientThread).start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                switch (type) {
                    case 0://免费
                        msg.obj = "M," + str.trim();
                        break;
                    case 1://收费
                        msg.obj = "C," + str.trim();
                        break;
                    case 2://修改车牌
                        msg.obj = "U," + str.trim();
                        break;
                }
                Log.i(TAG, "msg.obj:" + msg.obj);
                clientThread.revHandler.sendMessage(msg);
            }
        }, 100);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(MyEventBus message) {
        if (message.tag == Config.ReceiveMsg) {
            btnFree.setClickable(true);
            btnFree.setBackgroundColor(getResources().getColor(R.color.color139b17));
            btnToll.setClickable(true);
            btnToll.setBackgroundColor(getResources().getColor(R.color.colorf5303d));
            String content = message.bundle.getString("content");
            CarBean bean = new Gson().fromJson(content, CarBean.class);
            CarBean.ResultBean entity = bean.getResult();
            etLicense.setText(entity.getLicense());
            Glide.with(activity).load(entity.getImgcar()).into(ivCar);
            txtStatus.setText(entity.getStatus());
            txtAdmissionTime.setText(entity.getAdmissiontime());
            txtPlayingTime.setText(entity.getPlayingtime());
            txtParkingTime.setText(entity.getParkingtime());
            txtOrderAmount.setText("￥" + entity.getOrderamount());
            txtPaid.setText("￥" + entity.getPaid());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
