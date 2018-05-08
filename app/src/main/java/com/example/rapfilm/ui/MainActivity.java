package com.example.rapfilm.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rapfilm.CategoryFragment;
import com.example.rapfilm.Debug;
import com.example.rapfilm.DockerAdapter;
import com.example.rapfilm.DockerItem;
import com.example.rapfilm.ForceUpdateChecker;
import com.example.rapfilm.HomeContentFragment;
import com.example.rapfilm.HotActivity;
import com.example.rapfilm.NetworkSniffTask;
import com.example.rapfilm.R;
import com.example.rapfilm.common.FilmPreferences;
import com.example.rapfilm.common.JsonUtils;
import com.example.rapfilm.common.NetworkStatusUtil;
import com.example.rapfilm.model.Category;
import com.example.rapfilm.model.Collection;
import com.example.rapfilm.model.CollectionSimple;
import com.example.rapfilm.model.Discover;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.network.FilmApi;
import com.gmail.slimtbb.tvcomponents.helpers.OnCategoryListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscription;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements OnCategoryListener, ForceUpdateChecker.OnUpdateNeededListener {
    private LinkedHashMap<Integer, HomeContentFragment> contentFragments;
    private ProgressBar loading;
    private HorizontalGridView rvDocker;
    private DockerAdapter dockerAdapter;
    private static final long HEADER_ID_7 = 7;
    private static final long HEADER_ID_9 = 9;
    private static final String HEADER_NAME_1 = "Mới nhất";
    private static final String HEADER_NAME_3 = "Phim lẻ";
    private static final String HEADER_NAME_4 = "Phim bộ";
    private static final String HEADER_NAME_5 = "TV Show";
    private static final String HEADER_NAME_6 = "Hoạt hình";
    private static final String HEADER_NAME_7 = "Thể loại";
    private static final String HEADER_NAME_8 = "Đang xem";
    private static final String HEADER_NAME_9 = "Tìm kiếm";
    private static final String HEADER_NAME_10 = "Yêu thích";
    private boolean forceUpdate;
    private String link;
    private int versionCode;
    private Disposable mSubscriptionDiscover;
    private Subscription mSubscriptionRecent;
    private ArrayList<DockerItem> dockerItems = new ArrayList<>();
    private Disposable mSubscription;
    private ArrayList<Category> categories = new ArrayList<>();
    private Disposable mSubscriptionCategory;
    private CategoryFragment categoryFragment;
    private ArrayList<Collection> collections = new ArrayList<>();
    private ArrayList<CollectionSimple> collectionSimples = new ArrayList<>();
    private int oldpos = -1;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = findViewById(R.id.loading);
        rvDocker = findViewById(R.id.rv_main_docker);
        rvDocker.setNumRows(1);
        float density = getResources().getDisplayMetrics().density;
        Debug.e("DPI " + density + "");
        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        contentFragments = new LinkedHashMap<>();
        dockerItems.add(new DockerItem(HEADER_NAME_1, R.drawable.ic_new, 1, "hot", R.drawable.ic_new_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_3, R.drawable.ic_film, 2, "movie", R.drawable.ic_film_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_4, R.drawable.ic_series, 3, "series", R.drawable.ic_series_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_5, R.drawable.ic_tvshow, 4, "tvshow", R.drawable.ic_tvshow_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_6, R.drawable.ic_funny, 5, "anime", R.drawable.ic_funny_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_7, R.drawable.ic_category, 6, "theloai", R.drawable.ic_category_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_8, R.drawable.ic_history, 7, "recent", R.drawable.ic_history_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_10, R.drawable.ic_favourite, 10, "favourite", R.drawable.ic_favourite_hover));
        dockerItems.add(new DockerItem(HEADER_NAME_9, R.drawable.ic_search, 8, "search", R.drawable.ic_search_hover));
        loadDiscover();
        loadCategory();
        for (DockerItem dockerItem : dockerItems) {
            if (dockerItem.getId() != 1 && dockerItem.getId() != 6 && dockerItem.getId() != 7 && dockerItem.getId() != 10)
                loadDataFragment(dockerItem.getItemType(), dockerItem.getId());
            if (dockerItem.getId() == 7) {
//                loadRecent();
            }
        }
        getSupportActionBar().hide();
        getIp();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void loadCategory() {
        this.mSubscriptionCategory = FilmApi.genres(this).retry().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C06833());

    }

    private void loadDataFragment(String type, int ID) {
        this.mSubscription = FilmApi.newestCategory(this, null, type, 0, 21).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new loaddata(ID));

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ấn Back 2 lần để thoát ứng dụng", Toast.LENGTH_LONG);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onCategoryInstantSelectChange(RecyclerView.ViewHolder viewHolder, int pos, boolean selected) {

    }

    @Override
    public void onUpdateNeeded(String updateUrl, String des, String appVersion) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Cập nhật phiên bản " + appVersion)
                .setMessage(Html.fromHtml(des))
                .setPositiveButton("Cập nhật",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        Debug.e(updateUrl);
        new DownloadFileAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, updateUrl);
