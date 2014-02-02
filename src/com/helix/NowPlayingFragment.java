package com.helix;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class NowPlayingFragment extends ListFragment implements OnItemClickListener {
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Helix) getActivity()).setTab(0);
        setListAdapter(new ArrayAdapter<Song>(getActivity().getApplicationContext(), R.layout.nav_list_item, ((Helix) getActivity()).getNowPlaying()));
        getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		((Helix) getActivity()).play(position);
	}
}
