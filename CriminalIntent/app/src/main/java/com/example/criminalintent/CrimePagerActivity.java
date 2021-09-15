package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callbacks{
    private static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
    private ViewPager mViewPager;
    private Button mJTF, mJTL;
    private List<Crime> mCrimes;
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        mJTF = (Button) findViewById(R.id.jump_to_first_button);
        mJTL = (Button) findViewById(R.id.jump_to_last_button);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                Crime crime = mCrimes.get(i);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    mJTL.setEnabled(true);
                    mJTF.setEnabled(false);
                } else if (i == mCrimes.size() - 1) {
                    mJTL.setEnabled(false);
                    mJTF.setEnabled(true);
                } else {
                    mJTF.setEnabled(true);
                    mJTL.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        for (int i=0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        mJTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                mJTF.setEnabled(false);
            }
        });
        mJTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size()-1);
                mJTL.setEnabled(false);
            }
        });
    }

    @Override
    public void onCrimeUpdate(Crime crime) {

    }

    @Override
    public void onCrimeDelete(Crime crime) {
        finish();
    }
}
