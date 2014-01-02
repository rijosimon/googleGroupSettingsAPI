import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

public class Oauth2CallbackServlet {
	static final String pocClientId = "731320868944.apps.googleusercontent.com";
	static final String pocClientSecret = "mEYMAUO8yshmyRBeQcNDDi11";
	static final String pocApiKey = "AIzaSyATszSSS3G2yTpyAQruLvbNKGkQVzHaM28";
	static final String clientId = "189588518809.apps.googleusercontent.com";
	static final String clientSecret = "spIFXXFmJPeipfVphac82ICU";
	static final String apiKey = "AIzaSyBav4pG7htO5-LaEQXnnS8-d2r5xF7QSuk";
	static final String redirectUri = "https://cmsji-test.alaska.edu/adminaudit/oauth2callback";
	static final String scope = "https://www.googleapis.com/auth/apps/reporting/audit.readonly";
	static final String applicationId = "207535951991";
    static final String pocCustomerId = "C03vt4c59";
    static final String customerId = "C0225ety9";
    static final String adminAuditUri = "https://www.googleapis.com/apps/reporting/audit/v1/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		String accessToken = null;
		String refreshToken = null;
		
	    try {
	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        		new NetHttpTransport(),
	        		new JacksonFactory(),
	        		pocClientId,
	        		pocClientSecret,
	        		Collections.singleton(scope)).setAccessType("online").build();
	        GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
	        accessToken = tokenResponse.getAccessToken();
	        refreshToken = tokenResponse.getRefreshToken();
	    } catch (TokenResponseException e) {
	        response.sendError(e.getStatusCode(), e.getStatusMessage());
	    }

        HttpTransport httpTransport = new NetHttpTransport();
        GenericUrl adminAuditUrl = new GenericUrl(adminAuditUri + pocCustomerId + "/" + applicationId);
        adminAuditUrl.put("oauth_token", accessToken);
//        adminAuditUrl.put("eventName", "TOGGLE_ENABLE_PRE_RELEASE_FEATURES");
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        HttpRequest httpRequest = requestFactory.buildGetRequest(adminAuditUrl);

        try {
            HttpResponse httpResponse = httpRequest.execute();
            InputStreamReader isr = new InputStreamReader(httpResponse.getContent());
            BufferedReader br = new BufferedReader(isr);
            PrintWriter w = response.getWriter();
            
            while (br.ready()) {
            	w.println(br.readLine());
            }
            w.flush();
            w.close();
        } catch (HttpResponseException e) {
	        response.sendError(e.getStatusCode(), e.getStatusMessage());        	
        }
	}
}
