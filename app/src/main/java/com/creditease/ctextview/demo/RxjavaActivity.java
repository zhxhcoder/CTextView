package com.creditease.ctextview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class RxjavaActivity extends AppCompatActivity {
    EditText editTest;
    Disposable disposable;
    PublishSubject<String> mSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initPublishSubject();

        editTest = findViewById(R.id.editTest);
        editTest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mSubject.onNext("clear");
                } else {
                    mSubject.onNext(s.toString());
                }
            }
        });
    }

    private void initPublishSubject() {
        mSubject = PublishSubject.create();
        disposable = mSubject.debounce(500, TimeUnit.MILLISECONDS) // 这里设置了300ms的限制
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String keyword) throws Exception {// 这里会收到被限流后onNext发出的事件，过滤掉未到300ms发出的事件
                        if ("clear".equals(keyword)) {
                            Log.d("xxxx-2", "clear");
                        } else {
                            Log.d("xxxx-2", keyword);
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
