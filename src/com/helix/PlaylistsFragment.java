package com.helix;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Playlists;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class PlaylistsFragment extends ListFragment implements OnItemClickListener {
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Helix) getActivity()).setTab(1);
		Cursor cur = getActivity().getContentResolver().query(Playlists.EXTERNAL_CONTENT_URI, new String[] { Playlists.NAME, Playlists._ID }, null, null, Playlists.NAME + " ASC");
        setListAdapter(new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.nav_list_item, cur, new String[] { Playlists.NAME }, new int[] { android.R.id.text1 }, 0));
        getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Cursor cur = ((Cursor) getListView().getItemAtPosition(position));
		PlaylistsFragment fragment = new PlaylistsFragment();
		Bundle bundle = new Bundle();
		bundle.putLong("playlist_id", cur.getLong(1));
		fragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.content_frame, new SongsFragment()).addToBackStack(null).commit();
	}
}
