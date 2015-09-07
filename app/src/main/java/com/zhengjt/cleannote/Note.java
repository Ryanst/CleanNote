package com.zhengjt.cleannote;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName(Note.NOTE_CLASS)
public class Note extends AVObject {

    static final String NOTE_CLASS = "Note";
    private static final String TITLE_KEY = "title";
    private static final String CONTENT_KEY = "content";

    public String getTitle() {
        return this.getString(TITLE_KEY);
    }

    public void setTitle(String title) {
        this.put(TITLE_KEY, title);
    }

    public String getContent() {
        return this.getString(CONTENT_KEY);
    }

    public void setContent(String content) {
        this.put(CONTENT_KEY, content);
    }
}
