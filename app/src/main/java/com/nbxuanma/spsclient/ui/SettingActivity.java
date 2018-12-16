package com.nbxuanma.spsclient.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbxuanma.spsclient.R;
import com.nbxuanma.spsclient.statusbar.StatusBarUtil;
import com.nbxuanma.spsclient.utils.PerfectClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.txt_back)
    TextView txtBack;
    @BindView(R.id.ed_ip)
    EditText edIp;
    @BindView(R.id.ed_port)
    EditText edPort;
    @BindView(R.id.bn_set)
    Button bnSet;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_setting);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this);
        sp=getSharedPreferences("User",MODE_PRIVATE);
        edIp.setHint(sp.getString("IP","119.3.58.181"));
        edPort.setHint(String.valueOf(sp.getInt("Port",8085)));
        editor = sp.edit();
        bnSet.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {

                if (edIp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "IP不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (edPort.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Port不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                editor.putString("IP", edIp.getText().toString().trim());
                editor.putInt("Port", Integer.parseInt(edPort.getText().toString().trim()));
                editor.apply();
            }
        });
        txtBack.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                finish();
            }
        });
    }

}
