package com.example.mandroid;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mandroid.dynamic_proxy.dao.SomeDao;
import com.example.mandroid.dynamic_proxy.dao.impl.SomeDaoImpl;
import com.example.mandroid.observer.Observer;
import com.example.mandroid.observer.ObserverDemoImpl;
import com.example.mandroid.observer.ObserverImpl;
import com.example.mandroid.retrofit.CustomViewActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    private Button btn, btn2;
    private TextView tv1;

    @Override
    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0.5f);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation -> {
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = (Float) animation.getAnimatedValue();
            window.setAttributes(params);
            valueAnimator.start();
        });
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        tv1 = (TextView) findViewById(R.id.tv1);
        btn.setOnClickListener(view -> {
            // Intent intent = new Intent(this, TestActivity.class);
            // startActivity(intent);
            /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
            }
            ContentResolver contentResolver = getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "onCreate: " +name+ number);
                }
            } */
            SomeDao someDao = (SomeDao) new SomeDaoImpl();
            SomeDao proxy = (SomeDao) Proxy.newProxyInstance(someDao.getClass().getClassLoader(),
                    someDao.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return null;
                        }
                    });
            String result = proxy.setMethod("demo");
            proxy.myMethod();
            System.out.println(result);
            ObserverDemoImpl observerDemo = new ObserverDemoImpl();
            Observer observer1 = (Observer) new ObserverImpl();
            Observer observer2 = (Observer) new ObserverImpl();

            observerDemo.addObserver(observer1);
            observerDemo.addObserver(observer2);
            observerDemo.setMessage("发送");
        });
        btn2.setOnClickListener(view->{
            Intent intent = new Intent(this, CustomViewActivity.class);
            startActivity(intent);
        });
    }

}