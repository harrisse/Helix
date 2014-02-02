package com.helix;

import java.util.ArrayList;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.res.Configuration;

public class Helix extends Activity implements OnPreparedListener, OnCompletionListener {
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private MediaPlayer mMediaPlayer = new MediaPlayer();
	private ArrayList<Song> mPlaylist = new ArrayList<Song>();
	private int index = 0;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_helix);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.nav_list_item, getResources().getStringArray(R.array.nav_options)));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnCompletionListener(this);
	}
	
	public void onDestroy() {
		super.onDestroy();
		mMediaPlayer.release();
	}
	
	public void add(Song pair) {
		mPlaylist.add(pair);
		
		if (mPlaylist.size() == 1) {
			play(0);
		}
	}
	
	public void play(int i) {
		index = i;
		
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(getApplicationContext(), mPlaylist.get(i).uri());
			mMediaPlayer.prepareAsync();
		} catch (Exception e) {}
	}
	
	public void play() {
		mMediaPlayer.start();
	}
	
	public void pause() {
		mMediaPlayer.pause();
	}
	
	public void next() {
		index++;
		play(index);
	}
	
	public void previous() {
		if (mMediaPlayer.getCurrentPosition() > 5000) index --;
		play(index);
	}
	
	public ArrayList<Song> getNowPlaying() {
		return mPlaylist;
	}
	
	public void setTab(int tab) {
		getActionBar().setTitle(getResources().getStringArray(R.array.nav_options)[tab]);
		mDrawerList.setItemChecked(tab, true);
	}
	
	public void onPrepared(MediaPlayer player) {
		play();
	}
	
	public void onCompletion(MediaPlayer player) {
		next();
	}
	
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    mDrawerLayout.closeDrawer(mDrawerList);
	    	
	    	switch (position) {
	    		case 0:
	    			getFragmentManager().beginTransaction().replace(R.id.content_frame, new NowPlayingFragment()).addToBackStack(null).commit();
	    			return;
	    		case 1:
	    			getFragmentManager().beginTransaction().replace(R.id.content_frame, new PlaylistsFragment()).addToBackStack(null).commit();
	    			return;
	    		case 2:
	    			getFragmentManager().beginTransaction().replace(R.id.content_frame, new ArtistsFragment()).addToBackStack(null).commit();
	    			return;
	    		case 3:
	    			getFragmentManager().beginTransaction().replace(R.id.content_frame, new AlbumsFragment()).addToBackStack(null).commit();
	    			return;
	    		case 4:
	    			getFragmentManager().beginTransaction().replace(R.id.content_frame, new SongsFragment()).addToBackStack(null).commit();
	    			return;
	    	}
	    }
	}
}
