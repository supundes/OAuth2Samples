package com.tharindu.oauth.linkedin;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import com.tharindu.oauth.linkedin.util.OAuthTLSUtil;

public class OAuthCallbackListener extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final String linkedInUserInfoEndpoint = "https://api.linkedin.com/v1/people/~:(first-name,last-name,email-address,picture-url,public-profile-url,summary,industry)?format=json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Detect the presence of an authorization code
        String authorizationCode = request.getParameter("code");
        if (authorizationCode != null && authorizationCode.length() > 0) {


            // Generate POST request

            String url = LinkedInOAuthAppConstants.TOKEN_ENDPOINT
                    + "?grant_type=" + URLEncoder.encode(LinkedInOAuthAppConstants.GRANT_TYPE, StandardCharsets.UTF_8.name())
                    + "&code=" + URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8.name())
                    + "&redirect_uri=" + URLEncoder.encode(LinkedInOAuthAppConstants.REDIRECT_URI, StandardCharsets.UTF_8.name())
                    + "&client_id=" + URLEncoder.encode(LinkedInOAuthAppConstants.CLIENT_ID, StandardCharsets.UTF_8.name())
                    + "&client_secret=" + URLEncoder.encode(LinkedInOAuthAppConstants.CLIENT_SECRET, StandardCharsets.UTF_8.name());

            Map<String, String> requestProps = new HashMap<String, String>();
            requestProps.put("Content-Type", "application/x-www-form-urlencoded");

            String output = OAuthTLSUtil.sendRequest(url, null, requestProps, "POST");

            String accessToken = null;

            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(output);
                JSONObject jsonobj = (JSONObject) obj;
                accessToken = jsonobj.get("access_token").toString();

            } catch (ParseException e) {
                // Handle exception
            }

            Map<String, String> requestHeaders = new HashMap<String, String>();

            requestHeaders.put("Authorization", "Bearer " + accessToken);

            String resp = OAuthTLSUtil.sendRequest(linkedInUserInfoEndpoint, requestHeaders, requestProps, "GET");

            request.setAttribute("profile_info", resp);

            request.getRequestDispatcher("profile.jsp").forward(request, response);

        } else {
            // Handle failure
        }
    }
}
