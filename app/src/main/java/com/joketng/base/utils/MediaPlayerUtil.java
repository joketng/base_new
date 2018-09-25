package com.joketng.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import com.jointem.base.util.CommonConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:音频播放
 * Created by molin on 17/1/3.
 */
public class MediaPlayerUtil {

    private static MediaPlayer mPlayer;
    private static boolean isPause = false;

    /**
     * 播放（从头播）
     *
     * @param videoUrl 语音url
     */
    public static void play(String videoUrl, MediaPlayer.OnCompletionListener completionListener) {
        if (null == mPlayer) {
            mPlayer = new MediaPlayer();
        }
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mPlayer.reset();
                return false;
            }
        });
        mPlayer.setOnCompletionListener(completionListener);
        try {
            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            mPlayer.setDataSource(videoUrl);
            mPlayer.prepare();// prepare之后自动播放
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放（从断点处继续播）
     */
    public static void start() {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
            isPause = false;
        }
    }

    /**
     * 暂停
     */
    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 停止播放
     */
    public static void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * 是否正在播放
     */
    public static boolean isPlaying() {
        if (mPlayer != null) {
            return mPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 是否暂停中
     */
    public static boolean isPause() {
        return isPause;
    }

    public static void reset() {
        if (isPlaying() || isPause()) {
            if (mPlayer != null) {
                mPlayer.stop();
            }
        }
        mPlayer = null;
        isPause = false;
    }

    public static void setLoudSpeaker(Context context, boolean flag) {
        if (flag) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMicrophoneMute(false);
            audioManager.setSpeakerphoneOn(true);// 使用扬声器播放，即使已经插入耳机
            audioManager.setMode(AudioManager.STREAM_MUSIC);
        } else {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMicrophoneMute(false);
            audioManager.setSpeakerphoneOn(false);// 使用扬声器播放，即使已经插入耳机
            audioManager.setMode(AudioManager.STREAM_MUSIC);
        }
    }

    public static int getAudioDuration(Context context, String path) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(context, Uri.fromFile(new File(path)));  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int duration = player.getDuration();//获取音频的时间
//        Log.d("ACETEST", "### duration: " + duration);
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//这里想要只保留分秒可以写成"mm:ss"
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
//        String hms = formatter.format(duration);
        player.release();//记得释放资源
        return duration;
    }

    public static String getVideoDuration(Context context, String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path); //在获取前，设置文件路径（应该只能是本地路径）
        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        retriever.release(); //释放
        return duration;
    }

    public static String bitmap2File(Bitmap bitmap) {
        //此处范围的所谓外部存储是手机的自带内存32G,64G，并不是SD卡，是否有访问权限
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String dir = CommonConstants.PICTURE_PATH + File.separator + "wyc_img";
            isExist(dir);
//            File newFileDir = new File(dir, System.currentTimeMillis() + "wyc.jpg");
//
//            if (!newFileDir.exists()) {
//
//                newFileDir.mkdir();
//
//            }

            File file = new File(dir, "wyc_" + System.currentTimeMillis() + ".jpg");


//打开文件输出流

            FileOutputStream os = null;

            try {

                os = new FileOutputStream(file);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);

                os.flush();

                os.close();
                path = file.getAbsolutePath();

                return path;
            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            return path;

        }
        return path;
    }
        public static void isExist (String path){
            File file = new File(path);
//判断文件夹是否存在,如果不存在则创建文件夹
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }


