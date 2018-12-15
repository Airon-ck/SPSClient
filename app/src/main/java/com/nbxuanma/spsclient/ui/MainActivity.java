package com.nbxuanma.spsclient.ui;

import android.annotation.SuppressLint;
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

import com.nbxuanma.spsclient.ClientThread;
import com.nbxuanma.spsclient.MyServer;
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

    private ClientThread clientThread;
    private MyServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this);
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
//        server = new MyServer();
    }

    @OnClick({R.id.btn_free, R.id.btn_toll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_free:
                Log.i("TAG", "btn_free:");
                Send("M,浙B11111");
                break;
            case R.id.btn_toll:
                Log.i("TAG", "btn_toll:");
                Send("8888");
                break;
        }
    }

    //用于发送接收到的服务端的消息，显示在界面上
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("TAG", msg.obj.toString());

        }
    };

    public void Send(final String str) {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = str.trim();
        clientThread.revHandler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
