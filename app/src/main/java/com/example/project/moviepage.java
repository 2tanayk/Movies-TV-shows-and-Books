package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class moviepage extends AppCompatActivity {

    String name="";
      String title=" ";
      String plot=" ";
      String image_url="";
    ImageView it;
      int c=0;
    String url="https://www.omdbapi.com/?t="+name+"&apikey=b31a4c3e";
    public void searchandresult(View viw){
       it=findViewById(R.id.imageView2);
        EditText sear=findViewById(R.id.search);
        name= sear.getText().toString();
        url="https://www.omdbapi.com/?t="+name+"&apikey=b31a4c3e";
        RequestQueue R= Volley.newRequestQueue(moviepage.this);
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    title= response.getString("Title")+"\n Ratings imdb "+response.getString("imdbRating");
                    plot="\n GENRE: "+response.getString("Genre")+"\n PLOT \n" +response.getString("Plot");
                    image_url=response.getString("Poster");

                    c=1;
                } catch (JSONException e) {
                    Toast.makeText(moviepage.this, "OOPS SOMETHING WENT WEONG ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(moviepage.this, "OOPS SOMETHING WENT WRONG TRY AGAIN", Toast.LENGTH_SHORT).show();

            }
        });
        R.add(request);

        ImageRequest I=new ImageRequest(image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                it.setImageBitmap(response);

            }
        }, 0, 0,  ImageView.ScaleType.FIT_CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue R1=Volley.newRequestQueue(moviepage.this);
        R.add(I);
        if(c==1)
           display(title,plot);


       }

 public void display(String d,String p){
        TextView t=findViewById(R.id.titlebar);
        TextView plo=findViewById(R.id.plot);
        it=findViewById(R.id.imageView2);
     Button b=findViewById(R.id.backbutton);
     Button b1=findViewById(R.id.button);
     EditText w=findViewById(R.id.search);
     it.setVisibility(View.VISIBLE);
     w.setVisibility(View.INVISIBLE);
     b1.setVisibility(View.INVISIBLE);
     b.setVisibility(View.VISIBLE);
        t.setVisibility(View.VISIBLE);
     plo.setVisibility(View.VISIBLE);
        t.setText(d);
        plo.setText(p);


 }
 public void bback(View v){
        c=0;
        name=" ";
        image_url=" ";
     TextView t=findViewById(R.id.titlebar);
     it=findViewById(R.id.imageView2);
     TextView plo=findViewById(R.id.plot);
     Button b=findViewById(R.id.backbutton);
     Button b1=findViewById(R.id.button);
     EditText w=findViewById(R.id.search);
     w.setVisibility(View.VISIBLE);
     b1.setVisibility(View.VISIBLE);
     b.setVisibility(View.INVISIBLE);
     t.setVisibility(View.INVISIBLE);
     plo.setVisibility(View.INVISIBLE);
     it.setVisibility(View.INVISIBLE);
 }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviepage);
        Intent i=getIntent();
    }
}
