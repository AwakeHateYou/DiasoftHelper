package io.home.awake.diasofthelper.activity;

import android.content.Intent;

import android.os.AsyncTask;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;


import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import io.home.awake.diasofthelper.R;

import io.home.awake.diasofthelper.adapters.WorkListAdapter;
import io.home.awake.diasofthelper.model.ActualWorks;


public class MainActivity extends AppCompatActivity {
    private TextView loginField;
    private RecyclerView workRecyclerView;
    private ArrayList works;
    private WorkListAdapter adapter;
    private ActualWorksTask worksTask;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawer();
        Intent intent = getIntent();
        loginField = (TextView) findViewById(R.id.sampleText);
        workRecyclerView = (RecyclerView) findViewById(R.id.workRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        userID = intent.getLongExtra("userID", 0);
        worksTask = new ActualWorksTask(userID);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    worksTask.execute();
//                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        loginField.setText(getString(R.string.welcome) + " " + intent.getStringExtra("userName"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        worksTask.execute();
    }

    private void initListAdapter(ArrayList mDataSet) {
        workRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        workRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new WorkListAdapter(mDataSet, getApplicationContext());
        workRecyclerView.setAdapter(adapter);
    }


    protected void setDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_send_feedback);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();
    }

    public class ActualWorksTask extends AsyncTask<Void, Void, ActualWorks> {
        private Long userID;

        ActualWorksTask(Long userID) {
            this.userID = userID;
        }

        @Override
        protected ActualWorks doInBackground(Void... params) {
            try {
                final String url = "http://awakehateyou.hldns.ru:8080/works?userid=" + userID;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ActualWorks actualWorks = restTemplate.getForObject(url, ActualWorks.class);
                return actualWorks;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                ActualWorks actualWorks = null;
                return actualWorks;
            }
        }

        @Override
        protected void onPostExecute(ActualWorks actualWorks) {
            if (actualWorks != null) {
                initListAdapter(actualWorks.getActualWorks());
                System.out.print(actualWorks);
            } else {

            }
        }


    }
}

