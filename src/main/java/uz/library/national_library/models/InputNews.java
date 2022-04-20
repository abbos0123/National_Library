package uz.library.national_library.models;

public class InputNews {
    private String title ;
    private String data;

    public InputNews(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public InputNews() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
