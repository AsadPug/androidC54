package com.example.projetpetitionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;


public class ConteneurFragmentsActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    ScreenSlidePagerAdapter adapter;



    int positionClic;
    Membre.Builder m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);

        adapter = new ScreenSlidePagerAdapter(this);
        // remplir le ViewPager avec un adaptateur
        viewPager.setAdapter(adapter);




        viewPager.registerOnPageChangeCallback(new CallBackPage());

        m = new Membre.Builder();

    }


    public Membre.Builder getM() {
        return m;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principla, menu);
        return true;
    }


    //à compléter
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //question 2



        return true;
    }

    // à compléter
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {

            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);

        }
    }



    public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fm) {
            super(fm);
        }



        @Override
        public Fragment createFragment(int position) {


            if ( position ==0)
            {

               return new DebutFragment();
            }
            else // position 1
            {
               return new DeuxiemeFragment();
            }
            // à compléter

        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }




    private class CallBackPage extends ViewPager2.OnPageChangeCallback
    {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            System.out.println (position);
        }
    }


}