import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DogApi {

    private final Gson GSON;
    private HttpClient HTTP_CLIENT;
    private HttpRequest getRequest;
    private HttpResponse<String> httpResponse;
    private final List<Data> DATA_LIST;
    private boolean badRequest;

    public DogApi() {
        GSON = new Gson();
        DATA_LIST = new ArrayList<>();
        HTTP_CLIENT = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMinutes(1))
                .build();
        try {
            getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://dog.ceo/api/breeds/image/random"))
                    .build();
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
            badRequest = true;
        }
    }

    public String getURL() {

        if (badRequest) {
            return "";
        }

        // Send HTTP Request and Save Response
        try {
            httpResponse = HTTP_CLIENT.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("JSON " + httpResponse.body());

        Data data = GSON.fromJson(httpResponse.body(), Data.class);
        System.out.println("Data " + data);

        if(data.getStatus().equals("success")) {
            DATA_LIST.add(data);
            return data.getMessage();
        } else {
            /** implement returns another URL here */
            return DATA_LIST.getFirst().getMessage();
        }
    }

    public void closeHTTPStream() {
        /**
         * HTTPClient is not shutting down correctly
         */
        HTTP_CLIENT.shutdownNow();
        HTTP_CLIENT = null;
        /**
         * Runs Garbage Collector to
         * allow the program to exit
         * Couldn't find another way :(
         */
        System.gc();
    }

    public static void main(String[] args) {
        DogApi dogApi = new DogApi();
        System.out.println("\ngetURL()\n" + dogApi.getURL());
    }

    private static class Data {

        private String message;
        private String status;

        public Data() {
        }

        @Override
        public String toString() {
            return "Data{" +
                    "message='" + message + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
