package org.example.homework13;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

import static org.example.homework13.HttpUtil.getAllUsers;

public class HttpTest {
    private static final String USER_URL = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) throws IOException, InterruptedException {
        User newUser = createUser();
        User createdUser = HttpUtil.createNewUser(URI.create(USER_URL), newUser);
        System.out.println("Нового користувача додано:");
        System.out.println(createdUser);

        User updatedUser = new User();
        updatedUser.setName("Asadsad");
        updatedUser.setEmail("newemail@google.com");
        int userIdToUpdate = 1;
        User updatedResult = HttpUtil.updateUser(URI.create(USER_URL), userIdToUpdate, updatedUser);
        System.out.println("Оновлений користувач:");
        System.out.println(updatedResult);

        List<User> allUsers = getAllUsers(URI.create(USER_URL));
        System.out.println("Інформація про всіх користувачів:");
        for (User user : allUsers) {
            System.out.println(user);
        }

        int userIdToDelete = 1;
        boolean deletionSuccessful = HttpUtil.deleteUserById(URI.create(USER_URL), userIdToDelete);
        if (deletionSuccessful) {
            System.out.println("Користувача успішно видалено!");
        } else {
            System.out.println("Не вдалося видалити користувача.");
        }

        int userIdToFetch = 3;
        User fetchedUser = HttpUtil.getUserById(URI.create(USER_URL), userIdToFetch);
        System.out.println("Інформація про користувача за id: ");
        System.out.println(fetchedUser);

        String userNameToFetch = "Kamren";
        List<User> usersWithSameName = HttpUtil.getUsersByName(URI.create(USER_URL), userNameToFetch);
        System.out.println("Інформація про користувача за username: ");
        for (User user : usersWithSameName) {
            System.out.println(user);
        }
    }


    private static User createUser() {
        User user = new User();
        user.setName("Vlad");
        user.setUserName("rain");
        user.setEmail("timchenko.vld@gmail.com");
        user.setAddress(new Address("Kharkivska", "Apt. 123", "Kharkiv", "52128-3873",
                new Geo("-35.3452", "12.1357")));
        user.setPhone("1-720-766-8034 x57549");
        user.setWebsite("tymchenko.ua");
        user.setCompany(new Company("ATB", "What determines the fate of mankind in this world?",
                "make progress"));
        return user;
    }
}
