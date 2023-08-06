package org.example.homework13;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ToDosFinder {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public void displayOpenTodos (int userId) throws IOException, InterruptedException {
        ToDos[] toDos = fetchTasksForUser(1);
        System.out.println("Відкриті задачі для користувача " + userId + ":");
        for (ToDos elements : toDos){
            System.out.println(elements.toString());
        }
    }

    private ToDos[] fetchTasksForUser(int userId) throws IOException, InterruptedException {
        final String url = "https://jsonplaceholder.typicode.com/users/" + userId + "/todos";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        ToDos[] toDos = GSON.fromJson(response.body(), ToDos[].class);
        return filterOpenTodos(toDos);
    }
    private ToDos[] filterOpenTodos(ToDos[] todos) {
        List<ToDos> openTodos = new ArrayList<>();
        for (ToDos todo : todos) {
            if (!todo.isCompleted()) {
                openTodos.add(todo);
            }
        }
        return openTodos.toArray(new ToDos[0]);
    }
}
