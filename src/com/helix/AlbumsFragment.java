package com.helix;

import android.app.ListFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Artists;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class AlbumsFragment extends ListFragment implements OnItemClickListener {
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle bundle = getArguments();
		Uri uri = bundle != null && bundle.containsKey("artist_id") ? Artists.Albums.getContentUri("external", bundle.getLong("artist_id")) : Albums.EXTERNAL_CONTENT_URI;
		
		((Helix) getActivity()).setTab(3);
		Cursor cur = getActivity().getContentResolver().query(uri, new String[] { Albums.ALBUM, Albums._ID }, null, null, Albums.ALBUM + " ASC");
        setListAdapter(new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.nav_list_item, cur, new String[] { Albums.ALBUM }, new int[] { android.R.id.text1 }, 0));
        getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Cursor cur = ((Cursor) getListView().getItemAtPosition(position));
		SongsFragment fragment = new SongsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("album_id", cur.getString(1));
		fragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
	}
}
