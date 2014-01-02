import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

public class AuthServlet {

	static final String pocClientId = "237133784945.apps.googleusercontent.com";
	static final String pocClientSecret = "kWV4N-ktnU7pKEoBr01OLuqv";
	static final String clientId = "189588518809.apps.googleusercontent.com";
	static final String clientSecret = "spIFXXFmJPeipfVphac82ICU";
	static final String redirectUri = "https://cmsji-test.alaska.edu/adminaudit/oauth2callback";
	static final String scope = "https://www.googleapis.com/groups/v1/groups/";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),new JacksonFactory(), pocClientId, pocClientSecret, Collections.singleton(scope)).setAccessType("online").build();
        response.sendRedirect(flow.newAuthorizationUrl().setRedirectUri(redirectUri).build());
        return;
	}
	
}
