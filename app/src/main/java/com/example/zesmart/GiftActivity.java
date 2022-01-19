package com.example.zesmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GiftActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"Zestar T-Shirt","Zestar Pink Hat", "Zestar Bllack Hat"};
    String mDescription[] = {"Zestar T-Shirt Description", "Zestar Pink Hat Description", "Zestar Black Description"};
    int images[] = {R.drawable.baju, R.drawable.hat_pink, R.drawable.hat_black};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        listView = findViewById(R.id.listview);
        //now create an adapter class

         MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
         listView.setAdapter(adapter);

         //now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(GiftActivity.this, "Zestar T-Shirt Description", Toast.LENGTH_SHORT).show();
                }
                if (position == 0) {
                    Toast.makeText(GiftActivity.this, "Zestar Pink Hat Description", Toast.LENGTH_SHORT).show();
                }
                if (position == 0) {
                    Toast.makeText(GiftActivity.this, "Zestar Black Description", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.textgift1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textgift1);
            TextView myDescription = row.findViewById(R.id.textgift2);

            //now set your resource to view2
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);


            return row;
        }
    }
}