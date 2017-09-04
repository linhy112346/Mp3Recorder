package boyue.com.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity
        extends AppCompatActivity
{
    @Bind(R.id.btn_start)
    Button mBtnStart;
    @Bind(R.id.btn_pause)
    Button mBtnPause;
    @Bind(R.id.btn_continue)
    Button mBtnContinue;
    @Bind(R.id.btn_stop)
    Button mBtnStop;
    @Bind(R.id.btn_play)
    Button mBtnPlay;
    private MP3RecorderManager MP3RecorderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MP3RecorderManager = new MP3RecorderManager(this,
                                                    new File(Environment.getExternalStorageDirectory(),
                                                             "test.mp3"));

    }

    @OnClick({R.id.btn_start,
              R.id.btn_pause,
              R.id.btn_continue,
              R.id.btn_stop,
              R.id.btn_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                MP3RecorderManager.startRecorder();
                break;
            case R.id.btn_pause:
                MP3RecorderManager.pauseRecorder();
                break;
            case R.id.btn_continue:
                MP3RecorderManager.resumeRecorder();
                break;
            case R.id.btn_stop:
                MP3RecorderManager.stopRecorder();
                break;
            case R.id.btn_play:
                MediaPlayer mp = new MediaPlayer();
                try {
                    mp.setDataSource(Environment.getExternalStorageDirectory() + "/test.mp3");
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
