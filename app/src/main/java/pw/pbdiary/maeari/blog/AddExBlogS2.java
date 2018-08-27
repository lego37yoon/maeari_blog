package pw.pbdiary.maeari.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                    }
                    case API_OPTION_TISTORY: {
                        apiMWBlog.setVisibility(View.GONE);
                        apiEgloos.setVisibility(View.GONE);
                        apiTistory.setVisibility(View.VISIBLE);
                    }
                    case API_OPTION_EGLOOS: {
                        apiMWBlog.setVisibility(View.GONE);
                        apiEgloos.setVisibility(View.VISIBLE);
                        apiTistory.setVisibility(View.GONE);
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
}
