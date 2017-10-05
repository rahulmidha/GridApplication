package com.dal.user.gridapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
/*import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;*/
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {

    GridView grid, grid2;
    Activity activity;
    View[] clicked = new View[1];
    class GridAdapter extends BaseAdapter{
        Context context;
        public GridAdapter(Context c)
        {
            context = c;
        }
        @Override
        public int getCount() {
            return 81;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView newTextView;
            if(view != null)
                newTextView = (TextView) view;
            else
                newTextView = new TextView(context);

            newTextView.setText(""+(i+1));
            newTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clicked[0]!=null)
                        clicked[0].setBackgroundColor(0x00000000);
                    view.setBackgroundColor(Color.GRAY);
                    clicked[0] = view;
                }
            });
            return newTextView;
        }
    };
    private char[] getRandomCharArray()
    {
        char[] chars = new char[9];
        Random rand = new Random();

        for(int i = 0; i<9;i++)
        {
            chars[i] = (char) ('A' + rand.nextInt(9));
        }
        return chars;
    }

    class BottomGridAdapter extends BaseAdapter{
        Context context;
        public BottomGridAdapter(Context c)
        {
            context = c;
        }

        @Override
        public int getCount() {
            return 9+1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView newTextView;
            if(view != null)
                newTextView = (TextView) view;
            else
                newTextView = new TextView(context);
            if(i<9)
            {
                newTextView.setText(""+(char)('A'+i));

                newTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        view.setBackgroundColor(Color.GRAY);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.setBackgroundColor(0x00000000);
                            }
                        },200);
                        if(clicked[0]==null)
                        {
                            //show toast
                            Toast.makeText(activity, "No Number selected", Toast.LENGTH_LONG).show();
                        }
                        else {
                            TextView clickedText = (TextView) clicked[0];
                            clickedText.setText(((TextView) view).getText());
                            clickedText.setBackgroundColor(0x00000000);
                            clicked[0] = null;
                        }
                    }
                });
            }
            else
            {
                newTextView.setText("SORT");
                newTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        view.setBackgroundColor(Color.GRAY);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.setBackgroundColor(0x00000000);
                            }
                        },200);


                        for(int row = 0; row<9; row+=1){
                            char[] chars = getRandomCharArray();
                            for(int i = row*9; i < row*9+9; i+=1)
                            {
                                TextView element = (TextView)grid.getChildAt(i);
                                if(!element.getText().equals(""+(i+1)))
                                    chars[i-row*9] = element.getText().charAt(0);
                            }
                            Arrays.sort(chars);
                            for(int i = row*9; i < row*9+9; i+=1)
                            {
                                TextView element = (TextView)grid.getChildAt(i);
                                element.setText(""+chars[i-row*9]);
                            }
                        }
                    }
                });
            }


            return newTextView;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(new GridAdapter(this.getBaseContext()));
        grid2 = (GridView) findViewById(R.id.grid2);
        grid2.setAdapter(new BottomGridAdapter(this.getBaseContext()));
    }

}
