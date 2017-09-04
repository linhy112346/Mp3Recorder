package boyue.com.myapplication;

import android.content.Context;
import android.util.Log;

import com.czt.mp3recorder.MP3Recorder;

import java.io.File;
import java.io.IOException;

/**
 * 描述：
 * 作者：chezi008 on 2016/11/24 08:55
 * 邮箱：chezi008@163.com
 */
public class MP3RecorderManager {

    private Context     context;
    /**
     * MP3Recorder工具
     */
    private MP3Recorder recorder;
    /**
     * mp3文件
     */
    private File        mp3File;
    /**
     * 录音状态
     */
    private boolean isRecording = false;
    /**
     * 录音时长
     */
    private int mp3Duration = 0;

    public MP3RecorderManager(Context context, File mp3File) {
        this.mp3File = mp3File;
        this.context = context;
    }

    /**
     * 开始录音
     */
    public void startRecorder() {
        if (isRecording) {
            return;
        }
        mp3File.deleteOnExit();

        recorder = new MP3Recorder(mp3File);
        try {
            recorder.start();
            new Thread(mGetVoiceLevelRunnable).start();
            isRecording = true;
//            ThreadPoolUtils.execute(mGetVoiceLevelRunnable);
            mp3Duration = 0;
        } catch (IOException e) {
            e.printStackTrace();
//            ToastUtil.show(context, "sorry! 未知原因无法录音，请联系总部");
        }
    }
    public void pauseRecorder(){
        isRecording = false;
        recorder.pause();
    }
    public void resumeRecorder(){
        isRecording = true;
        new Thread(mGetVoiceLevelRunnable).start();
        recorder.resume();
    }
    /**
     * 结束录音
     *
     * @return 返回录音时长（s）未知错误时返回0
     */
    public int stopRecorder() {
        if (!isRecording) {//从暂停直接停止
            recorder.resume();
            recorder.stop();
            recorder = null;
            return mp3Duration;
        }
        recorder.stop();
        recorder = null;
        isRecording = false;
        return (int) mp3Duration;
    }

    /**
     * 删除录音文件
     */
    public void deleteFile() {
        mp3File.deleteOnExit();
    }

    // 获取时间
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mp3Duration += 100;
                    Log.d("MP3RecorderManager", "run: "+convertMilliSecondToMinute2(mp3Duration));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private String convertMilliSecondToMinute2(int mp3Duration) {
        int seceond = mp3Duration / 1000;
        int minute = seceond / 60;
        int hour = minute / 60;
        return (hour < 10 ? "0" + hour:hour+"")+":"+(minute<10?"0"+minute:minute+"")+":"+(seceond<10?"0"+seceond:seceond+"");
    }
}
