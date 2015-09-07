package com.zhengjt.cleannote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

public class CreateNote extends Activity {

    private EditText contentText;
    private EditText titleText;
    private String objectId;

    @Override
    protected void onPause() {
        super.onPause();
        // 页面统计，结束
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 页面统计，开始
        AVAnalytics.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_note);
        setTitle(R.string.create_note);

        titleText = (EditText) findViewById(R.id.note_title);
        contentText = (EditText) findViewById(R.id.note_content);

        Intent intent = getIntent();
        // 通过搜索结果打开
        if (intent.getAction() == Intent.ACTION_VIEW) {
            // 如果是VIEW action，我们通过getData获取URI
            Uri uri = intent.getData();
            String path = uri.getPath();
            int index = path.lastIndexOf("/");
            if (index > 0) {
                // 获取objectId
                objectId = path.substring(index + 1);
                GetCallback<AVObject> getCallback = new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject note, AVException arg1) {
                        if (note != null) {
                            String title = note.getString("title");
                            String content = note.getString("content");
                            if (title != null) {
                                titleText.setText(title);
                                contentText.setText(content);
                            }
                        }
                    }
                };
                AVOS.fetchNoteById(objectId, getCallback);
            }
        } else {
            // 通过ListView点击打开
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String title = extras.getString("title");
                String content = extras.getString("content");
                objectId = extras.getString("objectId");

                if (title != null) {
                    titleText.setText(title);
                    contentText.setText(content);
                }
            }
        }

        Button confirmButton = (Button) findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveCallback saveCallback = new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        // done方法一定在UI线程执行
                        if (e != null) {
                            Log.e("CreateNote", "Update note failed.", e);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("success", e == null);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                String title = titleText.getText().toString();
                String content = contentText.getText().toString();

                AVOS.createOrUpdateNote(objectId, title, content, saveCallback);
            }
        });
    }

}