//        new DownloadFileAsync().execute(updateUrl);
    }

    class C06833 implements Consumer<JsonElement> {

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            Log.e("jsonElemen", "jsonCategory = " + jsonElement);
            if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
                categories = (ArrayList<Category>) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), new C06833.C06821().getType());
            }
            categoryFragment = CategoryFragment.newInstance(categories);
        }

        class C06821 extends TypeToken<List<Category>> {
            C06821() {
            }
        }

        C06833() {
        }

    }

    @Override
    public void onCategoryDebounceSelectChange(RecyclerView.ViewHolder viewHolder, int pos, boolean selected) {
        if (oldpos == pos)
            return;
        switch (dockerAdapter.getCategoryByPosition(pos).getItemType()) {
            case "theloai":
                getSupportFragmentManager().beginTransaction().replace(R.id.content, categoryFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).disallowAddToBackStack().setAllowOptimization(true).commit();
                break;
            case "recent":
                getSupportFragmentManager().beginTransaction().replace(R.id.content, HomeContentFragment.newInstance(com.example.rapfilm.Utils.Utils.getHistory(this))).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).disallowAddToBackStack().setAllowOptimization(true).commit();
                break;
            case "search":
                getSupportFragmentManager().beginTransaction().replace(R.id.content, SearchFragmentHome.newInstance()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).disallowAddToBackStack().setAllowOptimization(true).commit();
                break;
            case "favourite":
                getSupportFragmentManager().beginTransaction().replace(R.id.content, HomeContentFragment.newInstance(com.example.rapfilm.Utils.Utils.getFavorite(this))).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).disallowAddToBackStack().setAllowOptimization(true).commit();
                break;
            default:
                HomeContentFragment currentFragment = contentFragments.get(dockerAdapter.getCategoryByPosition(pos).getId());
                getSupportFragmentManager().beginTransaction().replace(R.id.content, currentFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).disallowAddToBackStack().setAllowOptimization(true).commit();
        }
        oldpos = pos;
    }

    private void getIp() {
        new NetworkSniffTask(this).execute();
    }

    @Override
    public void onCategoryClick(int pos) {
        if (dockerAdapter.getCategoryByPosition(pos).getItemType().equals("hot")) {
            Intent intent = new Intent(MainActivity.this, HotActivity.class);
            intent.putExtra("collections", collectionSimples);
            intent.putExtra("title", dockerAdapter.getCategoryByPosition(pos).getTitle());
            intent.putExtra("itemType", dockerAdapter.getCategoryByPosition(pos).getItemType());
            startActivity(intent);
        } else if (dockerAdapter.getCategoryByPosition(pos).getItemType().equals("theloai")) {
            return;
        } else if (dockerAdapter.getCategoryByPosition(pos).getItemType().equals("recent") || dockerAdapter.getCategoryByPosition(pos).getItemType().equals("favourite")) {
            Intent intent = new Intent(MainActivity.this, VideoGridActivity.class);
            intent.putExtra("title", dockerAdapter.getCategoryByPosition(pos).getTitle());
            intent.putExtra("type", "local");
            intent.putExtra("itemType", dockerAdapter.getCategoryByPosition(pos).getItemType());
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, VideoGridActivity.class);
            intent.putExtra("title", dockerAdapter.getCategoryByPosition(pos).getTitle());
            intent.putExtra("type", "top_newest");
            intent.putExtra("itemType", dockerAdapter.getCategoryByPosition(pos).getItemType());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dockerAdapter != null) {
            dockerAdapter.setOnCategoryListener(this);
        }
    }

    private void loadDiscover() {
        if (NetworkStatusUtil.isNetworkAvaiable(this)) {
            this.mSubscriptionDiscover = FilmApi.discover(this).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new dataDiscover());
        }
    }

    class dataDiscover implements Consumer<JsonElement> {

        class C06481 extends TypeToken<List<Discover>> {
            C06481() {
            }
        }

        dataDiscover() {
        }

        public void call(JsonElement jsonElement) {
            if (JsonUtils.checkJson(jsonElement)) {
                Log.e("discover", "discover = " + jsonElement);
                boolean is_review = jsonElement.getAsJsonObject().get("is_review").getAsBoolean();
                Log.e("advertiser", "advertiser review = " + is_review);
                FilmPreferences.getInstance().setReview(is_review);
                createRows((ArrayList) ((List) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), new dataDiscover.C06481().getType())));
            }
        }

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            if (JsonUtils.checkJson(jsonElement)) {
                Log.e("discover", "discover = " + jsonElement);
                boolean is_review = jsonElement.getAsJsonObject().get("is_review").getAsBoolean();
                Log.e("advertiser", "advertiser review = " + is_review);
                FilmPreferences.getInstance().setReview(is_review);
                createRows((ArrayList) ((List) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), new dataDiscover.C06481().getType())));
            }
        }
    }


    private void createRows(ArrayList<Discover> discovers) {
        ArrayList<Film> films = new ArrayList<>();
        for (int i = 0; i < discovers.size(); i++) {
            if (((Discover) discovers.get(i)).getContents().size() > 0) {
                Iterator it = ((Discover) discovers.get(i)).getContents().iterator();
                while (it.hasNext()) {
                    Collection collection = (Collection) it.next();
                    if (collection.getContents() != null) {
                        if (collection.getContents().size() > 0) {
                            Debug.e(collection.getName() + collection.getId());
                            films.addAll(collection.getContents());
                        }
                    }
                    collections.add(collection);
                }
                for (Collection collection : collections)
                    if (collection.getContents() != null && collection.getContents().size() > 0)
                        collectionSimples.add(new CollectionSimple(collection.getId(), collection.getName()));
                contentFragments.put(1, HomeContentFragment.newInstance(films));
                dockerAdapter = new DockerAdapter(dockerItems, this);
                rvDocker.setAdapter(dockerAdapter);
                rvDocker.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
        }
    }


    class loaddata implements Consumer<JsonElement> {
        private int ID;

        loaddata(int ID) {
            this.ID = ID;
        }

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            parserJson(jsonElement, ID);
        }
    }

    public void parserJson(JsonElement jsonElement, int ID) {
        if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
            JsonElement dataElement = jsonElement.getAsJsonObject().get("data");
            Debug.e("dataElement = " + dataElement.toString());
            ArrayList<Film> temp = (ArrayList) new Gson().fromJson(dataElement, new Token().getType());
            contentFragments.put(ID, HomeContentFragment.newInstance(temp));
        }
    }

    class Token extends TypeToken<List<Film>> {
        Token() {
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Cập nhật..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        File file = null;

        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
            Debug.e("Fuck thang");
        }

        protected String doInBackground(String... aurl) {
            int count;
            Debug.e("Fuck phet");
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                Debug.e(url.getHost());
                File savedDir = new File("/sdcard/" + getPackageName());
                if (savedDir.exists() == false) {
                    savedDir.mkdir();
                }
                String disposition = conexion.getHeaderField("Content-Disposition");
                String fileName = null;
                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length() - 1);
                    }
                }
                if (fileName == null)
                    file = new File(savedDir, System.currentTimeMillis() + ".apk");
                else file = new File(savedDir, fileName);
                if (file.exists())
                    file.delete();
                file.createNewFile();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Debug.e(e.getMessage());
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            Debug.e(String.valueOf(file.length()));
            installApkAsUserLevel(file);
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }

    private void installApkAsUserLevel(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

