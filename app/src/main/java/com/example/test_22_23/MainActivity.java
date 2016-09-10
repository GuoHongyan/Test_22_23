package com.example.test_22_23;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView textViewContacts;
    ContentResolver resolver;
    Button buttonGetAll;
    String message=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewContacts=(TextView)findViewById(R.id.textViewContacts) ;
        buttonGetAll=(Button)findViewById(R.id.buttonGetAll);
        resolver = getContentResolver();
        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
                true, new Content(new Handler()));
        buttonGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //   String number=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    message+= "id:" + id + " name:" + name + "\n\n";
                }
                cursor.close();
                textViewContacts.setText(message);
            }
        });

    }

    public class Content extends ContentObserver {
        public Content(Handler handler) {
            super(handler);
        }
        @Override
        public void onChange(boolean change) {
           Toast.makeText(MainActivity.this,"联系人变化",Toast.LENGTH_LONG).show();
        }
    }

}
