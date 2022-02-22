package com.jorgesys.myjavaapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.jorgesys.myjavaapp.MyData.MAX_ELEMENTS;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    private static ArrayList<Integer> removedItems;

    static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(MyData._id[i],
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.drawableArray[i]
            ));
        }

        removedItems = new ArrayList<>();
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);

    }


    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            addItem(v);
        }

        private void addItem(View v) {
            if(removedItems.size() != 0) {
                int selectedItemPosition = recyclerView.getChildAdapterPosition(v);
                Log.i(TAG, "add item index: " + (selectedItemPosition + 1) + " data.size() :: " + data.size());
                int addItemAtListPosition = selectedItemPosition + 1;
                data.add(addItemAtListPosition, new DataModel(MyData._id[removedItems.get(0)],
                        MyData.nameArray[removedItems.get(0)],
                        MyData.versionArray[removedItems.get(0)],
                        MyData.drawableArray[removedItems.get(0)]
                ));
                adapter.notifyItemInserted(addItemAtListPosition);
                removedItems.remove(0);
            }else{
                Toast.makeText(v.getContext(), "Nothing more to add!", Toast.LENGTH_SHORT).show();
            }

        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData._id[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_item) {
            if (removedItems.size() != 0) {
                addRemovedItemToList();
            } else {
                Toast.makeText(this, "Nothing more to add...", Toast.LENGTH_SHORT).show();
            }
        }else  if (item.getItemId() == R.id.remove_item) {
            if (removedItems.size() >= MAX_ELEMENTS) {
                Toast.makeText(this, "Nothing more to delete...", Toast.LENGTH_SHORT).show();
            } else {
                RemoveItemFromList(data.size()-1);
            }

        }
        return true;
    }

    private void addRemovedItemToList() {
        Log.i(TAG, "data.size() :: " +  data.size());
        if(data.size() >= MAX_ELEMENTS){
            Log.i(TAG, "All elements are currently on the list!. data.size() :: " +  data.size());
            return;
        }
        int addItemAtListPosition = data.size();
        data.add(addItemAtListPosition, new DataModel(
                MyData._id[removedItems.get(0)],
                MyData.nameArray[removedItems.get(0)],
                MyData.versionArray[removedItems.get(0)],
                MyData.drawableArray[removedItems.get(0)]
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }

    private void RemoveItemFromList(int index) {
        Log.i(TAG, "data.size() :: " +  data.size());
        if(data.size() <= 0){
            Log.i(TAG, "All elements were deleted!. data.size() :: " +  data.size());
            return;
        }
        data.remove(index);
        adapter.notifyItemRemoved(index);
        removedItems.add(index);
    }


}