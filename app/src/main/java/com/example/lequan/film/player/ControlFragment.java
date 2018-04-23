package com.example.lequan.film.player;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.PlaybackOverlayFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ControlButtonPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.PlaybackControlsRow.FastForwardAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.HighQualityAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.PlayPauseAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.RewindAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.SkipNextAction;
import android.support.v17.leanback.widget.PlaybackControlsRow.SkipPreviousAction;
import android.support.v17.leanback.widget.PlaybackControlsRowPresenter;
import android.support.v17.leanback.widget.Presenter.ViewHolder;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.util.Log;
import android.widget.Toast;
import com.example.lequan.film.Utils.CardListRow;
import com.example.lequan.film.Utils.EpisodeListRow;
import com.example.lequan.film.callback.OnStatePlayBack;
import com.example.lequan.film.custom.StringPresenter;
import com.example.lequan.film.custom.TextCardPresenter;
import com.example.lequan.film.model.Episode;
import com.example.lequan.film.model.EpisodeDetail;
import com.example.lequan.film.model.Film;
import com.example.lequan.film.model.Season;
import com.example.lequan.film.ui.DetailActivityFilm;
import com.example.lequan.film.ui.presenter.DetailsDescriptionPresenter;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.example.lequan.film.R;
import java.util.ArrayList;
import java.util.Iterator;

public class ControlFragment extends PlaybackOverlayFragment implements OnItemViewClickedListener, OnStatePlayBack {
    private int ACTION_FAST = 3;
    private int ACTION_NEXT = 2;
    private int ACTION_PLAY = 1;
    private int ACTION_PREV = 0;
    private int ACTION_REWIN = -1;
    private Action action;
    private int currentPlayseason = 0;
    private String episodeId;
    private HighQualityAction highQualityAction;
    private PlaybackControlsRow mControlsRow;
    private EpisodeDetail mEpisodeDetail;
    private FastForwardAction mFastForwardAction;
    private String mFilmName;
    private String mFilmNameVi;
    private PlayPauseAction mPlayPauseAction;
    private SparseArrayObjectAdapter mPrimaryActionsAdapter;
    private RewindAction mRewindAction;
    private ArrayObjectAdapter mRowsAdapter;
    private SkipNextAction mSkipNextAction;
    private SkipPreviousAction mSkipPreviousAction;
    private PlaybackControlsRowPresenter playbackControlsRowPresenter;
    private Fragment playerFragment;
    private ClassPresenterSelector ps;
    private ArrayList<Season> seasons = new ArrayList();
    private ArrayList<Film> suggess = new ArrayList();

    class C06181 extends ButtonCallback {
        C06181() {
        }

        public void onPositive(MaterialDialog dialog) {
            super.onPositive(dialog);
            dialog.dismiss();
        }

        public void onNegative(MaterialDialog dialog) {
            super.onNegative(dialog);
            dialog.dismiss();
        }
    }

