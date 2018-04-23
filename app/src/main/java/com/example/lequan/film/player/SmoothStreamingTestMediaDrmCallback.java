package com.example.lequan.film.player;

import android.annotation.TargetApi;
import android.text.TextUtils;
import com.google.android.exoplayer.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer.drm.MediaDrmCallback;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@TargetApi(18)
public class SmoothStreamingTestMediaDrmCallback implements MediaDrmCallback {
    private static final Map<String, String> KEY_REQUEST_PROPERTIES;
    private static final String PLAYREADY_TEST_DEFAULT_URI = "http://playready.directtaps.net/pr/svc/rightsmanager.asmx";
    private static final Map<String, String> PROVISIONING_REQUEST_PROPERTIES = Collections.singletonMap("Content-Type", "application/octet-stream");

    static {
        HashMap<String, String> keyRequestProperties = new HashMap();
        keyRequestProperties.put("Content-Type", "text/xml");
        keyRequestProperties.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        KEY_REQUEST_PROPERTIES = keyRequestProperties;
    }

    public byte[] executeProvisionRequest(UUID uuid, ProvisionRequest request) throws IOException {
        return Util.executePost(request.getDefaultUrl() + "&signedRequest=" + new String(request.getData()), null, PROVISIONING_REQUEST_PROPERTIES);
    }

    public byte[] executeKeyRequest(UUID uuid, KeyRequest request) throws Exception {
        String url = request.getDefaultUrl();
        if (TextUtils.isEmpty(url)) {
            url = PLAYREADY_TEST_DEFAULT_URI;
        }
        return Util.executePost(url, request.getData(), KEY_REQUEST_PROPERTIES);
    }
}
