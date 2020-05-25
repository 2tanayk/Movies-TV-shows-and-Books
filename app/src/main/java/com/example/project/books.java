package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class books extends AppCompatActivity
{
    TextView textViewb,displayTextViewb;
    EditText editTextb;
    ImageView displayImageViewb;
    String book;
    Button b;
    ConstraintLayout searchView,displayView;

    public class DownloadTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url=null;
            HttpURLConnection urlConnection=null;

            try {
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data=reader.read();

                while(data!=-1)
                {
                    char current=(char)data;
                    result+=current;
                    data=reader.read();

                }
                return result;


            }catch(Exception e)
            {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(),"Could not find book :(",Toast.LENGTH_SHORT).show();

                return null;
            }//catch ends
        }//dob ends

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject obj2=jsonObject.getJSONObject("book");

                String title=obj2.getString("title");
                String sub_title=obj2.getString("sub_title");
                String author=obj2.getString("author");
                String review_count=obj2.getString("review_count");
                String rating=obj2.getString("rating");
                String to_read_or_not=obj2.getString("to_read_or_not");
                String detail_link=obj2.getString("detail_link");
                String genre=obj2.getString("genre");
                String pages=obj2.getString("pages");
                String release_date=obj2.getString("release_date");

                displayTextViewb.setText("title:"+title+"\r\n"+"sub title:"+sub_title+"\r\n"+"author:"+author+"\r\n"+"reviews:"+review_count+"\r\n"+"rating:"+rating+"\r\n"+"to read or not:"+to_read_or_not+"\r\n"+"detailed link:"+detail_link+"\r\n"+"genre:"+genre+"\r\n"+"pages:"+pages+"\r\n"+"release date:"+release_date+"\r\n");

                JSONArray arr=new JSONArray(obj2.getString("critic_reviews"));

                 /*String bookInfo = jsonObject.getString("book");

                 JSONObject j=new JSONObject(bookInfo);
                 String critic=j.getString("critic_reviews");

                 Log.i("Weather content", bookInfo);


                 JSONArray arr = new JSONArray(critic);*/

                //String message = "";

                for (int i=0; i < arr.length(); i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    try {
                        String snippet = jsonPart.getString("snippet");
                        String source = jsonPart.getString("source");
                        String review_link= jsonPart.getString("review_link");
                        String pos_or_neg = jsonPart.getString("pos_or_neg");
                        String star_rating = jsonPart.getString("star_rating");
                        String review_date = jsonPart.getString("review_date");
                        String smiley_or_sad = jsonPart.getString("smiley_or_sad");
                        String source_logo = jsonPart.getString("source_logo");

                        displayTextViewb.append("snippet:"+snippet+"\r\n"+"source:"+source+"\r\n"+"review link:"+review_link+"\r\n"+"positive/negative:"+pos_or_neg+"\r\n"+"star rating:"+star_rating+"\r\n"+"review date:"+review_date+"\r\n"+"smiley/sad:"+smiley_or_sad+"\r\n"+"source:"+source_logo);

                    /* if (!main.equals("") && !description.equals("")) {
                         message += main + ": " + description + "\r\n";*/
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                /* if (!message.equals("")) {
                     resultTextView.setText(message);
                 } else {
                     Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
                 } */

            }//try ends
            catch (Exception e) {

                Toast.makeText(getApplicationContext(),"Could not find book :(",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }//catch ends

        }//ope ends

    }//class ends

    public void getBook(View view)
    {

        book=editTextb.getText().toString();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editTextb.getWindowToken(), 0);

        DownloadTask task=new DownloadTask();
        try {
            String encodedBookName=URLEncoder.encode(book,"UTF-8");

            b.setVisibility(View.INVISIBLE);
            textViewb.setVisibility(View.INVISIBLE);
            editTextb.setVisibility(View.INVISIBLE);

            displayView.setVisibility(View.VISIBLE);

            task.execute("https://idreambooks.com/api/books/reviews.json?q="+encodedBookName+"&key=e4b085b6cb2e446df89b3164508b25573cb03140");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not find book :(",Toast.LENGTH_SHORT).show();
        }

    }//getBook ends



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitybooks);
     /*   try {
            this.getSupportActionBar().hide();
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this,"Oops:(",Toast.LENGTH_SHORT).show();
        }*/

        Intent i =getIntent();

        textViewb=(TextView)findViewById(R.id.textViewb);
        editTextb=(EditText)findViewById(R.id.searchEditTextb);
        b=(Button)findViewById(R.id.button);
        searchView=findViewById(R.id.searchView);
        displayView=findViewById(R.id.displayView);

        displayImageViewb=(ImageView)findViewById(R.id.displayImageViewb);
        displayTextViewb=(TextView)findViewById(R.id.displayTextViewb);

        displayView.setVisibility(View.INVISIBLE);

    }//onCreate ends


}//class ends
