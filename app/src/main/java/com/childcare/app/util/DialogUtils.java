package com.childcare.app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.childcare.app.R;

/**
 * 对话框工具类
 *
 * @author john
 * @since 2017-03-27
 */

@SuppressWarnings("ALL")
public final class DialogUtils {

    /***
     * 获取一个普通对话框构建器
     *
     * @param context 上下文
     * @return 对话框构建器
     */
    private static AlertDialog.Builder getDialog(Context context) {
        return new AlertDialog.Builder(context);
    }

    /***
     * 获取一个进度对话框
     *
     * @param context 上下文
     * @param message 消息
     * @return 进度对话框
     */
    public static ProgressDialog getProgressDialog(Context context,
                                                   String message) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.ProgressDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.dialog_progress, null);
        TextView msgTv = (TextView) customView.findViewById(R.id.tv_message);

        if (!StringUtils.isEmpty(message)) {
            msgTv.setText(message);
        }

        progressDialog.show();
        progressDialog.setContentView(customView);

        return progressDialog;
    }

    /***
     * 获取一个信息对话构建器
     *
     * @param context         上下文
     * @param title           标题
     * @param message         消息内容
     * @param onClickListener 确认按钮监听器
     * @return 带有按钮的对话框
     */
    public static AlertDialog.Builder getMessageDialog(Context context,
                                                       String title,
                                                       String message,
                                                       OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.dialog_action_ok), onClickListener);
        return builder;
    }

    /***
     * 获取一个信息对话构建器(单个按钮)
     *
     * @param context         上下文
     * @param message         消息内容
     * @param onClickListener 确认按钮监听器
     * @return 带有按钮的对话框
     */
    public static AlertDialog.Builder getMessageDialog(Context context,
                                                       String message,
                                                       OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.dialog_action_ok), onClickListener);
        return builder;
    }

    /**
     * 获取消息对话框构建器
     *
     * @param context 上下文
     * @param message 消息内容
     * @return 消息对话框构建器
     */
    public static AlertDialog.Builder getMessageDialog(Context context,
                                                       String message) {
        return getMessageDialog(context, message, null);
    }

    /**
     * 获取确认消息对话框构建器
     *
     * @param context         上下文
     * @param message         消息内容
     * @param onClickListener 确认按钮监听器(一个按钮)
     * @return 确认消息对话框构建器
     */
    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       String okString,
                                                       OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(okString, onClickListener);
        return builder;
    }

    /**
     * 获取确认消息对话框构建器
     *
     * @param context 上下文
     * @param message 消息内容
     * @return 确认消息对话框构建器
     */
    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       String okString,
                                                       OnClickListener onOkListener,
                                                       String cancelString,
                                                       OnClickListener onCancelListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(okString, onOkListener);
        builder.setNegativeButton(cancelString, onCancelListener);
        return builder;
    }

    /**
     * 获取确认消息对话框构建器
     *
     * @param context               上下文
     * @param message               消息内容
     * @param onOkClickListener     确认按钮监听器
     * @param onCancelClickListener 取消按钮监听器
     * @return 确认消息对话框构建器
     */
    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       OnClickListener onOkClickListener,
                                                       OnClickListener onCancelClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.dialog_action_ok),
                onOkClickListener);
        builder.setNegativeButton(context.getString(R.string.dialog_action_cancel),
                onCancelClickListener);
        return builder;
    }

    /**
     * 获取多选对话框构建器
     *
     * @param context         上下文
     * @param title           标题
     * @param arrays          选项数据
     * @param onClickListener 点击选项的监听器
     * @return 多选对话框构建器
     */
    public static AlertDialog.Builder getSelectDialog(Context context,
                                                      String title,
                                                      String[] arrays,
                                                      OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!StringUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton(context.getString(R.string.dialog_action_cancel), null);
        return builder;
    }

    /**
     * 获取多选对话框构建器(无标题)
     *
     * @param context         上下文
     * @param arrays          选项数据
     * @param onClickListener 点击选项的监听器
     * @return 多选对话框构建器
     */
    public static AlertDialog.Builder getSelectDialog(Context context,
                                                      String[] arrays,
                                                      OnClickListener onClickListener) {
        return getSelectDialog(context, "", arrays, onClickListener);
    }

    /**
     * 获取单选对话框构建器
     *
     * @param context         上下文
     * @param title           标题
     * @param arrays          选项数据
     * @param selectIndex     选择索引
     * @param onClickListener 点击选项监听器
     * @return 单选对话框构建器
     */
    public static AlertDialog.Builder getSingleChoiceDialog(Context context,
                                                            String title,
                                                            String[] arrays,
                                                            int selectIndex,
                                                            OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!StringUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton(context.getString(R.string.dialog_action_cancel), null);
        return builder;
    }

    /**
     * 获取单选对话框构建器(无标题)
     *
     * @param context         上下文
     * @param arrays          选项数据
     * @param selectIndex     选择索引
     * @param onClickListener 点击选项监听器
     * @return 单选对话框构建器
     */
    public static AlertDialog.Builder getSingleChoiceDialog(Context context,
                                                            String[] arrays,
                                                            int selectIndex,
                                                            OnClickListener onClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
    }

    /**
     * 退出对话框
     */
    public static AlertDialog getQuitDialog(Context context,
                                            String message,
                                            OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setPositiveButton(R.string.dialog_action_quit, onClickListener)
               .setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
               .setTitle(R.string.dialog_title_reminder_tips)
               .setMessage(message)
               .setCancelable(false);
        return builder.create();
    }



}