# ssl-utils-android

More details in blog post: https://mklimek.github.io/trust-specific-certificate-on-jvm/

Add it in gradle:

top build.gradle:
```gradle
    allprojects {
        repositories { 
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
```

module build gradle:
```gradle
   dependencies {
        compile 'com.github.mklimek:ssl-utils-android:$RELEASE_VERSION'
   }
```

Get $RELEASE_VERSION from https://github.com/mklimek/ssl-utils-android/releases


Example usage:

Put your certificate into `assets` directory directly.

```java
OkHttpClient client = new OkHttpClient();
SSLContext sslContext = SslUtils.getSslContextForCertificateFile(context, "BPClass2RootCA-sha2.cer");
client.setSslSocketFactory(sslContext.getSocketFactory());
```
