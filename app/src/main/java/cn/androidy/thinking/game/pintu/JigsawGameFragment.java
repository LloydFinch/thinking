package cn.androidy.thinking.game.pintu;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.androidy.thinking.R;

/**
 * @author mwping1324@163.com Created on 2015年1月20日 上午11:10:40
 */
public class JigsawGameFragment extends Fragment {

	private JigsawView jigsawView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_jigsawgame, null);
		jigsawView = (JigsawView) v.findViewById(R.id.jigsawView);
		jigsawView.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.game_main_pic1), 3,
				3);
		return v;
	}
}
