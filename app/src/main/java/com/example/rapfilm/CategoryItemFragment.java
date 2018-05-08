package com.example.rapfilm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rapfilm.common.JsonUtils;
import com.example.rapfilm.model.Collection;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.Type;
import com.example.rapfilm.network.FilmApi;
import com.example.rapfilm.ui.DetailActivityFilm;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tu NM on 023, 06/23.
 */

public class CategoryItemFragment extends Fragment implements OnItemClickListener, OnItemFocusListener {
    private CircularProgressBar circularProgressBar;
    private TextView title;
    private VerticalGridView verticalGridView;
    private VideoGridContentAdapter videoGridContentAdapter;
    public static String TYPE = "TYPE";
    public static String ITEM_TYPE = "ITEM_TYPE";
    private String type, item_type;
    private Disposable mSubscriptionImdb;
    private Disposable mSubscriptionTopDownload;
    private Disposable mSubscriptionTopVote;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategoryItemFragment newInstance(String type, String item_type) {
        CategoryItemFragment fragment = new CategoryItemFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        args.putString(ITEM_TYPE, item_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        type = getArguments().getString(
                TYPE);
        item_type = getArguments().getString(
                ITEM_TYPE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_item_fragment, container, false);
        circularProgressBar = view.findViewById(R.id.loading);
        title = view.findViewById(R.id.title);
        verticalGridView = view.findViewById(R.id.trv);
        verticalGridView.setNumColumns(4);
        videoGridContentAdapter = new VideoGridContentAdapter(new ArrayList<>(), this, this);
        verticalGridView.setAdapter(videoGridContentAdapter);
        getData();
        return view;
    }

    private void getData() {
        if (item_type.equals("hot")) {
            FilmApi.collection(getActivity(), type, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new getDataHot());
        } else
            switch (type) {
                case "topVote":
                    this.mSubscriptionTopVote = FilmApi.topVote(getActivity(), item_type, Type.NONE, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new getDataCategory());
                    break;
                case "topDownload":
                    this.mSubscriptionTopDownload = FilmApi.topDownload(getActivity(), item_type, Type.NONE, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new getDataCategory());
                    break;
                case "topIMDb":
                    this.mSubscriptionImdb = FilmApi.topIMDb(getActivity(), item_type, Type.NONE, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new getDataCategory());
                    break;
            }
    }

    @Override
    public void onItemFocus() {
        getData();
    }

    class getDataHot implements Consumer<JsonElement> {
        getDataHot() {
        }

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            if (JsonUtils.checkJson(jsonElement)) {
                Collection collection = (Collection) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), Collection.class);
                Debug.e(collection.getName() + collection.getContents().size());
                initView(collection.getContents());
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), DetailActivityFilm.class);
        intent.putExtra("film", videoGridContentAdapter.getItemByPos(position).getId());
        intent.putExtra("film_ojb", videoGridContentAdapter.getItemByPos(position));
        startActivity(intent);

    }

    class getDataCategory implements Consumer<JsonElement> {
        getDataCategory() {
        }
        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            parserJson(jsonElement);
        }
    }

    public void parserJson(JsonElement jsonElement) {
        if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
            JsonElement dataElement = jsonElement.getAsJsonObject().get("data");
            initView((ArrayList) new Gson().fromJson(dataElement, new C06812().getType()));
        }
    }

    private void initView(ArrayList<Film> films) {
        circularProgressBar.setVisibility(View.GONE);
        videoGridContentAdapter.addAll(films);
    }

    class C06812 extends TypeToken<List<Film>> {
        C06812() {
        }
    }

}
