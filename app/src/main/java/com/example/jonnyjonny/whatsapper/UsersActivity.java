package com.example.jonnyjonny.whatsapper;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    private ArrayList<String>waUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        final ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

         waUsers = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, waUsers);
        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.swipe);
        try {
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseUser user : objects) {
                            waUsers.add(user.getUsername());
                        }
                        listView.setAdapter(adapter);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try{
                    ParseQuery<ParseUser>parseQuery=ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

                    parseQuery.whereNotContainedIn("username",waUsers);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if(objects.size()>0){
                                if(e==null){
                                    for (ParseUser user:objects){
                                        waUsers.add(user.getUsername());
                                    }
                                    adapter.notifyDataSetChanged();
                                    if (mySwipeRefreshLayout.isRefreshing()){
                                        mySwipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            }else {
                                if (mySwipeRefreshLayout.isRefreshing()){
                                    mySwipeRefreshLayout.setRefreshing(false);
                                }
                            }

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(UsersActivity.this,WhatsAppMessageActivity.class);
        intent.putExtra("selectedUser",waUsers.get(position));
        startActivity(intent);
    }
}

