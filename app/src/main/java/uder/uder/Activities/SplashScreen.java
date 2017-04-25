package uder.uder.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import uder.uder.GiftView;
import uder.uder.R;
import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.animation.*;

import java.io.IOException;
import java.io.InputStream;


public class SplashScreen extends AppCompatActivity {

    private GiftView giftView;
    private ProgressBar progressBar;

    private final int SPLASH_DISPLAY_TIMER = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);

        GiftView gifImageView = (GiftView) findViewById(R.id.gifView);
        gifImageView.setGifImageResource(R.drawable.uder);

//        imageView= (ImageView)findViewById(gifView);
//        imageView.setVisibility(imageView.VISIBLE);
        //input resource

//        try
//        {
//         InputStream inputstream=getAssets().open("uder.gif");
//         byte[] bytes= IOUtils.toByteArray(inputstream);
//        imageView.setImageBitmap(bytes);
//      imageView.jumpDrawablesToCurrentState();
//
//        //giftView.startAnimation(R.id.gifView,);
//https://developer.android.com/guide/topics/graphics/view-animation.html
//
//     Animation udergif = AnimationUtils.loadAnimation(this,R.drawable.uder);
//            imageView.startAnimation(udergif);
//        }
//        catch
//                (IOException e)
//        {e.printStackTrace();}

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen
             */

            //http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen

            @Override
            public void run() {
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        },SPLASH_DISPLAY_TIMER);
    }
}
