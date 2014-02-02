package com.helix;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Artists;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class ArtistsFragment extends ListFragment implements OnItemClickListener {
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Helix) getActivity()).setTab(2);
		getActivity().getActionBar().setTitle(getResources().getStringArray(R.array.nav_options)[2]);
		Cursor cur = getActivity().getContentResolver().query(Artists.EXTERNAL_CONTENT_URI, new String[] { Artists.ARTIST, Artists._ID }, null, null, Artists.ARTIST + " ASC");
        setListAdapter(new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.nav_list_item, cur, new String[] { Artists.ARTIST }, new int[] { android.R.id.text1 }, 0));
        getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Cursor cur = ((Cursor) getListView().getItemAtPosition(position));
		AlbumsFragment fragment = new AlbumsFragment();
		Bundle bundle = new Bundle();
		bundle.putLong("artist_id", cur.getLong(1));
		fragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
	}
}
