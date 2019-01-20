package pw.pbdiary.maeari.blog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.GrantTypeValues;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

import java.io.IOException;

public class AddExBlogS2 extends AppCompatActivity {
    private Button apiTistory;
    //private Button apiEgloos;
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
        //apiEgloos = (Button) findViewById(R.id.eglooOAuthButton);
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
                        //apiEgloos.setVisibility(View.GONE);
                        apiTistory.setVisibility(View.GONE);
                        break;
                    }
                    case API_OPTION_TISTORY: {
                        apiMWBlog.setVisibility(View.GONE);
                        //apiEgloos.setVisibility(View.GONE);
                        apiTistory.setVisibility(View.VISIBLE);
                        break;
                    }
                    case API_OPTION_EGLOOS: {
                        apiMWBlog.setVisibility(View.GONE);
                        //apiEgloos.setVisibility(View.VISIBLE);
                        apiTistory.setVisibility(View.GONE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                apiMWBlog.setVisibility(View.GONE);
                //apiEgloos.setVisibility(View.GONE);
                apiTistory.setVisibility(View.GONE);
            }
        });
    }

    public void gotoMWBlogAuthForm(View view) {
        Intent goMWBlogAuth = new Intent(getApplicationContext(), MWBlogAuthForm.class);

        startActivity(goMWBlogAuth);
    }

    public void gotoTistoryOAuthWeb(View view) {
        AuthorizationServiceConfiguration tistoryServiceConfig = new AuthorizationServiceConfiguration(Uri.parse("https://www.tistory.com/oauth/authorize"),Uri.parse("https://www.tistory.com/oauth/access_token"));
        AuthorizationRequest.Builder tistoryAuthRequestBuilder = new AuthorizationRequest.Builder(tistoryServiceConfig,BuildConfig.T_APP_ID, ResponseTypeValues.CODE,Uri.parse("maeariblog://oauth"));
        tistoryAuthRequestBuilder.setState("ORIGINAL");
        AuthorizationRequest tistoryAuthRequest = tistoryAuthRequestBuilder.build();
        AuthorizationService tistoryService = new AuthorizationService(this);

        Intent authIntent = tistoryService.getAuthorizationRequestIntent(tistoryAuthRequest);
        startActivityForResult(authIntent,0);
    }
    /* public void openChooseApiDialog(View view) {
        AlertDialog.Builder egloos_builder = new AlertDialog.Builder(getActivity());
        egloos_builder .setMessage(R.string.egloosChooseApiMessage)
                .setTitle(R.string.egloosChooseApiTitle);
        AlertDialog egloosDialog = egloos_builder.create();
    } */

    /* public void gotoMetaWeBlogEgloosForm(View view) {
        Intent goEgloosMetaWeAuth = new Intent(getApplicationContext(), MWEgloosAuthForm.class);

        startActivity(goEgloosMetaWeAuth);
    } */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            Log.d("TOKEN","SUCCESS TO GET CODE FROM SERVER");
            AuthorizationResponse tistoryResponse = AuthorizationResponse.fromIntent(data);
            AuthorizationException tistoryException = AuthorizationException.fromIntent(data);
            if (tistoryResponse != null) {
                Log.d("AUTHCODE",tistoryResponse.authorizationCode);
                getAccessToken getToken = new getAccessToken("https://www.tistory.com/oauth/access_token?client_id="+BuildConfig.T_APP_ID+"&client_secret="+BuildConfig.T_S_K+"&redirect_uri=maeariblog://oauth&code="+tistoryResponse.authorizationCode+"&grant_type=authorization_code");
                getToken.execute();
            } else {
                Snackbar tistoryError = Snackbar.make(findViewById(R.id.addexblogs2),getResources().getString(R.string.failed_to_get_oauth_code),Snackbar.LENGTH_LONG);
                tistoryError.show();
                if (tistoryException != null) {
                    Log.d("CODEERROR",tistoryException.toString());
                } else {
                    Log.d("CODEERROR","UNKNOWN ERROR AT CODE");
                }
            }
            /* if (tistoryResponse != null) {
                AuthorizationService tistoryService = new AuthorizationService(this);
                ClientAuthentication tistoryAuth = new ClientSecretPost(BuildConfig.T_S_K);
                tistoryAuth.getRequestParameters(BuildConfig.T_APP_ID);
                tistoryService.performTokenRequest(tistoryResponse.createTokenExchangeRequest(),tistoryAuth,new AuthorizationService.TokenResponseCallback() {
                    @Override
                    public void onTokenRequestCompleted(@Nullable TokenResponse response, @Nullable AuthorizationException ex) {
                        if(response != null) {
                            Intent i = new Intent(getApplicationContext(),AddExBlogS3_TISTORY.class);
                            i.putExtra("access_token",response.accessToken.toString());
                            startActivity(i);
                        } else {
                            Snackbar TistoryTokenError = Snackbar.make(findViewById(R.id.addexblogs2),getResources().getString(R.string.failed_to_get_oauth_token),Snackbar.LENGTH_LONG);
                            TistoryTokenError.show();
                            if (ex != null) {
                                Log.d("TOKENERROR",ex.toString());
                            } else {
                                Log.d("TOKENERROR","UNKNOWN ERROR AT TOKEN");
                            }
                        }
                    }
                });
            } else {
                Snackbar tistoryError = Snackbar.make(findViewById(R.id.addexblogs2),getResources().getString(R.string.failed_to_get_oauth_code),Snackbar.LENGTH_LONG);
                tistoryError.show();
                if (tistoryException != null) {
                    Log.d("CODEERROR",tistoryException.toString());
                } else {
                    Log.d("CODEERROR","UNKNOWN ERROR AT CODE");
                }
            } */
        } else {
            Snackbar requestError = Snackbar.make(findViewById(R.id.addexblogs2),getResources().getString(R.string.unknown_request),Snackbar.LENGTH_LONG);
            requestError.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class getAccessToken extends AsyncTask<Void,Void,String> {
        private String url;
        public getAccessToken(String url) {
            this.url = url;
        }

        @Override
        protected void onPostExecute(String access_token) {
            Intent i = new Intent(getApplicationContext(),AddExBlogS3_TISTORY.class);
            String[] arraylist;
            arraylist = access_token.split("=");
            access_token = arraylist[1];
            i.putExtra("access_token",access_token);
            Log.d("ACCESS_TOKEN",access_token);
            startActivity(i);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String access_token;
            useAPIData getToken = new useAPIData();
            try {
                access_token = getToken.getRequest(url);
                return access_token;
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }
    }
}
