package mmustapha.g_hub.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mmustapha.g_hub.R;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.ProfileFragmentListener{

    String mDevName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Check ./Index/Adapter/DevAdapter for Intent Source on line 106
        Intent intent = getIntent();
        mDevName = intent.getStringExtra("DEVELOPER_NAME");
    }

    /**
     * Retrieves the clicked Developer's Username
     * @return
     */
    @Override
    public String getDevName() {
        return mDevName;
    }
}