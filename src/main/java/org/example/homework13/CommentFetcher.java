package org.example.homework13;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CommentFetcher {
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/users/1/posts";
    private static final String COMMENTS_URL = "https://jsonplaceholder.typicode.com/posts/10/comments";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public void fetchAndSaveComments() throws IOException, InterruptedException {
        UserPosts[] userPosts = fetchUserPosts(URI.create(POSTS_URL));
        UserPosts lastPost = getLastPost(userPosts);
        PostComment[] postComments = fetchCommentsForPost(URI.create(COMMENTS_URL));
        saveCommentsToFile(lastPost.getUserId(), lastPost.getId(), postComments);
        System.out.println("Коментарі успішно записано у файл.");
    }

    private UserPosts[] fetchUserPosts(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), UserPosts[].class);
    }

    private UserPosts getLastPost(UserPosts[] userPosts) {
        return userPosts[userPosts.length - 1];
    }

    private PostComment[] fetchCommentsForPost(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), PostComment[].class);
    }

    private void saveCommentsToFile(int userId, int postId, PostComment[] postComments) {
        String fileName = "src/main/java/org/example/homework13/user-" + userId + "-post-" + postId + "-comments.json";
        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("File " + file.getName() + " doesn't exist");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(postComments, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
