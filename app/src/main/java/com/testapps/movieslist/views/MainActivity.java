package com.testapps.movieslist.views;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.testapps.movieslist.R;
import com.testapps.movieslist.common.BaseActivity;
import com.testapps.movieslist.di.IHasComponent;
import com.testapps.movieslist.di.components.DaggerIMainActivityComponent;
import com.testapps.movieslist.di.components.IMainActivityComponent;
import com.testapps.movieslist.di.components.MoviesListAppComponent;
import com.testapps.movieslist.di.modules.MainActivityModule;
import com.testapps.movieslist.presenters.MainActivityPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent> {

    @Inject MainActivityPresenterImpl presenter;

    @BindView(R.id.toolbar_main) Toolbar mToolBar;

    private IMainActivityComponent mainActivityComponent;
    private FragmentManager fragmentManager;

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        initializationToolbar();

        fragmentManager = getSupportFragmentManager();
        ListFragment listFragment = (ListFragment)fragmentManager.findFragmentByTag("ListFragment");
        if (listFragment == null){
            listFragment = new ListFragment();
        }
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frag_container, listFragment)
                    .commit();
        }
    }

    @Override
    protected void setupComponent(MoviesListAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .moviesListAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mainActivityComponent.inject(this);
    }

    private void initializationToolbar() {
        mToolBar.setTitleTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            presenter.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }

    // -----  IMainActivityView implement method

    @Override
    public void popFragmentFromStack() {
        fragmentManager.popBackStack();
    }
}

