package org.example.homework13;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class HttpUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static User createNewUser(URI uri, User user) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User updateUser(URI uri, int userId, User updatedUser) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(updatedUser);
        URI userUri = uri.resolve("/users/" + userId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(userUri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static boolean deleteUserById(URI uri, int userId) throws IOException, InterruptedException {
        URI userUri = uri.resolve("/users/" + userId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(userUri)
                .DELETE()
                .build();
        HttpResponse<Void> response = CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
        int statusCode = response.statusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    public static User getUserById(URI uri, int userId) throws IOException, InterruptedException {
        URI userUri = uri.resolve("/users/" + userId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(userUri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static List<User> getUsersByName(URI uri, String userName) throws IOException, InterruptedException {
        URI usersUri = uri.resolve("/users?username=" + userName);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(usersUri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(GSON.fromJson(response.body(), User[].class));
    }

    public static List<User> getAllUsers(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(GSON.fromJson(response.body(), User[].class));
    }
}
