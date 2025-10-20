package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String STATUS_CODE = "status_code";
    private static final int SUCCESS_CODE = 200;
    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {
        // return statement included so that the starter code can compile and run.
        client.newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("https://dog.ceo/api/breed/%s/list", breed))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString("status").equals("success")) {
                final JSONArray message = responseBody.getJSONArray("message");
                final List<String> subBreeds  = new ArrayList<>();
                for (int i = 0; i < message.length(); i++) {
                    subBreeds.add(message.getString(i));
                }
                return subBreeds;
            }
            else {
                throw new BreedNotFoundException("Failed to fetch breed info for: " + breed);
            }
        }
        catch (IOException | RuntimeException event) {
            throw new BreedNotFoundException("Failed to fetch breed info for: " + breed);
        }

    }
}