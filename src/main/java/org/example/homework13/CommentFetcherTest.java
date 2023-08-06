package org.example.homework13;

import java.io.IOException;

public class CommentFetcherTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        CommentFetcher commentFetcher = new CommentFetcher();
        commentFetcher.fetchAndSaveComments();
    }
}
