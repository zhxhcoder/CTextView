package com.creditease.ctextview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxjavaActivity extends AppCompatActivity {
    EditText editTest;
    TextView flatMap;
    TextView snackBar;
    PublishSubject<String> mSubject;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initPublishSubject();

        editTest = findViewById(R.id.editTest);
        flatMap = findViewById(R.id.flatMap);
        snackBar = findViewById(R.id.snackBar);

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


        disposables.add(Observable.just(1, 2)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        //模拟网络请求
                        Thread.sleep(5000);
                        return 1;
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        //模拟网络请求
                        Thread.sleep(3000);
                        return Observable.fromArray(integer);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //TODO
                    }
                }, throwable -> {

                }));


        flatMap.setOnClickListener(v -> {
            SnackbarUtil.LongSnackbar(snackBar, "1妹子向你发来一条消息\n妹子向你发来一条消息\n妹子向你发来一条消息", SnackbarUtil.Info).show();
        });
        snackBar.setOnClickListener(v -> {
            SnackbarUtil.LongSnackbar(snackBar, "2妹子向你发来一条消息\n妹子向你发来一条消息\n妹子向你发来一条消息", SnackbarUtil.Info).show();
        });
    }

    private void initPublishSubject() {
        mSubject = PublishSubject.create();
        disposables.add(mSubject.debounce(500, TimeUnit.MILLISECONDS) // 这里设置了300ms的限制
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String keyword) throws Exception {// 这里会收到被限流后onNext发出的事件，过滤掉未到300ms发出的事件
                        if ("clear".equals(keyword)) {
                            Log.d("xxxx-2", "clear");
                        } else {
                            Log.d("xxxx-2", keyword);
                        }
                    }
                }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
