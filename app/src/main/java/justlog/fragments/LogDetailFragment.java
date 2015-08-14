package justlog.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.io.File;

import cn.androidy.common.utils.DimenUtils;
import cn.androidy.common.utils.FileUtils;
import cn.androidy.common.utils.SizeColorPhrase;
import cn.androidy.logger.core.SupportLogger;
import cn.androidy.logger.data.LogEntity;
import cn.androidy.logger.data.LogReadTask;
import cn.androidy.logger.view.LoggerDetailView;
import cn.androidy.thinking.R;

public class LogDetailFragment extends Fragment implements LogReadTask.ILogReadListener {
    public static final String KEY_LOG_TAG = "KEY_LOG_TAG";
    LoggerDetailView customLoggerView;

    public static LogDetailFragment newInstance(String tag) {
        LogDetailFragment detailFragment = new LogDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_LOG_TAG, tag);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logdetail, null);
        customLoggerView = (LoggerDetailView) view.findViewById(R.id.customLoggerView);
        final String tag = getLogTag();
        SupportLogger.readLog(tag, LogDetailFragment.this);
        view.findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                File f;
                if (!TextUtils.isEmpty(tag)) {
                    f = new File(SupportLogger.getCommonLogDir(), tag);
                } else {
                    f = new File(SupportLogger.getCommonLogDir());
                }
                FileUtils.deleteFile(f.getAbsolutePath());
                SupportLogger.readLog(tag, LogDetailFragment.this);
            }
        });
        return view;
    }

    private String getLogTag() {
        return getArguments().getString(KEY_LOG_TAG);
    }

    @Override
    public void onLogGet(LogEntity entity) {
        if (entity != null) {
            customLoggerView.getLogDetailText().setText(getHighliText(entity.getMsg()));
        } else {
            customLoggerView.getLogDetailText().setText("");
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public static final String TEXT_TO_HIGHLIGHT = "===";

    private CharSequence getHighliText(String msg) {
        if (TextUtils.isEmpty(msg) || !msg.contains(TEXT_TO_HIGHLIGHT)) {
            return msg;
        } else {
//            msg = msg.replaceAll(TEXT_TO_HIGHLIGHT, "{" + TEXT_TO_HIGHLIGHT + "}");
//            CharSequence charSequence = SizeColorPhrase.from(msg).withSeparator("{}")
//                    .innerColorSize(Color.RED, DimenUtils.spTopx(getActivity(), 18)).format();
            return msg;
        }
    }
}
