package info.diepnguyen.locationtracking;

import android.net.Uri;

/**
 * Created by admin on 11/27/17.
 */

public class Book {
    private String uid;
    private String bookName;
    private String authorName;
    private String genre;
    private String photoURL;


    public Book() {

    }

    public Book(String uid, String bookName, String authorName, String genre, String photoURL) {
        this.uid = uid;
        this.bookName = bookName;
        this.authorName = authorName;
        this.genre = genre;
        this.photoURL = photoURL;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
