package pw.pbdiary.maeari.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
        public getBlogInfo(String url,ContentValues values) {
            this.url = url;
        }
        @Override
        protected String doInBackground(Void... voids) {
            String jsonRaw;
            useAPIData getThisBlog = new useAPIData();
            try {
                jsonRaw = getThisBlog.getRequest(url);
                return jsonRaw;
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }
        @Override
        protected void onPostExecute(String jsonRaw) {
            JSONObject jsonData = new JSONObject();
            JSONArray status = null;
            try {
                status = (JSONArray)jsonData.get("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject infoRaw = null;
            try {
                infoRaw = (JSONObject) jsonData.get("item");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray blogmail = null;
            try {
                if (infoRaw != null) {
                    blogmail = (JSONArray) infoRaw.get("id");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray blogs = null;
            try {
                blogs = (JSONArray) infoRaw.get("blogs");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<String[]> blogList = new ArrayList<>();
            ArrayAdapter<String[]> adapter = new ArrayAdapter<>(AddExBlogS3_TISTORY.this,R.layout.listview_bloglist,blogList);

            ListView listView = (ListView) findViewById(R.id.blog_list_tistory);
            listView.setAdapter(adapter);
            for(int i=0;i<blogs.length();i++) {
                try {
                    JSONObject infoData = blogs.getJSONObject(i);
                    JSONArray isDefault = null, urlFirst=null,urlSecond=null,profileThumbImageUrl=null,blogTitle=null,blogrole=null, blogID=null;
                    try {
                        urlFirst = (JSONArray) infoData.get("url");
                        urlSecond = (JSONArray) infoData.get("secondaryUrl");
                        isDefault = (JSONArray) infoData.get("default");
                        profileThumbImageUrl = (JSONArray) infoData.get("profileThumbnailImageUrl");
                        blogTitle = (JSONArray) infoData.get("title");
                        blogrole = (JSONArray) infoData.get("role");
                        blogID = (JSONArray) infoData.get("blogId");
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(isDefault.toString().equals("Y")) {
                        if(!urlSecond.toString().equals("https://")||!urlSecond.toString().equals("")) {
                            blogList.add(new String[]{blogTitle.toString()+getResources().getString(R.string.defaultBlog),blogrole.toString(),urlSecond.toString(),profileThumbImageUrl.toString()});
                        } else {
                            blogList.add(new String[]{blogTitle.toString()+getResources().getString(R.string.defaultBlog),blogrole.toString(),urlFirst.toString(),profileThumbImageUrl.toString()});
                        }
                    } else {
                        if(!urlSecond.toString().equals("https://")||!urlSecond.toString().equals("")) {
                            blogList.add(new String[]{blogTitle.toString(),blogrole.toString(),urlSecond.toString(),profileThumbImageUrl.toString()});
                        } else {
                            blogList.add(new String[]{blogTitle.toString(),blogrole.toString(),urlFirst.toString(),profileThumbImageUrl.toString()});
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
