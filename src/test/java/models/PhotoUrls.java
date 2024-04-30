package models;


public class PhotoUrls {
    String stringSet;

    public PhotoUrls(String string) {
        this.stringSet = string;
    }

    public String getStringSet() {
        return stringSet;
    }

    public void setStringSet(String stringSet) {
        this.stringSet = stringSet;
    }

    @Override
    public String toString() {
        return "[\"" + stringSet + "\"]";
    }
}
