import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.json.*;

public class script {
    public static void main(String[] args) {
        try {
            script.call_me();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void call_me() throws Exception {
        Boolean should_run = true;
        while (should_run) {
            SimpleDateFormat gmtDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            //Current Date Time in GMT
            System.out.println("Current Date and Time in GMT time zone: " + gmtDateFormat.format(new Date()));


            URL url = new URL("http://open.10000track.com/route/rest");
            Map<String, String> params = new HashMap<>();
            params.put("method", "jimi.oauth.token.get");
            params.put("timestamp", gmtDateFormat.format(new Date()));
            params.put("app_key", "8FB345B8693CCD00E24B3F5EEE161B65");
            params.put("sign", "23bfa9c9590a239d9fa25628e3149f96");
            params.put("sign_method", "md5");
            params.put("v", "0.9");
            params.put("format", "json");
            params.put("user_id", "liveasy@97");
            params.put("user_pwd_md5", "cc120882480fd847d5a092a2d9817e75");
            params.put("expires_in", "7200");

            StringBuilder postData = new StringBuilder();

            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; )
                sb.append((char) c);
            String response = sb.toString();
            System.out.println(response);

            JSONObject myResponse = new JSONObject(response.toString());
            System.out.println("code- " + myResponse.getString("code"));
            System.out.println("message- " + myResponse.getString("message"));
            JSONObject result = myResponse.getJSONObject("result");
            System.out.println(result);
            System.out.println("AccessToken- " + result.getString("accessToken"));
            String access_token = result.getString("accessToken");


            int loop_count = 0;
            while (loop_count < 100) {


                gmtDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                //Current Date Time in GMT
                System.out.println("Current Date and Time in GMT time zone: " + gmtDateFormat.format(new Date()));


                URL url_location = new URL("http://open.10000track.com/route/rest");
                params = new HashMap<>();
                params.put("method", "jimi.user.device.location.list");
                params.put("timestamp", gmtDateFormat.format(new Date()));
                params.put("app_key", "8FB345B8693CCD00E24B3F5EEE161B65");
                params.put("sign", "23bfa9c9590a239d9fa25628e3149f96");
                params.put("sign_method", "md5");
                params.put("v", "0.9");
                params.put("format", "json");
                params.put("user_id", "liveasy@97");
                params.put("user_pwd_md5", "cc120882480fd847d5a092a2d9817e75");
                params.put("expires_in", "7200");
                params.put("access_token", access_token);
                params.put("target", "liveasy@97");

                postData = new StringBuilder();

                for (Map.Entry<String, String> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                postDataBytes = postData.toString().getBytes("UTF-8");
                conn = (HttpURLConnection) url_location.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                sb = new StringBuilder();
                for (int c; (c = in.read()) >= 0; )
                    sb.append((char) c);
                response = sb.toString();
                System.out.println(response);
                myResponse = new JSONObject(response.toString());
                System.out.println("code- " + myResponse.getString("code"));
                System.out.println("message- " + myResponse.getString("message"));
                JSONArray jsonArray = myResponse.getJSONArray("result");
                System.out.println(jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    System.out.println("imei- " + json.getString("imei"));
                    System.out.println("lat- " + json.getString("lat"));
                    System.out.println("long- " + json.getString("lng"));
                    System.out.println("speed- " + json.getString("speed"));

                    URL api_url = new URL("http://");
                    Map<String, String> input_params = new HashMap<>();
                    input_params.put("imei", json.getString("imei"));
                    input_params.put("lat", json.getString("lat"));
                    input_params.put("long", json.getString("lng"));
                    input_params.put("speed", json.getString("speed"));

                }
                Thread.sleep(60 * 1000);
                loop_count++;
            }
        }
    }
}
