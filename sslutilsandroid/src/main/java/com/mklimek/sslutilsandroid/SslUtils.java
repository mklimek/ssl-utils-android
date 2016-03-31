package com.mklimek.sslutilsandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SslUtils {

    public static SSLContext getSslContextForCertificateFile(Context context, String fileName) {
        try {
            KeyStore keyStore = SslUtils.getKeyStore(context, fileName);
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            String msg = "cannot create http client with certificate from assets (which JellyBean lack)";
            Log.e("SslUtilsAndroid", msg, e);
            throw new RuntimeException(msg);
        }
    }

    private static KeyStore getKeyStore(Context context, String fileName) {
        KeyStore keyStore = null;
        try {
            AssetManager assetManager = context.getAssets();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = assetManager.open(fileName);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.d("SslUtilsAndroid", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
        } catch (Exception e) {
            Log.e("SslUtilsAndroid","Error during getting keystore", e);
        }
        return keyStore;
    }
}
