package life.xinyu.community.controller;

import life.xinyu.community.Provider.GithubProvider;
import life.xinyu.community.dto.AccessTokenDTO;
import life.xinyu.community.dto.Githubuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Value("${github.client.id}")
    private String clintID;
    @Value("${github.client.sercet}")
    private String clientSecrect;
    @Value("${github.redircent.url}")
    private String redirectUri;
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clintID);
        accessTokenDTO.setClient_secret(clientSecrect);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        Githubuser user = githubProvider.getUser(accessToken);
        System.out.println(user.getLogin());

        return "index";
    }
}
