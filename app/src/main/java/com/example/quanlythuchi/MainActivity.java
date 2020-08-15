package com.example.quanlythuchi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Fragment.ChiFragment;
import com.example.quanlythuchi.Fragment.GioiThieuFragment;
import com.example.quanlythuchi.Fragment.ThongKeFragment;
import com.example.quanlythuchi.Fragment.ThuFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ThongKeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_thongke);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_khoanthu:
                getSupportActionBar().setTitle("Khoản Thu");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ThuFragment()).commit();
                break;
            case R.id.nav_khoanchi:
                getSupportActionBar().setTitle("Khoản Chi");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ChiFragment()).commit();
                break;
            case R.id.nav_thongke:
                getSupportActionBar().setTitle("Thống Kê");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ThongKeFragment()).commit();
                break;
            case R.id.nav_info:
                getSupportActionBar().setTitle("Giới Thiệu");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GioiThieuFragment()).commit();
                break;
            case R.id.nav_logout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông Báo!")
                        .setIcon(R.mipmap.ic_warning)
                        .setMessage("Bạn thực sự muốn thoát!")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog dialog1 = builder.create();
                                dialog1.dismiss();
                            }
                        });
                builder.show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}