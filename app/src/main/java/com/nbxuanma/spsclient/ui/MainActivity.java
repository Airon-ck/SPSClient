package com.nbxuanma.spsclient.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nbxuanma.spsclient.R;
import com.nbxuanma.spsclient.client.ClientThread;
import com.nbxuanma.spsclient.entity.CarBean;
import com.nbxuanma.spsclient.keyboard.DeleteCallback;
import com.nbxuanma.spsclient.keyboard.HideCallback;
import com.nbxuanma.spsclient.keyboard.KeyCallback;
import com.nbxuanma.spsclient.keyboard.KeyboardHandle;
import com.nbxuanma.spsclient.keyboard.ShowCallback;
import com.nbxuanma.spsclient.server.WebServer;
import com.nbxuanma.spsclient.statusbar.StatusBarUtil;
import com.nbxuanma.spsclient.utils.Config;
import com.nbxuanma.spsclient.utils.DialogBuild;
import com.nbxuanma.spsclient.utils.MyEventBus;
import com.nbxuanma.spsclient.utils.PerfectClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HideCallback, ShowCallback, DeleteCallback, KeyCallback {

    @BindView(R.id.Re_title)
    RelativeLayout ReTitle;
    @BindView(R.id.et_license)
    TextView etLicense;
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
    @BindView(R.id.kbv_addcar)
    KeyboardView mKeyboardView;
    @BindView(R.id.et_one)
    EditText etOne;
    @BindView(R.id.et_two)
    EditText etTwo;
    @BindView(R.id.et_three)
    EditText etThree;
    @BindView(R.id.et_four)
    EditText etFour;
    @BindView(R.id.et_five)
    EditText etFive;
    @BindView(R.id.et_six)
    EditText etSix;
    @BindView(R.id.et_seven)
    EditText etSeven;
    @BindView(R.id.et_eight)
    EditText etEight;
    @BindView(R.id.lin_license)
    LinearLayout linLicense;
    @BindView(R.id.key_sure)
    TextView keySure;
    @BindView(R.id.txt_sure)
    TextView txtSure;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.Re_edit)
    RelativeLayout ReEdit;
    @BindView(R.id.txt_prompt)
    TextView txtPrompt;
    @BindView(R.id.Re_origin)
    RelativeLayout ReOrigin;

    private static final String TAG = "TAG";
    private Activity activity;
    private ClientThread clientThread;
    private WebServer server;
    private String[] pr = new String[]{"浙", "京", "粤", "津", "晋", "冀", "黑", "吉", "辽", "蒙", "苏",
            "沪", "皖", "赣", "鲁", "豫", "鄂", "湘", "闽", "桂", "渝", "琼", "川", "贵", "云", "藏", "陕", "甘", "青",
            "宁", "新"};
    private String[] pr2 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
            "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", "港", "奥", "学"};
    private Keyboard k2, k1;
    KeyboardHandle keyboardHandle = null;
    private EditText mEtEight = null;
    private EditText mEtCarnum[] = new EditText[8];
    private boolean CanModify = false;
    private String License = "";

    @Override
    protected void onStart() {
        super.onStart();
        new Thread() {
            public void run() {
                server = new WebServer();
                server.start();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this);
        EventBus.getDefault().register(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity = this;
        k1 = new Keyboard(this, R.xml.province);
        k2 = new Keyboard(this, R.xml.symbols);
        mEtCarnum[0] = etOne;
        mEtCarnum[1] = etTwo;
        mEtCarnum[2] = etThree;
        mEtCarnum[3] = etFour;
        mEtCarnum[4] = etFive;
        mEtCarnum[5] = etSix;
        mEtCarnum[6] = etSeven;
        mEtCarnum[7] = etEight;
        keyboardHandle = KeyboardHandle.creator(activity, mEtCarnum, mKeyboardView, this);
        keyboardHandle.setHideCallback(this);
        keyboardHandle.setShowCallback(this);
        keyboardHandle.setDeleteCallback(this);
        keyboardHandle.setKeyBoard(k1, pr).setKeyBoard(k2, pr2).useKeyBoard(k1);
        btnFree.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Send(etLicense.getText().toString().trim(), 0);
            }
        });
        btnToll.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Send(etLicense.getText().toString().trim(), 1);
            }
        });
        ivModify.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ReEdit.setVisibility(View.VISIBLE);
                ReOrigin.setVisibility(View.INVISIBLE);
                for (int i = 0; i < License.length(); i++) {
                    mEtCarnum[i].setText(License.substring(i, i + 1));
                }
            }
        });
        txtSure.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ReEdit.setVisibility(View.INVISIBLE);
                ReOrigin.setVisibility(View.VISIBLE);
                if (CanModify) {
                    Send(keyboardHandle.getContent(), 2);
                    etLicense.setText(keyboardHandle.getContent());
                } else {
                    DialogBuild.showCustom(view, "车辆已出场，不可再修改车牌！", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                keyboardHandle.hideKeyboard();
            }
        });
        txtCancel.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ReEdit.setVisibility(View.INVISIBLE);
                ReOrigin.setVisibility(View.VISIBLE);
                keyboardHandle.hideKeyboard();
            }
        });
        txtSetting.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                DialogBuild.showCustom(view, "确定进入设置界面修改IP和Port，这将会影响运作！", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, SettingActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });
        keySure.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                for (EditText e : mEtCarnum) {
                    e.clearFocus();
                }
                keyboardHandle.hideKeyboard();
                Log.i(TAG, "KeyContent:" + keyboardHandle.getContent());
            }
        });

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

                    } else if (msg.obj.equals("ERR")) {
                        Toast.makeText(activity, "ERR", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    public void Send(final String str, final int type) {
        if (str.isEmpty()) {
            Toast.makeText(activity, "车牌不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
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
                try {
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    Toast.makeText(activity, "请检查IP相关设置！", Toast.LENGTH_LONG).show();
                }

            }
        }, 150);

    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(MyEventBus message) {
        if (message.tag == Config.ReceiveMsg) {
            String content = message.bundle.getString("content");
            Log.i(TAG, "content:" + content);
            CarBean bean = new Gson().fromJson(content, CarBean.class);
            etLicense.setText(bean.getLicense());
            etEight.setVisibility(bean.getLicense().length() == 8 ? View.VISIBLE : View.GONE);
            License = bean.getLicense();
            etLicense.setText(License);
            if (!bean.getImgcar().isEmpty()) {
                byte[] input = Base64.decode(bean.getImgcar(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);
                ivCar.setImageBitmap(bitmap);
            } else {
                ivCar.setImageResource(R.mipmap.app_logo);
            }
//            Glide.with(activity).load(bean.getImgcar()).error(R.mipmap.ic_launcher).into(ivCar);
            txtStatus.setText(bean.getStatus() == 0 ? "待出场" : "已出场");//status:0待出场/1已出场
            btnFree.setBackgroundColor(bean.getStatus() == 0 ? getResources().getColor(R.color.color139b17) : getResources().getColor(R.color.colorE6E6E6));
            btnFree.setClickable(bean.getStatus() == 0);
            btnToll.setBackgroundColor(bean.getStatus() == 0 ? getResources().getColor(R.color.colorf5303d) : getResources().getColor(R.color.colorE6E6E6));
            btnToll.setClickable(bean.getStatus() == 0);
            if (bean.getStatus() == 0) {
                CanModify = true;
            } else {
                CanModify = false;
            }
            Log.i(TAG, "Status:" + bean.getStatus());
//            ivModify.setClickable(bean.getStatus() == 0);
            txtAdmissionTime.setText(bean.getAdmissiontime());
            txtPlayingTime.setText(bean.getPlayingtime());
            txtParkingTime.setText(bean.getParkingtime());
            DecimalFormat df = new DecimalFormat("#.00");
            txtOrderAmount.setText("￥" + bean.getOrderamount());
            txtPaid.setText("￥" + bean.getPaid());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void keyHide() {
        keySure.setVisibility(View.GONE);
    }

    @Override
    public void keyShow() {
        keySure.setVisibility(View.VISIBLE);
    }

    @Override
    public void delete(int position) {
        if (position == 7) {
            mEtEight.setVisibility(View.GONE);
        }
    }

    @Override
    public void startEdit(int i) {
        if (i == 0) {
            keyboardHandle.useKeyBoard(k1);
        } else {
            keyboardHandle.useKeyBoard(k2);
        }
    }

}
