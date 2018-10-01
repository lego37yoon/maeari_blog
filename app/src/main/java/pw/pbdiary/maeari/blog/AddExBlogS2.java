package pw.pbdiary.maeari.blog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddExBlogS2 extends AppCompatActivity {
    private Button apiTistory;
    private Button apiEgloos;
    private Button apiMWBlog;
    private boolean isCustomTabOpened = false;
    private static final int API_OPTION_METAWE = 0;
    private static final int API_OPTION_TISTORY = 1;
    private static final int API_OPTION_EGLOOS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexblogs2);

        apiTistory = (Button) findViewById(R.id.tisOAuthButton);
        apiEgloos = (Button) findViewById(R.id.eglooOAuthButton);
        apiMWBlog = (Button) findViewById(R.id.mwbAuthButton);

        final Spinner api_select_spinner = (Spinner)findViewById(R.id.api_select_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.api_value, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        api_select_spinner.setAdapter(adapter);

        api_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                switch(position) {
                    case API_OPTION_METAWE: {
                        apiMWBlog.setVisibility(View.VISIBLE);
                        apiEgloos.setVisibility(View.GONE);
                        apiTistory.setVisibility(View.GONE);
                        break;
                    }
                    case API_OPTION_TISTORY: {
                        apiMWBlog.setVisibility(View.GONE);
                        apiEgloos.setVisibility(View.GONE);
                        apiTistory.setVisibility(View.VISIBLE);
                        break;
                    }
                    case API_OPTION_EGLOOS: {
                        apiMWBlog.setVisibility(View.GONE);
                        apiEgloos.setVisibility(View.VISIBLE);
                        apiTistory.setVisibility(View.GONE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                apiMWBlog.setVisibility(View.GONE);
                apiEgloos.setVisibility(View.GONE);
                apiTistory.setVisibility(View.GONE);
            }
        });
    }

    public void gotoMWBlogAuthForm(View view) {
        Intent goMWBlogAuth = new Intent(getApplicationContext(), MWBlogAuthForm.class);

        startActivity(goMWBlogAuth);
    }

    public void gotoTistoryOAuthWeb(View view) {
        isCustomTabOpened = true;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setShowTitle(true);
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(this, Uri.parse("https://www.tistory.com/oauth/authorize?client_id=4043747cf8c75bec8f5ba21c106a8884&redirect_uri=https://latios.pbdiary.pw/tistoyOAuthToken.html&response_type=token"));
    }
    /* public void openChooseApiDialog(View view) {
        AlertDialog.Builder egloos_builder = new AlertDialog.Builder(getActivity());
        egloos_builder .setMessage(R.string.egloosChooseApiMessage)
                .setTitle(R.string.egloosChooseApiTitle);
        AlertDialog egloosDialog = egloos_builder.create();
    } */

    public void gotoMetaWeBlogEgloosForm(View view) {
        Intent goEgloosMetaWeAuth = new Intent(getApplicationContext(), MWEgloosAuthForm.class);

        startActivity(goEgloosMetaWeAuth);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCustomTabOpened) {
            isCustomTabOpened = false;
            Intent intent = new Intent(getApplicationContext(),AddExBlogS3_TISTORY.class);
            startActivity(intent);
        }
    }
}
