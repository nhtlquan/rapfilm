package com.example.rapfilm;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rapfilm.model.Film;
import com.example.rapfilm.ui.DetailActivityFilm;
import com.gmail.slimtbb.tvcomponents.helpers.OnAddFocusablesHelper;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * Created by Tu NM on 023, 06/23.
 */

public class HomeContentFragment extends Fragment {
    private OnAddFocusablesHelper.PositionHolder holder;
    private HorizontalGridView mTvRecyclerView;

    public HomeContentFragment() {
        holder = new OnAddFocusablesHelper.PositionHolder();
    }

    private static final String ITEMS = "items";
    private ArrayList<Film> items;
    private OnItemClickListener listener;


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeContentFragment newInstance(ArrayList<Film> items) {
        HomeContentFragment fragment = new HomeContentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ITEMS, items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        items = (ArrayList<Film>) getArguments().getSerializable(
                ITEMS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content2, container, false);
        mTvRecyclerView = view.findViewById(R.id.trv);
        mTvRecyclerView.setNumRows(1);
        mTvRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        HomeContentAdapter homeContentAdapter = new HomeContentAdapter(items, new OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailActivityFilm.class);
                intent.putExtra("film_ojb", items.get(position));
                intent.putExtra("film", items.get(position).getId());
                startActivity(intent);
            }
        });
        homeContentAdapter.setListener(new HomeContentAdapter.FindNextFocusAtLastPositionListener() {
            @Override
            public void onFocusChanged() {
                mTvRecyclerView.smoothScrollToPosition(0);
                mTvRecyclerView.requestFocus();
                final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == SCROLL_STATE_IDLE) {
                            if (mTvRecyclerView.getLayoutManager().findViewByPosition(0) != null)
                                mTvRecyclerView.getLayoutManager().findViewByPosition(0).requestFocus();
                            mTvRecyclerView.removeOnScrollListener(this);
                        }
                    }
                };
                mTvRecyclerView.addOnScrollListener(onScrollListener);
            }
        });
        mTvRecyclerView.setAdapter(homeContentAdapter);
        return view;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) outRect.left = space * 3;
            else
                outRect.left = space;
            if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1)
                outRect.right = space * 3;
            else outRect.right = space;

        }
    }


}
