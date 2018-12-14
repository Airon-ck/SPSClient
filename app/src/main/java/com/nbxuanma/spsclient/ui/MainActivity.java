package com.nbxuanma.spsclient.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nbxuanma.spsclient.R;
import com.nbxuanma.spsclient.statusbar.StatusBarUtil;
import com.nbxuanma.spsclient.utils.JsonParse;

import org.apache.http.Header;
import org.json.JSONArray;

import app.socketlib.com.library.ContentServiceHelper;
import app.socketlib.com.library.listener.SocketResponseListener;
import app.socketlib.com.library.socket.SessionManager;
import app.socketlib.com.library.socket.SocketConfig;
import app.socketlib.com.library.utils.Contants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SocketResponseListener {

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

    private static final String HOST = "119.3.58.181";
    private static final int PORT = 8085;
    private String token = "", url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this);
        SocketConfig socketConfig = new SocketConfig.Builder(getApplicationContext())
                .setIp(HOST)//ip
                .setPort(PORT)//端口
                .setReadBufferSize(10240)//readBuffer
                .setIdleTimeOut(30)//客户端空闲时间,客户端在超过此时间内不向服务器发送数据,则视为idle状态,则进入心跳状态
                .setTimeOutCheckInterval(10)//客户端连接超时时间,超过此时间则视为连接超时
                .setRequestInterval(10)//请求超时间隔时间
                .setHeartbeatRequest("(1,1)\r\n")//与服务端约定的发送过去的心跳包
                .setHeartbeatResponse("(10,10)\r\n") //与服务端约定的接收到的心跳包
                .builder();
        ContentServiceHelper.bindService(this, socketConfig);
        SessionManager.getInstance().setReceivedResponseListener(this);
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

    @Override
    public void socketMessageReceived(String msg) {
        Log.i("TAG", "OnReceiveMsg:" + msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContentServiceHelper.unBindService(this);
    }

    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", token);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Tag", "----->" + response.toString());
                int code = JsonParse.getStatus(response.toString());
                if (code == 1) {

                } else {
                    //获取数据失败
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

}
