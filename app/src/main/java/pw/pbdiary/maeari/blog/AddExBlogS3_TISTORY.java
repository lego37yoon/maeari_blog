package pw.pbdiary.maeari.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddExBlogS3_TISTORY extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexblogs3_tistory);

        intent = getIntent();
        String access_token = intent.getStringExtra("access_token");
        String apiURL = "https://www.tistory.com/apis/blog/info?access_token="+access_token+"&output=json";
        getBlogInfo thisblog = new getBlogInfo(apiURL,null);
        thisblog.execute();
    }

    public class getBlogInfo extends AsyncTask<Void,Void,String> {
        private String url;
        getBlogInfo(String url, ContentValues values) {
            this.url = url;
        }
        @Override
        protected String doInBackground(Void... voids) {
            String jsonRaw = null;
            useAPIData getThisBlog = new useAPIData();
            try {
                jsonRaw = getThisBlog.getRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonRaw;
        }
        @Override
        protected void onPostExecute(String jsonRaw) {
            JSONArray jsonData,insideTistoryData,itemData,blogs;
            String insideTistoryRaw,status,infoItem,blogemail;
            JSONObject jsonObject,insideTistoryObject,allObject,itemObject;
            try {
                jsonObject = new JSONObject(jsonRaw);
                insideTistoryObject = jsonObject.getJSONObject("tistory");
                status = insideTistoryObject.optString("status");
                itemObject = insideTistoryObject.getJSONObject("item");
                blogemail = itemObject.optString("id");
                blogs = itemObject.getJSONArray("blogs");
                ArrayList<TBlogList> blogList = new ArrayList<>();
                TextView nickname = findViewById(R.id.email_tistory);

                RecyclerView recyclerView = findViewById(R.id.blog_list_tistory);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(lm);

                ImageView imageView = findViewById(R.id.blogthumb);
                //recyclerView.setAdapter(adapter);
                if (blogs != null&&status.equals("200")) {
                    for(int m=0;m<blogs.length();m++) {
                        try {
                            JSONObject infoData = blogs.getJSONObject(m);
                            String isDefault, urlFirst,urlSecond,profileThumbImageUrl,blogTitle,blogrole, blogID;
                            urlFirst = infoData.optString("url");
                            urlSecond = infoData.optString("secondaryUrl");
                            isDefault = infoData.optString("default");
                            profileThumbImageUrl = infoData.optString("profileThumbnailImageUrl");
                            blogTitle = infoData.optString("title");
                            blogrole = infoData.optString("role");
                            blogID = infoData.optString("nickname");
                            if(isDefault.equals("Y")) {
                                //Glide.with(getApplicationContext()).load(profileThumbImageUrl).into(imageView);
                                if(!urlSecond.equals("https://")||!urlSecond.equals("")) {
                                    nickname.setText(blogID);
                                    blogList.add(new TBlogList(blogTitle+Html.fromHtml("<font color='#ffad35' size='1'>"+getResources().getString(R.string.defaultBlog)+"</font>"),blogrole,urlSecond,profileThumbImageUrl));
                                } else {
                                    nickname.setText(blogID);
                                    blogList.add(new TBlogList(blogTitle+Html.fromHtml("<font color='#ffad35' size='1'>"+getResources().getString(R.string.defaultBlog)+"</font>"),blogrole,urlFirst,profileThumbImageUrl));
                                }
                            } else {
                                if(!urlSecond.equals("https://")||!urlSecond.equals("")) {
                                    blogList.add(new TBlogList(blogTitle,blogrole,urlSecond,profileThumbImageUrl));
                                } else {
                                    blogList.add(new TBlogList(blogTitle,blogrole,urlFirst,profileThumbImageUrl));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    nickname.setText(getResources().getString(R.string.failed_to_get_from_server));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
class TBlogList {
    String bTitle;
    String bRole;
    String bURL;
    String bPTURL;
    public TBlogList(String bTitle,String bRole,String bURL,String bPTURL) {
        this.bURL = bURL;
        this.bPTURL = bPTURL;
        this.bRole = bRole;
        this.bTitle = bTitle;
    }
}
