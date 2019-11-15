package com.example.user.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    static String bookTitle, bookAuthor, bookURL, bookThumbnail, bookDescription, bookPublishedDate, bookCategories;
    static double bookRating;
    static int bookRatingReviews, bookPageCount;

    private QueryUtils() {
    }

    public static List<Book> fetchBookData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Book> book = extractbooks(jsonResponse);

        return book;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractbooks(String bookJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(bookJSON);
            JSONArray booksArray = root.getJSONArray("items");
            for (int i=0; i<booksArray.length(); i++){
                JSONObject currentObject = booksArray.getJSONObject(i);
                JSONObject info = currentObject.getJSONObject("volumeInfo");
                bookTitle = info.getString("title");
                if(info.has("averageRating") && info.has("description")){
                    bookDescription = info.getString("description");
                    bookRating = info.getDouble("averageRating");
                    bookURL = info.getString("infoLink");
                    bookRatingReviews = info.getInt("ratingsCount");
                    bookPageCount = info.getInt("pageCount");
                    if(info.has("categories")){
                        JSONArray categories = info.getJSONArray("categories");
                        bookCategories = categories.getString(0);
                    }else
                        bookCategories = "";
                    JSONObject imageLinks = info.getJSONObject("imageLinks");
                    bookThumbnail = imageLinks.getString("thumbnail");
                    bookPublishedDate = info.getString("publishedDate");
                    if(info.has("authors")){
                        JSONArray authors = info.getJSONArray("authors");
                        bookAuthor = authors.getString(0);
                    }else
                        bookAuthor = info.getString("publisher");

                    books.add(new Book(bookTitle,bookAuthor,bookDescription,bookThumbnail,bookURL,bookCategories, bookPublishedDate,bookRating,bookRatingReviews,bookPageCount));
                }

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        return books;
    }

}
