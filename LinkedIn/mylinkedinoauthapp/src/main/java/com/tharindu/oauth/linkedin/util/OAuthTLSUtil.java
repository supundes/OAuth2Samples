package com.tharindu.oauth.linkedin.util;

import org.apache.commons.codec.binary.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;


public class OAuthTLSUtil {

    /**
     * Truststore type of the client
     */
    private static String trustStoreType = "JKS";

    /**
     * Ttrustmanager type of the client
     */
    private static String trustManagerType = "SunX509";
    /**
     * Default transport layer security protocol for client
     */
    private static String protocol = "TLSv1.2";

    private static String trustStorePath = "truststore.jks";

    private static String trustStorePassword = "wso2carbon";

    private static KeyStore trustStore;
    private static HttpsURLConnection httpsURLConnection;
    private static SSLSocketFactory sslSocketFactory;

    private static boolean isInitialized = false;

    private static void init() {
        InputStream is = null;
        try {
            trustStore = KeyStore.getInstance(trustStoreType);
            File keystoreFile = new File(OAuthTLSUtil.class.getClassLoader().getResource(trustStorePath).getFile());
            is = new FileInputStream(keystoreFile);
            trustStore.load(is, trustStorePassword.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerType);
            trustManagerFactory.init(trustStore);

            // Create and initialize SSLContext for HTTPS communication
            SSLContext sslContext = SSLContext.getInstance(protocol);


            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            sslSocketFactory = sslContext.getSocketFactory();

            isInitialized = true;


        } catch (KeyStoreException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (CertificateException e) {
            e.printStackTrace();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failed to close file. " + e.getMessage());
                }
            }
        }
    }

    public static String sendRequest(String link, Map<String, String> requestHeaders, Map<String, String> requestProps, String method) {

        if (!isInitialized) {
            init();
        }

        InputStream inputStream = null;
        BufferedReader reader = null;
        String response = null;

        try {
            URL url = new URL(link);


            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestMethod(method);

            if (requestHeaders != null) {
                for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                    httpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (requestProps != null) {
                for (Map.Entry<String, String> entry : requestProps.entrySet()) {
                    httpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            inputStream = httpsURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            response = builder.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
