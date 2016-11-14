package com.example.hp.gavaakshi;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity{
    private WebView mWebRTCWebView;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebRTCWebView = (WebView)findViewById(R.id.webview);

        setUpWebViewDefaults(mWebRTCWebView);

        mWebRTCWebView.loadUrl("https://max-gavaakshi-fw-v2-69-srmurali002421833.codeanyapp.com/");

        mWebRTCWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "onPermissionRequest");
                request.grant(request.getResources());
            }
        });

    }
        @Override
        public void onStop() {
            super.onStop();
            /**
             * When the application falls into the background we want to stop the media stream
             * such that the camera is free to use by other apps.
             */
            mWebRTCWebView.evaluateJavascript("if(window.localStream){window.localStream.stop();}", null);
        }

        /**
         * Convenience method to set some generic defaults for a
         * given WebView
         *
         * @param webView
         */

        private void setUpWebViewDefaults(WebView webView) {
            WebSettings settings = webView.getSettings();

            // Enable Javascript
            settings.setJavaScriptEnabled(true);

            // Use WideViewport and Zoom out if there is no viewport defined
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);

            // Enable pinch to zoom without the zoom buttons
            settings.setBuiltInZoomControls(false);

            // Allow use of Local Storage
            settings.setDomStorageEnabled(true);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                // Hide the zoom controls for HONEYCOMB+
                settings.setDisplayZoomControls(false);
            }

            // Enable remote debugging via chrome://inspect
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }

            webView.setWebViewClient(new WebViewClient());

            // AppRTC requires third party cookies to work
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(mWebRTCWebView, true);
        }


}
