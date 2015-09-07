package com.zhengjt.cleannote;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.search.AVSearchQuery;

import java.util.Collections;
import java.util.List;

/**
 * Created by lzw on 14-9-12.
 */
public class AVOS {
    public static void AVInit(Context ctx) {
        // 注册子类
        AVObject.registerSubclass(com.zhengjt.cleannote.Note.class);
        AVOSCloud.setDebugLogEnabled(true);
        // 初始化应用 Id 和 应用 Key，您可以在应用设置菜单里找到这些信息
        AVOSCloud.initialize(ctx, "tT949YVLKL41E0hvhRcbMeOH",
                "8NnuvgsvJO0Ga1ybX1sVT80N");
        // 启用崩溃错误报告
        AVAnalytics.enableCrashReport(ctx, true);
        AVOSCloud.setLastModifyEnabled(true);
    }

    public static void fetchNoteById(String objectId, GetCallback<AVObject> getCallback) {
        com.zhengjt.cleannote.Note note = new com.zhengjt.cleannote.Note();
        note.setObjectId(objectId);
        // 通过Fetch获取content内容
        note.fetchInBackground(getCallback);
    }

    public static void createOrUpdateNote(String objectId, String title, String content, SaveCallback saveCallback) {
        final com.zhengjt.cleannote.Note note = new com.zhengjt.cleannote.Note();
        if (!TextUtils.isEmpty(objectId)) {
            // 如果存在objectId，保存会变成更新操作。
            note.setObjectId(objectId);
        }
        note.setTitle(title);
        note.setContent(content);
        // 异步保存
        note.saveInBackground(saveCallback);
    }

    public static List<com.zhengjt.cleannote.Note> findNotes() {
        // 查询当前Note列表
        AVQuery<com.zhengjt.cleannote.Note> query = AVQuery.getQuery(com.zhengjt.cleannote.Note.class);
        // 按照更新时间降序排序
        query.orderByDescending("updatedAt");
        // 最大返回1000条
        query.limit(1000);
        try {
            return query.find();
        } catch (AVException exception) {
            Log.e("tag", "Query notes failed.", exception);
            return Collections.emptyList();
        }
    }

    public static void searchQuery(String inputSearch) {
        AVSearchQuery searchQuery = new AVSearchQuery(inputSearch);
        searchQuery.search();
    }
}
