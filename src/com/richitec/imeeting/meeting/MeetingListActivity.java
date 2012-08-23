package com.richitec.imeeting.meeting;

import com.richitec.imeeting.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MeetingListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list_activity_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meeting_list_activity_layout, menu);
        return true;
    }
}