    private void showDialog() {
        new Builder(getActivity()).title((CharSequence) "ahihi").content((CharSequence) "bla bla").positiveText((CharSequence) "chap nhan").negativeText((CharSequence) "thoat").callback(new C06181()).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.mEpisodeDetail = (EpisodeDetail) args.getParcelable("mCurrentEpisode");
            this.mFilmName = args.getString("mFilmName");
            this.mFilmNameVi = args.getString("mFilmName_vi");
            this.seasons = (ArrayList<Season>) args.getSerializable("seasons");
            this.suggess = (ArrayList<Film>) args.getSerializable("suggess");
            this.currentPlayseason = args.getInt("currentplayseason");
            this.episodeId = args.getString("episodeID");
        }
        this.mControlsRow = new PlaybackControlsRow(new String(this.mFilmNameVi + " - " + this.mFilmName));
        this.highQualityAction = new HighQualityAction(getActivity());
        this.mSkipNextAction = new SkipNextAction(getActivity());
        this.mSkipPreviousAction = new SkipPreviousAction(getActivity());
        this.mPlayPauseAction = new PlayPauseAction(getActivity());
        this.mFastForwardAction = new FastForwardAction(getActivity());
        this.mRewindAction = new RewindAction(getActivity());
        this.playerFragment = getFragmentManager().findFragmentByTag("tag");
        Log.e("playerFragment", "playerFragment" + this.playerFragment);
        if (this.playerFragment != null) {
            ((PlayerFragment) this.playerFragment).setOnStatePlayBack(this);
        }
        this.ps = new ClassPresenterSelector();
        this.playbackControlsRowPresenter = createControlsRowAndPresenter();
        setupControlsRowPresenter(this.playbackControlsRowPresenter);
        this.ps.addClassPresenter(PlaybackControlsRow.class, this.playbackControlsRowPresenter);
        this.ps.addClassPresenter(ListRow.class, new ListRowPresenter());
        this.mRowsAdapter = new ArrayObjectAdapter(this.ps);
        addPlaybackControlsRow();
        addEpisodeRow();
        addSuggessRow();
    }

    private void addEpisodeRow() {
        if (this.seasons.size() <= 0 || this.seasons.size() != 1 || ((Season) this.seasons.get(0)).getContents().size() != 1) {
            for (int i = 0; i < this.seasons.size(); i++) {
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new StringPresenter(getActivity()));
                for (Episode episode : ((Season) this.seasons.get(i)).getContents()) {
                    listRowAdapter.add(episode);
                }
                this.mRowsAdapter.add(new EpisodeListRow(new HeaderItem((long) i, ((Season) this.seasons.get(i)).getName()), listRowAdapter, (ArrayList) ((Season) this.seasons.get(i)).getContents()));
            }
        }
    }

    private void addSuggessRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new TextCardPresenter(getActivity()));
        Iterator it = this.suggess.iterator();
        while (it.hasNext()) {
            listRowAdapter.add((Film) it.next());
        }
        this.mRowsAdapter.add(new CardListRow(new HeaderItem(0, "Phim tương tự"), listRowAdapter, this.suggess));
    }

    public PlaybackControlsRow getControlsRow() {
        return this.mControlsRow;
    }

    private void addPlaybackControlsRow() {
        this.mRowsAdapter.add(getControlsRow());
        setAdapter(this.mRowsAdapter);
        setOnItemViewClickedListener(this);
    }

    public PlaybackControlsRowPresenter createControlsRowAndPresenter() {
        PlaybackControlsRowPresenter presenter = new PlaybackControlsRowPresenter(new DetailsDescriptionPresenter());
        this.mControlsRow = getControlsRow();
        this.mPrimaryActionsAdapter = new SparseArrayObjectAdapter(new ControlButtonPresenterSelector());
        if (this.seasons.size() > 0 && this.seasons.get(0) != null && ((Season) this.seasons.get(0)).getContents().size() > 1) {
            this.mPrimaryActionsAdapter.set(this.ACTION_PREV, this.mSkipPreviousAction);
            this.mPrimaryActionsAdapter.set(this.ACTION_NEXT, this.mSkipNextAction);
        }
        this.mPrimaryActionsAdapter.set(this.ACTION_PLAY, this.mPlayPauseAction);
        this.mPrimaryActionsAdapter.set(this.ACTION_REWIN, this.mRewindAction);
        this.mPrimaryActionsAdapter.set(this.ACTION_FAST, this.mFastForwardAction);
        this.mControlsRow.setPrimaryActionsAdapter(this.mPrimaryActionsAdapter);
        this.mPlayPauseAction.setIndex(1);
        addSecondaryActions(new ArrayObjectAdapter(new ControlButtonPresenterSelector()));
        setupControlsRowPresenter(presenter);
        return presenter;
    }

    public void setupControlsRowPresenter(PlaybackControlsRowPresenter presenter) {
        presenter.setProgressColor(getActivity().getResources().getColor(R.color.player_progress_color));
        presenter.setBackgroundColor(getActivity().getResources().getColor(R.color.player_background_color));
    }

    protected void addSecondaryActions(ArrayObjectAdapter secondaryActionsAdapter) {
        if (this.mEpisodeDetail.getDownload().size() > 1) {
            secondaryActionsAdapter.add(this.highQualityAction);
        }
        if (this.seasons.size() <= 0 || ((Season) this.seasons.get(0)).getContents().size() > 0) {
            this.mControlsRow.setSecondaryActionsAdapter(secondaryActionsAdapter);
        } else {
            this.mControlsRow.setSecondaryActionsAdapter(secondaryActionsAdapter);
        }
    }

    public void onItemClicked(ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof Action) {
            this.action = (Action) item;
            if (this.action == this.mPlayPauseAction) {
                if (this.playerFragment != null) {
                    this.mPlayPauseAction.nextIndex();
                    notifyChanged(this.mPlayPauseAction);
                    ((PlayerFragment) this.playerFragment).playOrPause(0);
                }
            } else if (this.action == this.mRewindAction) {
                ((PlayerFragment) this.playerFragment).rewind15s();
            } else if (this.action == this.mFastForwardAction) {
                ((PlayerFragment) this.playerFragment).fast15s();
            } else if (this.action == this.mSkipNextAction) {
               int posE = getCurrentNextPosEpisode();
                if (posE != 1000000) {
                    Episode   episode = (Episode) ((Season) this.seasons.get(this.currentPlayseason)).getContents().get(posE);
                    if (episode != null) {
                        this.episodeId = episode.getId();
                        ((PlayerFragment) this.playerFragment).setFromRecent();
                        ((PlayerFragment) this.playerFragment).getLinkPlay(this.episodeId, this.currentPlayseason);
                        return;
                    }
                    return;
                }
                Toast.makeText(getActivity(), R.string.toast_cant_next, Toast.LENGTH_LONG).show();
            } else if (this.action == this.mSkipPreviousAction) {
               int posE = getCurrentPrevPosEpisode();
                if (posE != 1000000) {
                    Log.e("posE", "posE next= " + posE + " / " + this.currentPlayseason);
                    Episode  episode = (Episode) ((Season) this.seasons.get(this.currentPlayseason)).getContents().get(posE);
                    if (episode != null) {
                        this.episodeId = episode.getId();
                        ((PlayerFragment) this.playerFragment).setFromRecent();
                        ((PlayerFragment) this.playerFragment).getLinkPlay(this.episodeId, this.currentPlayseason);
                        return;
                    }
                    return;
                }
                Toast.makeText(getActivity(), R.string.toast_cant_previous, Toast.LENGTH_LONG).show();
            }
        } else if (item instanceof Episode) {
            this.currentPlayseason = (int) row.getHeaderItem().getId();
            Log.e("currentPlaySeason", "currentSeason = " + this.currentPlayseason);
            this.episodeId = ((Episode) item).getId();
            ((PlayerFragment) this.playerFragment).setFromRecent();
            ((PlayerFragment) this.playerFragment).getLinkPlay(this.episodeId, this.currentPlayseason);
        } else if (item instanceof Film) {
            Intent intent = new Intent(getActivity(), DetailActivityFilm.class);
            intent.putExtra("film", ((Film) item).getId());
            intent.putExtra("film_ojb", ((Film) item));
            startActivity(intent);
            getActivity().finish();
        }
    }

    public int getCurrentNextPosEpisode() {
        int pos = 0;
        if (this.currentPlayseason < 0) {
            return 1000000;
        }
        if (this.seasons.size() <= this.currentPlayseason || this.seasons.get(this.currentPlayseason) == null) {
            return 0;
        }
        ArrayList<Episode> episodes = (ArrayList) ((Season) this.seasons.get(this.currentPlayseason)).getContents();
        for (int i = 0; i < episodes.size(); i++) {
            Log.e("episode", "episode id = " + ((Episode) episodes.get(i)).getId() + " -- " + this.episodeId);
            if (((Episode) episodes.get(i)).getId().equals(this.episodeId)) {
                pos = i + 1;
            }
        }
        Log.e("episode", "episode size = " + episodes.size());
        Log.e("episode", "episode size pos = " + pos);
        if (pos < episodes.size()) {
            return pos;
        }
        this.currentPlayseason++;
        if (this.seasons.size() > this.currentPlayseason) {
            return 0;
        }
        this.currentPlayseason--;
        return 1000000;
    }

    public int getCurrentPrevPosEpisode() {
        int pos = 0;
        if (this.currentPlayseason < 0) {
            return 1000000;
        }
        if (this.seasons.get(this.currentPlayseason) == null) {
            return 0;
        }
        ArrayList<Episode> episodes = (ArrayList) ((Season) this.seasons.get(this.currentPlayseason)).getContents();
        for (int i = 0; i < ((Season) this.seasons.get(this.currentPlayseason)).getContents().size(); i++) {
            if (((Episode) episodes.get(i)).getId().equals(this.episodeId)) {
                pos = i - 1;
            }
        }
        if (pos >= 0) {
            return pos;
        }
        this.currentPlayseason--;
        Log.e("currentSeason", "currentSeason = " + this.currentPlayseason);
        if (this.currentPlayseason >= 0) {
            return ((Season) this.seasons.get(this.currentPlayseason)).getContents().size() - 1;
        }
        this.currentPlayseason++;
        return 1000000;
    }

    private void notifyChanged(Action action) {
        if (this.mPrimaryActionsAdapter.indexOf((Object) action) >= 0) {
            this.mPrimaryActionsAdapter.notifyArrayItemRangeChanged(this.mPrimaryActionsAdapter.indexOf((Object) action), 1);
        }
    }

    public void onStatePlayBack(int action, boolean isPlay) {
    }

    public void onProgressChange(long currentTime, long totalTime, int percent) {
        if (this.mControlsRow != null) {
            this.mControlsRow.setCurrentTime((int) currentTime);
            this.mControlsRow.setTotalTime((int) totalTime);
            this.mControlsRow.setBufferedProgress((int) (((long) percent) * totalTime));
        }
    }
}
