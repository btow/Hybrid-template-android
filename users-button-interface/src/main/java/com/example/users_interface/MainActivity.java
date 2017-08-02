package com.example.users_interface;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Заменить на фрагменты
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    mTextMessage.setText(R.string.title_1);
                    return true;
                case R.id.navigation_2:
                    mTextMessage.setText(R.string.title_2);
                    return true;
                case R.id.navigation_3:
                    mTextMessage.setText(R.string.title_3);
                    return true;
                case R.id.navigation_4:
                    mTextMessage.setText(R.string.title_4);
                    return true;
                case R.id.navigation_5:
                    mTextMessage.setText(R.string.title_5);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
