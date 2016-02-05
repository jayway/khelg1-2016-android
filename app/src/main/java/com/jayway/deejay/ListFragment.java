package com.jayway.deejay;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(((MainActivity) getActivity()).getAlbumData(R.layout.list_item));

        return rootView;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (!(context instanceof MainActivity)) {
            throw new RuntimeException("Fragment have to be attached to a MainActivity");
        }
    }
}
