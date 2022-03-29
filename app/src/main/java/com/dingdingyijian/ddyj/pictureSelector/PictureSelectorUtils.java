package com.dingdingyijian.ddyj.pictureSelector;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.dingdingyijian.ddyj.R;
import com.dingdingyijian.ddyj.utils.ComUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;

/**
 * @author tyh
 * @date 2020/5/22
 */
public class PictureSelectorUtils {
    private Dialog dialog;
    private Display display;
    private static PictureSelectorUtils instance;
    private Context mContext;

    private PictureSelectorUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public synchronized static PictureSelectorUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (PictureSelectorUtils.class) {
                if (instance == null) {
                    instance = new PictureSelectorUtils(context);
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("deprecation")
    public void PictureSelector(Activity activity, int maxSelectNum, int minSelectNum, int imageSpanCount) {
        if (!ComUtil.isDestroy(activity)) {
            int animationMode = AnimationType.ALPHA_IN_ANIMATION;
            PictureWindowAnimationStyle mWindowAnimationStyle = new PictureWindowAnimationStyle();
            mWindowAnimationStyle.ofAllAnimation(R.anim.picture_anim_up_in, R.anim.picture_anim_down_out);
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
            // 获取Dialog布局
            View view = LayoutInflater.from(activity).inflate(R.layout.info_photo_view, null);
            dialog = new Dialog(activity, R.style.AlertDialogStyle);
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);
            // 设置Dialog最小宽度为屏幕宽度
            view.setMinimumWidth(display.getWidth());
            // 获取自定义Dialog布局中的控件
            RelativeLayout rl_click = view.findViewById(R.id.rl_click);
            RelativeLayout content_pictures = view.findViewById(R.id.content_pictures);
            RelativeLayout content_album = view.findViewById(R.id.content_album);
            TextView cancelTV = view.findViewById(R.id.cancelTV);
            dialog.setContentView(view);
            dialog.show();
            /**
             * 设置宽高
             */
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
            //相册
            content_album.setOnClickListener(v -> {
                //打开相册
                PictureSelector.create(activity)
                        .openGallery(PictureMimeType.ofImage()) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageEngine(GlideEngine.createGlideEngine()) //外部传入图片加载引擎，必传项
                        .isWeChatStyle(true) //是否开启微信样式
                        .isUseCustomCamera(true) //是否使用自定义相机
                        .maxSelectNum(maxSelectNum) // 最大图片选择数量
                        .minSelectNum(minSelectNum)//最小选择数量
                        .setRecyclerAnimationMode(animationMode) //列表动画效果
                        .setPictureWindowAnimationStyle(mWindowAnimationStyle) //自定义相册启动退出动画
                        .synOrAsy(true) //是否同步
                        .isMaxSelectEnabledMask(true) //选择条件达到阀时列表是否启用蒙层效果
                        .isGif(false) //是否显示gif图
                        .setCaptureLoadingColor(ContextCompat.getColor(activity, R.color.colorBannerOrange)) //拍照进度颜色
                        .minimumCompressSize(100) //小于100kb不压缩
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                        .setButtonFeatures(CustomCameraView.BUTTON_STATE_ONLY_CAPTURE)// 自定义相机按钮状态,只能拍照
                        .isPreviewImage(true)// 是否可预览图片
                        .isCamera(true) //是否显示拍照按钮
                        .imageSpanCount(imageSpanCount) //每行显示多少条
                        .isCompress(true) //是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.dismiss();
            });
            //拍照
            content_pictures.setOnClickListener(v -> {
                //拍照
                PictureSelector.create(activity)
                        .openCamera(PictureMimeType.ofImage())
                        .imageEngine(GlideEngine.createGlideEngine()) // 外部传入图片加载引擎，必传项
                        .isUseCustomCamera(true) //是否使用自定义相机'
                        .isCompress(true)// 是否压缩
                        .setCaptureLoadingColor(ContextCompat.getColor(activity, R.color.colorBannerOrange)) //拍照进度颜色
                        .minimumCompressSize(100) //小于100kb不压缩
                        .setButtonFeatures(CustomCameraView.BUTTON_STATE_ONLY_CAPTURE)// 自定义相机按钮状态,只能拍照
                        .synOrAsy(true) //true同步 false异步
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.dismiss();
            });
            rl_click.setOnClickListener(v -> dialog.dismiss());
            cancelTV.setOnClickListener(v -> dialog.dismiss());

        }
    }
}
