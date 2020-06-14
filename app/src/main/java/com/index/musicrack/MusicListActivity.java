package com.index.musicrack;

//AndroidX

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MusicListActivity extends AppCompatActivity {
    List<Music> mMusicList;
    MusicAdapter mMusicAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        mMusicList = new ArrayList<>();
//        TextView textView = findViewById(R.id.text_view);

        mListView = findViewById(R.id.List);

        loadMusics();

        mMusicAdapter = new MusicAdapter(this, R.layout.music,mMusicList);
        mListView.setAdapter(mMusicAdapter);

//        if (mMusicList.size() > 0) textView.setText(mMusicList.get(0).title);
    }

    //ContentProviderからmusicListにデータを集める
    private void loadMusics() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;
        StringBuilder sb = null;
        // true: images, false:audio
        boolean flg = false;


        // 例外を受け取る
        try {
            if (flg) {
                // images
                cursor = contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null);
            } else {
                // audio
                cursor = contentResolver.query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                        null, null, null);
            }

            if (cursor != null && cursor.moveToFirst()) {
                String str = String.format(
                        "MediaStore.Images = %s\n\n", cursor.getCount());

                sb = new StringBuilder(str);

                do {

                    String id = cursor.getString(cursor.getColumnIndex(
                            MediaStore.Images.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndex(
                            MediaStore.Images.Media.DISPLAY_NAME));
                    String uri = (cursor.getString(cursor.getColumnIndex(
                            MediaStore.Images.Media._ID)));
                    Music music = new Music(id, title, uri);

                    mMusicList.add(music);


                    sb.append("ID: ");
                    sb.append(cursor.getString(cursor.getColumnIndex(
                            MediaStore.Images.Media._ID)));
                    sb.append("\n");
                    sb.append("Title: ");
                    sb.append(cursor.getString(cursor.getColumnIndex(
                            MediaStore.Images.Media.TITLE)));
                    sb.append("\n");
                    sb.append("Path: ");
                    sb.append(cursor.getString(cursor.getColumnIndex(
                            MediaStore.Images.Media.DATA)));
                    sb.append("\n\n");

                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

            Toast toast = Toast.makeText(this,
                    "例外が発生、Permissionを許可していますか？", Toast.LENGTH_SHORT);
            toast.show();

            //MainActivityに戻す
            finish();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // ここまで実行されれば、端末の全部の音楽データがmusicListに入るはず
//        textView.setText(sb);
    }
}
