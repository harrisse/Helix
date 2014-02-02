package com.helix;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NowPlayingFragment extends ListFragment implements OnItemClickListener {
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Helix helix = (Helix) getActivity();
		helix.setTab(0);
        setListAdapter(new ArrayAdapter<Song>(getActivity().getApplicationContext(), R.layout.nav_list_item, ((Helix) getActivity()).getPlaylist()));
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(helix.getCurrentIndex(), true);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		((Helix) getActivity()).play(position);
	}
}
