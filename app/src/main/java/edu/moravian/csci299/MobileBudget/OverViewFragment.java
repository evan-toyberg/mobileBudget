package edu.moravian.csci299.MobileBudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

public class OverViewFragment extends Fragment {
    private WebView webView;
    private List<Action> Action = Collections.emptyList();


    public OverViewFragment() {
// Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.overview_fragment, container, false);
        webView = (WebView) base.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getString(R.string.url));

        return base;
    }

}
