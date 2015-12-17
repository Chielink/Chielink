package jp.peer4.chielink.helper;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * POSTリクエスト送信 AsyncTaskLoaderクラス
 */
public class HttpPostAsyncTaskLoaderHelper extends AsyncTaskLoader<JSONObject>{

    // サーバープログラムURL
    private String url = null;
    // リクエストJSON
    private JSONObject reqJson = null;

    /**
     * コンストラクタ
     */
    public HttpPostAsyncTaskLoaderHelper(Context context, String url, JSONObject json) {
        super(context);
        this.url = url;
        this.reqJson = json;
    }

    /**
     * 非同期処理実行
     */
    @Override
    public JSONObject loadInBackground() {
        Log.d("***debug***", "loadInBackground");
        return httpPostExecute(this.url, this.reqJson);
    }

    /**
     * POSTリクエスト送信処理
     */
    public JSONObject httpPostExecute(String url, JSONObject json) {

        // レスポンスJSON
        JSONObject resJson = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");

            // リクエスト作成
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type", "application/reqJson");

            // リクエスト送信
            HttpResponse response = httpclient.execute(httpPost);

            // HTTPステータス取得
            int status = response.getStatusLine().getStatusCode();
            // リクエスト先URL＋HTTPステータス出力
            Log.d("***debug***", "Response(" + url + ") HTTP Status:" + Integer.toString(status));

            // HTTPステータスコード確認
            if(status == HttpStatus.SC_OK){
                // レスポンス受信
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                response.getEntity().writeTo(outputStream);
                resJson = new JSONObject(outputStream.toString());
                outputStream.close();

                // リクエスト先URL＋レスポンスJSON出力
                Log.d("***debug***", "Response(" + url + ") JSON:" + resJson.toString());
            }
        } catch (ClientProtocolException e) {
            Log.d("***debug***", "ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("***debug***", "IOException");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("***debug***", "JSONException");
            e.printStackTrace();
        }

        return resJson;
    }

    @Override
    protected void onStartLoading() {
        Log.d("***debug***", "onStartLoading");
        forceLoad();
    }
}
