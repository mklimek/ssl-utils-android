# SslUtilsAndroid

More details in blog post: https://mklimek.github.io/trust-specific-certificate-on-jvm/

Add it in gradle:
```gradle
    allprojects {
        repositories { 
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
   dependencies {
        compile 'com.github.mklimek:ssl-utils-android:1.0.0'
   }
```

Example usage:
```java
OkHttpClient client = new OkHttpClient();
SSLContext sslContext = SslUtils.getSslContextForCertificateFile(app, "BPClass2RootCA-sha2.cer");
client.setSslSocketFactory(sslContext.getSocketFactory());
```
