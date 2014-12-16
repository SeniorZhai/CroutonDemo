package app.zhaitao.com.croutondemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import de.keyboardsurfer.android.widget.crouton.Crouton;


public class MainActivity extends FragmentActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new CroutonPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onDestroy() {
        Crouton.clearCroutonsForActivity(this);
        super.onDestroy();
    }

    class CroutonPagerAdapter extends FragmentPagerAdapter {
        public CroutonPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new CroutonFragment();
            else
                return new AboutFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
