package pw.pbdiary.maeari.blog;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomAppBar mAppBar = findViewById(R.id.mainnav);
        setSupportActionBar(mAppBar);
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.newArticle);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddExBlogS1.class);
                startActivity(i);
            }
        });
    }
    public void onNotiClicked(View view) {
    }

    public void onSettingsClicked(View view) {
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);
    }
}
