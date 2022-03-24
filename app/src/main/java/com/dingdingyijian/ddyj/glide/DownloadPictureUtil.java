package com.dingdingyijian.ddyj.glide;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * create at 2020/9/4  16:34
 * 图片下载保存工具类
 */
public class DownloadPictureUtil {

    public static void downloadPicture(final Context context, final String url) {
        if (!assertValidRequest(context)) {
            return;
        }
        GlideApp.with(context).downloadOnly().load(url).into(new FileTarget() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                ToastUtil.getInstance().longs(context, "开始下载...");

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                ToastUtil.getInstance().longs(context, "保存失败");
            }

            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                super.onResourceReady(resource, transition);
                // 传入的保存文件夹名
                final String downloadFolderName = "DDYJ";
                // 保存的图片名称
                String name = System.currentTimeMillis() + "";
                String mimeType = ImageUtil.getImageTypeWithMime(resource.getAbsolutePath());
                name = name + "." + mimeType;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // 大于等于29版本的保存方法
                    ContentResolver resolver = context.getContentResolver();
                    // 设置文件参数到ContentValues中
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, name);
                    values.put(MediaStore.Images.Media.DESCRIPTION, name);
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + mimeType);
                    values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + downloadFolderName + "/");

                    Uri insertUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    BufferedInputStream inputStream = null;
                    OutputStream os = null;
                    try {
                        inputStream = new BufferedInputStream(new FileInputStream(resource.getAbsolutePath()));
                        if (insertUri != null) {
                            os = resolver.openOutputStream(insertUri);
                        }
                        if (os != null) {
                            byte[] buffer = new byte[1024 * 4];
                            int len;
                            while ((len = inputStream.read(buffer)) != -1) {
                                os.write(buffer, 0, len);
                            }
                            os.flush();
                        }
                        ToastUtil.getInstance().longs(context, "成功保存到 ".concat(Environment.DIRECTORY_PICTURES + "/" + downloadFolderName));
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtil.getInstance().longs(context, "保存失败");
                    } finally {
                        try {
                            if (os != null) {
                                os.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // 低于29版本的保存方法
                    final String path = Environment.getExternalStorageDirectory() + "/" + downloadFolderName + "/";
                    FileUtil.createFileByDeleteOldFile(path + name);
                    boolean result = FileUtil.copyFile(resource, path, name);
                    if (result) {
                        ToastUtil.getInstance().longs(context, "成功保存到 ".concat(path));
                        new SingleMediaScanner(context, path.concat(name), () -> {
                        });
                    } else {
                        ToastUtil.getInstance().longs(context, "保存失败");
                    }
                }
            }
        });
    }

    /**
     * 解决加载图片activity被销毁问题
     *
     * @param context
     * @return
     */
    private static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !isDestroy(activity);
        } else if (context instanceof ContextWrapper) {
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper.getBaseContext() instanceof Activity) {
                Activity activity = (Activity) contextWrapper.getBaseContext();
                return !isDestroy(activity);
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        if (activity == null) {
            return true;
        }
        return activity.isFinishing() || activity.isDestroyed();
    }
}