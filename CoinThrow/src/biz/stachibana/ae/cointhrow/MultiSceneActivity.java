package biz.stachibana.ae.cointhrow;

import java.util.ArrayList;

import org.andengine.ui.activity.SimpleLayoutGameActivity;

public abstract class MultiSceneActivity extends SimpleLayoutGameActivity {
	// ResourceUtilのインスタンス
	private ResourceUtil mResourceUtil;
	// 起動済みのSceneの配列
	private ArrayList<KeyListenScene> mSceneArray;

	@Override
	protected void onCreateResources() {
		mResourceUtil = ResourceUtil.getInstance(MultiSceneActivity.this);
		mSceneArray = new ArrayList<KeyListenScene>();
	}

	public ResourceUtil getResourceUtil(){
		return mResourceUtil;
	}
	
	public ArrayList<KeyListenScene> getSceneArray(){
		return mSceneArray;
	}
	
	// 起動済みのKeyListenScreenを格納する配列
	public abstract void appendScene(KeyListenScene scene);
	// 最初のシーンに戻るための関数
	public abstract void backToInitial();
	// シーンとシーン格納配列を更新する関数
	public abstract void refreshRunningScreen(KeyListenScene scene);
}
