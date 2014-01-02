import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessTokenRequest.GoogleAuthorizationCodeGrant;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAuthorizationRequestUrl;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.api.services.groups-settings.v1.Groupssettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigAPI {

}

public void setUp() throws IOException {
    HttpTransport httpTransport = new NetHttpTransport();
    JacksonFactory jsonFactory = new JacksonFactory();

    // The clientId and clientSecret are copied from the API Access tab on
    // the Google APIs Console
    String clientId = "YOUR_CLIENT_ID";
    String clientSecret = "YOUR_CLIENT_SECRET";

    // Or your redirect URL for web based applications.
    String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
    String scope = "https://www.googleapis.com/auth/groups-settings";

    // Step 1: Authorize -->
    String authorizationUrl = new GoogleAuthorizationRequestUrl(clientId, redirectUrl, scope)
        .build();

    // Point or redirect your user to the authorizationUrl.
    System.out.println("Go to the following link in your browser:");
    System.out.println(authorizationUrl);

    // Read the authorization code from the standard input stream.
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("What is the authorization code?");
    String code = in.readLine();
    // End of Step 1 <--

    // Step 2: Exchange -->
    AccessTokenResponse response = new GoogleAuthorizationCodeGrant(httpTransport, jsonFactory,
        clientId, clientSecret, code, redirectUrl).execute();
    // End of Step 2 <--

    GoogleAccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(
        response.accessToken, httpTransport, jsonFactory, clientId, clientSecret,
        response.refreshToken);

    Groupssettings service = new Groupssettings(httpTransport, accessProtectedResource, jsonFactory);
    service.setApplicationName("YOUR_APPLICATION_NAME");
  }
