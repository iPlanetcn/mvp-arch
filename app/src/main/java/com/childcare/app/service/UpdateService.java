package com.childcare.app.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.MimeTypeMap;

import com.childcare.app.R;
import com.childcare.app.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import timber.log.Timber;

import static com.childcare.app.util.constant.Extras.EXTRA_APP_DOWNLOAD_URL;

/**
 * 更新服务
 *
 * @author john
 * @since 2017-03-31
 */
public class UpdateService extends Service {
    /**
     * 超时
     */
    private static final int TIMEOUT = 10 * 1000;
    /**
     * 下载成功
     */
    private static final int DOWNLOAD_SUCCESS = 1;
    /**
     * 下载失败
     */
    private static final int DOWNLOAD_ERROR = 0;
    /**
     * 通知管理器
     */
    private NotificationManager mNotificationManager;
    /**
     * 通知ID
     */
    private static final int NOTIFICATION_ID = 0;

    /**
     * 通知构建器
     */
    private NotificationCompat.Builder mNotificationBuilder;

    /**
     * 是否正在更新
     */
    private boolean mIsUpdating = false;


    /**
     * 带若引用的Handler
     */
    private DownloadCompleteHandler mHandler = null;


    /**
     * apk文件
     */
    public File mApkFile;


    /**
     * 下载地址
     */
    private String mDownUrl = "";

    /**
     * 获取启动UpdateService的Intent
     *
     * @param context     上下文
     * @param downloadUrl APK下载地址
     * @return Intent
     */
    public static Intent getStartIntent(@NonNull Context context,
                                        @NonNull String downloadUrl) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(EXTRA_APP_DOWNLOAD_URL, downloadUrl);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new DownloadCompleteHandler(this);
        mDownUrl = intent.getStringExtra(EXTRA_APP_DOWNLOAD_URL);
        if (mDownUrl.substring(mDownUrl.length() - 4).equals(".apk")) {
            String apkName = mDownUrl.substring(mDownUrl.lastIndexOf('/') + 1,
                    mDownUrl.lastIndexOf('.'));

            createApkFile(apkName);
            createUpdateNotification();
            if (!mIsUpdating) {
                createDownloadApkThread();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载APK文件线程
     */
    private void createDownloadApkThread() {
        final Message message = new Message();
        new Thread(() -> {
            mIsUpdating = true;

            try {
                long downloadSize = downloadApkFiles(mDownUrl, mApkFile.toString());
                // 判断下载文件的大小
                if (downloadSize > 0) {
                    message.what = DOWNLOAD_SUCCESS;
                } else {
                    message.what = DOWNLOAD_ERROR;
                }
            } catch (Exception e) {
                Timber.e(e.getMessage());
                message.what = DOWNLOAD_ERROR;
            } finally {
                mHandler.sendMessage(message);
            }

            mIsUpdating = false;
        }).start();

    }

    /**
     * 下载APK文件
     *
     * @param downUrl 下载地址
     * @param file    文件名
     * @return 文件大小
     */
    private long downloadApkFiles(String downUrl, String file) {
        int downStep = 1;
        int totalSize;
        int readSize;
        int downloadCount = 0;
        int updateCount = 0;
        InputStream inputStream;
        OutputStream outputStream;
        URL downloadUrl;
        HttpURLConnection connection;
        try {
            downloadUrl = new URL(downUrl);
            connection = (HttpURLConnection) downloadUrl.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            totalSize = connection.getContentLength();
            if (connection.getResponseCode() == 404) {
                throw new Exception("failed");
            }

            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while ((readSize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readSize);
                downloadCount += readSize;
                if (updateCount == 0 ||
                        (downloadCount * 100 / totalSize - downStep) >= updateCount) {
                    updateCount += downStep;
                    mNotificationBuilder.setProgress(100, updateCount, false);
                    mNotificationBuilder.setContentText(
                            getString(R.string.noti_download_apk_progress, updateCount));
                    mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
                }
            }

            connection.disconnect();
            inputStream.close();
            outputStream.close();

            mNotificationBuilder.setProgress(0, 0, false);
            mNotificationBuilder.setContentText(
                    getString(R.string.noti_download_apk_complete_touch_install));
            mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());

        } catch (Exception e) {
            Timber.e(e.getMessage());
        }

        return downloadCount;
    }

    /**
     * 下载通知栏消息
     */
    private void createUpdateNotification() {
        mNotificationBuilder = new NotificationCompat.Builder(this,"channelId");
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mNotificationBuilder.setLargeIcon(largeIcon)
                            .setSmallIcon(R.drawable.ic_noti_download)
                            .setTicker(getString(R.string.noti_download_apk_ticker))
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText(getString(R.string.noti_download_apk_content_text))
                            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                            .setAutoCancel(true);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    /**
     * 创建APK文件
     *
     * @param fileName 文件名
     */
    public void createApkFile(String fileName) {
        mApkFile = new File(Environment.getExternalStorageDirectory().getPath(), fileName);
        try {
            if (!mApkFile.exists()) {
                boolean createdFlag = mApkFile.createNewFile();
                if (!createdFlag) {
                    Timber.d("create new file failed");
                }
            }
        } catch (IOException e) {
            Timber.e(e.getMessage());
        }
    }

    /**
     * 安装APK
     */
    private void installationApk() {
        mNotificationManager.cancel(NOTIFICATION_ID);
        stopSelf();
        Uri uri = Uri.fromFile(mApkFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, MimeTypeMap.getSingleton().getMimeTypeFromExtension("apk"));

        startActivity(intent);
    }

    /**
     * 下载的消息处理
     */
    private static class DownloadCompleteHandler extends Handler {
        // 通过若引用，避免内存泄漏
        WeakReference<UpdateService> mService;

        DownloadCompleteHandler(UpdateService service) {
            mService = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UpdateService updateService = mService.get();
            switch (msg.what) {
                // 下载成功处理
                case DOWNLOAD_SUCCESS:
                    updateService.installationApk();
                    break;
                // 下载错误处理
                case DOWNLOAD_ERROR:
                    ToastUtils.show(mService.get().getApplicationContext(),
                            R.string.msg_download_error);
                    break;
                // 停止更新服务
                default:
                    updateService.stopSelf();
                    break;
            }
        }
    }


}
