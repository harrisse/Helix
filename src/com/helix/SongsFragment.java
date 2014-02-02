package com.helix;

import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Genres.Members;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class SongsFragment extends ListFragment implements OnItemClickListener {
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Helix) getActivity()).setTab(4);
		
		ContentResolver resolver = getActivity().getContentResolver();
		Bundle bundle = getArguments();
		
		if (bundle == null) {
			Cursor cur = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { Media.TITLE, Media._ID }, null, null, Media.TITLE + " ASC");
			setListAdapter(new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.nav_list_item, cur, new String[] { Media.TITLE }, new int[] { android.R.id.text1 }, 0));
		} else if (bundle.containsKey("album_id")) {
			Cursor cur = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { Media.TITLE, Media._ID }, Media.ALBUM_ID + "=?", new String[] { bundle.getString("album_id") }, Media.TITLE + " ASC");
			setListAdapter(new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.nav_list_item, cur, new String[] { Media.TITLE }, new int[] { android.R.id.text1 }, 0));
		} else if (bundle.containsKey("playlist_id")) {
			Cursor cur = resolver.query(Members.getContentUri("external", bundle.getLong("playlist_id")), new String[] { Members.TITLE, Members.AUDIO_ID }, null, null, Members.TITLE + " ASC");
			setListAdapter(new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.nav_list_item, cur, new String[] { Members.TITLE }, new int[] { android.R.id.text1 }, 0));
		}
		
        getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Cursor cur = ((Cursor) getListView().getItemAtPosition(position));
		((Helix) getActivity()).add(new Song(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, cur.getLong(1)), cur.getString(0)));
	}
}
