package com.example.lequan.film.network;

import com.google.gson.JsonElement;
import com.squareup.okhttp.OkHttpClient;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SearchApi {
    //    private static SearchApiInterface searchApiInterface;
    //
    //    static class C06151 implements RequestInterceptor {
    //        C06151() {
    //        }
    //
    //        public void intercept(RequestFacade request) {
    //            request.addHeader("Connection", "close");
    //            request.addHeader("X-appota-key", "1-jkadnJ897H12nK127jNh-hkshydloHN19OkawslbU-cede9fe9b158b72783fd103dce08acd40dfe780c");
    //        }
    //    }
    //
    //    static class C06162 implements X509TrustManager {
    //        C06162() {
    //        }
    //
    //        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    //        }
    //
    //        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    //        }
    //
    //        public X509Certificate[] getAcceptedIssuers() {
    //            return new X509Certificate[0];
    //        }
    //    }
    //
    //    static class C06173 implements HostnameVerifier {
    //        C06173() {
    //        }
    //
    //        public boolean verify(String hostname, SSLSession session) {
    //            return true;
    //        }
    //    }
    //
    //    public interface SearchApiInterface {
    //        @GET("/apis/item.json")
    //        Observable<JsonElement> search(@Query("keyword") String str);
    //    }
    //
    //    public static SearchApiInterface getInstance(String endpoint) {
    //        if (searchApiInterface == null) {
    //            searchApiInterface = (SearchApiInterface) new Builder().setEndpoint("https://search.appota.com").setLogLevel(LogLevel.FULL).setRequestInterceptor(new C06151()).setClient(new OkClient(getUnsafeOkHttpClient())).build().create(SearchApiInterface.class);
    //        }
    //        return searchApiInterface;
    //    }
    //
    //    private static OkHttpClient getUnsafeOkHttpClient() {
    //        try {
    //            TrustManager[] trustAllCerts = new TrustManager[]{new C06162()};
    //            SSLContext sslContext = SSLContext.getInstance("SSL");
    //            sslContext.init(null, trustAllCerts, new SecureRandom());
    //            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    //            OkHttpClient client = new OkHttpClient();
    //            client.setSslSocketFactory(sslSocketFactory);
    //            client.setHostnameVerifier(new C06173());
    //            return client;
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return null;
    //        }
    //    }
}
