package life.xinyu.community.Provider;

import com.alibaba.fastjson.JSON;
import life.xinyu.community.dto.AccessTokenDTO;
import life.xinyu.community.dto.Githubuser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType  = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create( mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            String[] split = string.split("&");
            return split[0].split("=")[1];
        }
        catch (IOException e){

        }
        return null;
    }
    public Githubuser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            Githubuser githubuser = JSON.parseObject(string, Githubuser.class);
            return githubuser;
        }
        catch (Exception e){}
        return null;
    }
}
