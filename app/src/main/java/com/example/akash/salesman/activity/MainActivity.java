package com.example.akash.salesman.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akash.salesman.R;

import com.example.akash.salesman.fragment.CategoryPageFragment;
import com.example.akash.salesman.fragment.LoginPageFragment;
import com.example.akash.salesman.fragment.MainContentFragment;
import com.example.akash.salesman.fragment.BookMarksFragment;
import com.example.akash.salesman.fragment.NotificationsFragment;
import com.example.akash.salesman.fragment.ScreenSliderPagerFragment;
import com.example.akash.salesman.fragment.SettingsFragment;
import com.example.akash.salesman.other.CategoryPageItem;
import com.example.akash.salesman.other.ContentItem;
import com.example.akash.salesman.other.DBOperations;
import com.example.akash.salesman.other.FragmentInfo;
import com.example.akash.salesman.other.TabbedContentFragment;

import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements CategoryPageFragment.OnCategoryFragmentInteractionListener, MainContentFragment.OnMainContentFragmentInteractionListener, ScreenSliderPagerFragment.OnFragmentInteractionListener, TabbedContentFragment.OnFragmentInteractionListener, LoginPageFragment.OnFragmentInteractionListener, BookMarksFragment.OnBookMarksFragmentInteractionListener{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 1;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_BOOKMARK = "bookmark";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_MAIN_CONTENT_PAGE = "main_content_page";
    private static final String TAG_MAINCONTENTSCREENSLIDE = "screen_slider_page";
    public static String CURRENT_TAG = TAG_HOME;

    Stack<FragmentInfo> activeCenterFragments = new Stack<>();

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.tvNavHeaderName);
        txtWebsite = (TextView) navHeader.findViewById(R.id.tvNavHeaderWebsite);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("SalesMan App");
        txtWebsite.setText("akasgarg@cisco.com");



    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentById(navItemIndex) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                FragmentInfo fragmentInfo = new FragmentInfo();
                fragmentInfo.setFragment(fragment);
                fragmentInfo.setNavItemIndex(navItemIndex);
                fragmentInfo.setTag(CURRENT_TAG);
                activeCenterFragments.push(fragmentInfo);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                if (activeCenterFragments.size() > 0) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    for (FragmentInfo activeFragment : activeCenterFragments) {
                        fragmentTransaction.remove(activeFragment.getFragment());
                    }
                    activeCenterFragments.clear();
                    fragmentTransaction.commit();
                }

                return CategoryPageFragment.newInstance(3);

            case 1:
                // photos
                LoginPageFragment loginPageFragment = new LoginPageFragment();
                return loginPageFragment;
            case 2:
                // movies fragment
                BookMarksFragment bookMarksFragment = new BookMarksFragment();
                return bookMarksFragment;
            case 3:
                // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;

            case 4:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new CategoryPageFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_photos:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_bookmark:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_BOOKMARK;
                        break;
                    /*case R.id.nav_notifications:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;*/
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new    intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if(CURRENT_TAG.contentEquals(TAG_HOME) || activeCenterFragments.size() <= 1){

                super.onBackPressed();
            }
            else{
                Log.i("INFO", "CURRENT TAG: " + CURRENT_TAG);
                Log.i("INFO", "Nav Item Index: " + navItemIndex);
                FragmentInfo fragInfo = activeCenterFragments.pop();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragInfo.getFragment());

                final FragmentInfo fragmentInfo = activeCenterFragments.peek();

                navItemIndex = fragmentInfo.getNavItemIndex();
                CURRENT_TAG = fragmentInfo.getTag();
                setToolbarTitle();
                Runnable mPendingRunnable = new Runnable() {
                    @Override
                    public void run() {

                        Fragment fragment = fragmentInfo.getFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                        FragmentInfo fragmentInfo = new FragmentInfo();
                        fragmentInfo.setFragment(fragment);
                        fragmentInfo.setNavItemIndex(navItemIndex);
                        fragmentInfo.setTag(CURRENT_TAG);
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                };
                if (mPendingRunnable != null) {
                    mHandler.post(mPendingRunnable);
                }

                toggleFab();
                drawer.closeDrawers();
                invalidateOptionsMenu();
                return;
                //super.onBackPressed();
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    @Override
    public void OnCategoryListFragmentInteractionListener(final CategoryPageItem categoryPageItem) {

        navItemIndex = 5;
        CURRENT_TAG = TAG_MAIN_CONTENT_PAGE;
        //selectNavigationMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            toggleFab();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                Fragment fragment = MainContentFragment.newInstance(categoryPageItem);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                FragmentInfo fragmentInfo = new FragmentInfo();
                fragmentInfo.setFragment(fragment);
                fragmentInfo.setNavItemIndex(navItemIndex);
                fragmentInfo.setTag(CURRENT_TAG);
                activeCenterFragments.push(fragmentInfo);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        toggleFab();
        drawer.closeDrawers();
        invalidateOptionsMenu();


    }

    @Override
    public void OnListFragmentInteractionListener(final ContentItem item, final List<ContentItem> contentItems) {

        navItemIndex = 7;
        CURRENT_TAG = TAG_MAINCONTENTSCREENSLIDE;
        //selectNavigationMenu();
        getSupportActionBar().setTitle(item.getItem_name());

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            toggleFab();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                Fragment fragment = ScreenSliderPagerFragment.newInstance(item, contentItems);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                FragmentInfo fragmentInfo = new FragmentInfo();
                fragmentInfo.setFragment(fragment);
                fragmentInfo.setNavItemIndex(navItemIndex);
                fragmentInfo.setTag(CURRENT_TAG);
                activeCenterFragments.push(fragmentInfo);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        toggleFab();
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    @Override
    public void onMenuItemClicklistener(ContentItem item) {
        Bundle basket = new Bundle();
        basket.putString("contentToBeShared", "Item Name: " + item.getItem_name()+"\n"+ "Description: " + item.getDisplay_content() +"\n"
                        + "Owner: " + item.getOwner() + "\n" + "Contact: " + item.getContact());

        Intent a = new Intent(MainActivity.this, ShareContentActivity.class);
        a.putExtras(basket);
        startActivity(a);
    }

    @Override
    public void onBookMarkofContent(ContentItem item) {
        DBOperations dbOperations = new DBOperations(this);
        dbOperations.open();
        dbOperations.bookMarkContent(item);
        dbOperations.close();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
