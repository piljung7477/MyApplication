package com.project.main.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.project.application.GlobalApplication;
import com.project.service.NetworkService;
import com.project.splash.model.ConnectResult;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.eonpush.com.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    NetworkService networkService;;

    @Bind(R.id.textView) TextView textView;
    @Bind(R.id.textView2) TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        networkService = GlobalApplication.getInstance().getNetworkService();

        textView.setText("테스트입니다.");
        textView2.setText("테스트입니다.2");

        try {
            this.testCon();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void testCon() throws IOException {
        Call<ConnectResult> testCall = networkService.getConnection("test Call!!!");
        testCall.enqueue(new Callback<ConnectResult>() {
            @Override
            public void onResponse(Call<ConnectResult> call, Response<ConnectResult> response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONObject resultValue = new JSONObject(jsonObject.getString("result"));
                        String result = resultValue.getString("message");
                        Log.d(TAG, "jsonString : " + result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ConnectResult> call, Throwable t) {

            }
        });
    }
}
