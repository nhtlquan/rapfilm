package com.example.rapfilm.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist.Guidance;
import android.support.v17.leanback.widget.GuidedAction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.example.rapfilm.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

public class DialogUpdateFragment extends GuidedStepFragment {
//    private static final int ACTION_ID_NEGATIVE = 2;
//    private static final int ACTION_ID_POSITIVE = 1;
//    private BroadcastReceiver broadcastReceiver = new C05531();
//    private String content = "";
//    private boolean isForce;
//    private String link = "";
//    private MaterialDialog mProgressDialog;
//
//    class C05531 extends BroadcastReceiver {
//        C05531() {
//        }
//
//        public void onReceive(Context context, Intent intent) {
//            if (intent != null && intent.getAction().equals("Login")) {
//                DialogUpdateFragment.this.getActivity().finish();
//            }
//        }
//    }
//
//    class C05542 implements Consumer<Boolean> {
//        C05542() {
//        }
//
//
//        @Override
//        public void accept(Boolean aBoolean) {
//            if (aBoolean.booleanValue()) {
//                DialogUpdateFragment.this.mProgressDialog = new Builder(DialogUpdateFragment.this.getActivity()).cancelable(false).progress(false, 100, false).show();
//                new DownloadApk().execute(new String[]{DialogUpdateFragment.this.link});
//                return;
//            }
//            Toast.makeText(DialogUpdateFragment.this.getActivity(), "Không có quyền ghi dữ liệu !", 1).show();
//        }
//    }
//
//    private class DownloadApk extends AsyncTask<String, Integer, File> {
//        private int length;
//
//        private DownloadApk() {
//        }
//
//        protected File doInBackground(String... params) {
//            try {
//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android", getFileName(params[0]));
//                if (file.exists()) {
//                    file.delete();
//                }
//                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
//                InputStream is = connection.getInputStream();
//                try {
//                    this.length = connection.getContentLength();
//                } catch (NumberFormatException e) {
//                    this.length = 8059279;
//                }
//                OutputStream os = new FileOutputStream(file, false);
//                byte[] buff = new byte[1024];
//                int progress = 0;
//                while (true) {
//                    int n = is.read(buff);
//                    if (n > 0) {
//                        os.write(buff, 0, n);
//                        progress += n;
//                        publishProgress(new Integer[]{Integer.valueOf(progress)});
//                    } else {
//                        os.flush();
//                        is.close();
//                        os.close();
//                        return file;
//                    }
//                }
//            } catch (IOException e2) {
//                e2.printStackTrace();
//                return null;
//            }
//        }
//
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            DialogUpdateFragment.this.mProgressDialog.setProgress((values[0].intValue() * 100) / this.length);
//        }
//
//        protected void onPostExecute(File file) {
//            DialogUpdateFragment.this.mProgressDialog.dismiss();
//            if (file != null) {
//                Intent intent = new Intent("android.intent.action.VIEW");
//                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                intent.setFlags(268435456);
//                DialogUpdateFragment.this.startActivity(intent);
//                if (!DialogUpdateFragment.this.isForce) {
//                    DialogUpdateFragment.this.getActivity().finish();
//                } else if (DialogUpdateFragment.this.getActivity() instanceof DialogUpdateActivity) {
//                    ((DialogUpdateActivity) DialogUpdateFragment.this.getActivity()).finisActvity();
//                } else {
//                    DialogUpdateFragment.this.getActivity().finish();
//                }
//            }
//        }
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        private String getFileName(String link) {
//            int index = link.lastIndexOf("/");
//            if (index < 0) {
//                return "aPhim.apk";
//            }
//            String name = link.substring(index + 1, link.length());
//            Log.e("name", "name - " + (name.endsWith(".apk") ? name : "aPhim.apk"));
//            if (name.endsWith(".apk")) {
//                return name;
//            }
//            return "aPhim.apk";
//        }
//    }
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.e("isForce", "isForce = " + this.isForce);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("Login");
//        intentFilter.addAction("Logout");
//        getActivity().registerReceiver(this.broadcastReceiver, intentFilter);
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    public void onDestroyView() {
//        if (!(this.broadcastReceiver == null || getActivity() == null)) {
//            getActivity().unregisterReceiver(this.broadcastReceiver);
//        }
//        super.onDestroyView();
//    }
//
//    @NonNull
//    public Guidance onCreateGuidance(Bundle savedInstanceState) {
//        return new Guidance("Cập nhật ứng dụng", Html.fromHtml(this.content).toString(), "", null);
//    }
//
//    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
//        if (getArguments() != null) {
//            this.content = getArguments().getString("content");
//            this.isForce = getArguments().getBoolean("force");
//            this.link = getArguments().getString("link");
//        }
//        actions.add(((GuidedAction.Builder) ((GuidedAction.Builder) new GuidedAction.Builder().id(1)).title(getString(R.string.update))).build());
//        Log.e("isForce", "isForce = " + this.isForce);
//        if (!this.isForce) {
//            actions.add(((GuidedAction.Builder) ((GuidedAction.Builder) new GuidedAction.Builder().id(2)).title(getString(R.string.cancel))).build());
//        }
//    }
//
//    public void onGuidedActionClicked(GuidedAction action) {
//        if (1 == action.getId()) {
//            RxPermissions.getInstance(getActivity()).request("android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE").subscribe(new C05542());
//            return;
//        }
//        getActivity().finish();
//    }
}
