package com.example.lequan.film.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.SearchFragment.SearchResultProvider;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter.ViewHolder;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SearchBar;
import android.support.v17.leanback.widget.SearchEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.lequan.film.Debug;
import com.example.lequan.film.common.ConvertCharacter;
import com.example.lequan.film.common.FilmPreferences;
import com.example.lequan.film.common.JsonUtils;
import com.example.lequan.film.custom.TextCardPresenter;
import com.example.lequan.film.model.Film;
import com.example.lequan.film.R;
import com.example.lequan.film.model.Type;
import com.example.lequan.film.network.FilmApi;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements SearchResultProvider {
    private static final int REQUEST_SPEECH = 16;
    private static final long SEARCH_DELAY_MS = 2000;
    private static final String TAG = SearchFragment.class.getSimpleName();
    private final Runnable mDelayedLoad = new C06931();
    private final Handler mHandler = new Handler();
    private ArrayList<Film> mItems = new ArrayList();
    private String mQuery;
    private ArrayObjectAdapter mRowsAdapter;
    private Disposable mSubscription;

    class C06931 implements Runnable {
        C06931() {
        }

        public void run() {
            if (!FilmPreferences.getInstance().isReview()) {
                SearchFragment.this.loadRows();
            }
        }
    }

    class C06973 extends AsyncTask<String, Void, ListRow> {
        private final String query = SearchFragment.this.mQuery;

        class C06962 implements Consumer<Throwable> {
            C06962() {
            }


            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }

        C06973() {
        }

        protected void onPreExecute() {
            SearchFragment.this.mRowsAdapter.clear();
        }

        protected ListRow doInBackground(String... params) {
            List<Film> result = new ArrayList();
            final ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new TextCardPresenter(SearchFragment.this.getActivity()));
            SearchFragment.this.mSubscription = FilmApi.search(SearchFragment.this.getActivity(), ConvertCharacter.convertToEnglish(SearchFragment.this.mQuery), "", Type.NONE, SearchFragment.this.mRowsAdapter.size(), 18).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonElement>() {
                @Override
                public void accept(JsonElement jsonElement) throws Exception {
                    Debug.e("success" + jsonElement.toString());
                    if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
                        listRowAdapter.addAll(0, JsonUtils.parserSearch(jsonElement));
                    }
                }
            }, new C06962());
            return new ListRow(new HeaderItem("Kết quả: "), listRowAdapter);
        }

        protected void onPostExecute(ListRow listRow) {
            SearchFragment.this.mRowsAdapter.add(listRow);
        }
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        private ItemViewClickedListener() {
        }

        public void onItemClicked(ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Film) {
//                Film movie = (Film) item;
//                Log.d(SearchFragment.TAG, "Movie: " + movie.toString());
//                Intent intent = new Intent(SearchFragment.this.getActivity(), DetailsActivity.class);
//                intent.putExtra("film", movie.getId());
//                SearchFragment.this.startActivity(intent);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setSearchResultProvider(this);
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View v = getView().findViewById(R.id.lb_search_bar);
        if (v instanceof SearchBar) {
            ((SearchBar) v).findViewById(R.id.lb_search_bar_speech_orb).setVisibility(View.GONE);
            ((SearchEditText) v.findViewById(R.id.lb_search_text_editor)).setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i != 3) {
                        return false;
                    }
                    if (!FilmPreferences.getInstance().isReview()) {
                        SearchFragment.this.mQuery = ((SearchEditText) v.findViewById(R.id.lb_search_text_editor)).getText().toString();
                        Toast.makeText(SearchFragment.this.getActivity(), SearchFragment.this.mQuery, Toast.LENGTH_LONG).show();
                        SearchFragment.this.loadRows();
                    }
                    return true;
                }
            });
        }
    }

    public boolean hasResults() {
        return this.mRowsAdapter.size() > 0;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "onActivityResult requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data);
        switch (requestCode) {
            case 16:
                switch (resultCode) {
                    case -1:
                        setSearchQuery(data, true);
                        return;
                    case 2:
                        Log.w(TAG, Integer.toString(requestCode));
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    public ObjectAdapter getResultsAdapter() {
        Log.d(TAG, "getResultsAdapter");
        return this.mRowsAdapter;
    }


    public boolean onQueryTextChange(String newQuery) {
        Log.i(TAG, String.format("Search Query Text Change %s", new Object[]{newQuery}));
        loadQueryWithDelay(newQuery, SEARCH_DELAY_MS);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Log.i(TAG, String.format("Search Query Text Submit %s", new Object[]{query}));
        this.mQuery = query;
        loadQueryWithDelay(query, 0);
        return true;
    }

    private void loadQueryWithDelay(String query, long delay) {
        this.mHandler.removeCallbacks(this.mDelayedLoad);
        if (!TextUtils.isEmpty(query) && !query.equals("nil")) {
            this.mQuery = query;
            this.mHandler.postDelayed(this.mDelayedLoad, delay);
        }
    }

    private void loadRows() {
        new C06973().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }
}
