package biz.stachibana.ae.cointhrow;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;

public class MainActivity extends MultiSceneActivity {
	// 画面のサイズ。
	private int CAMERA_WIDTH = 480;
	private int CAMERA_HEIGHT = 800;

	public EngineOptions onCreateEngineOptions() {
		// サイズを指定し描画範囲をインスタンス化
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// ゲームのエンジンを初期化。
		// 第1引数 タイトルバーを表示しないモード
		// 第2引数 画面は縦向き（幅480、高さ800）
		// 第3引数 解像度の縦横比を保ったまま最大まで拡大する
		// 第4引数 描画範囲
		EngineOptions eo = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return eo;
	}

	@Override
	protected Scene onCreateScene() {
		// MainSceneをインスタンス化し、エンジンにセット
		MainScene mainScene = new MainScene(this);
		return mainScene;
	}

	@Override
	protected int getLayoutID() {
		// ActivityのレイアウトのIDを返す
		return R.layout.activity_main;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		// SceneがセットされるViewのIDを返す
		return R.id.renderview;
	}

	@Override
	public void appendScene(KeyListenScene scene) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void backToInitial() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void refreshRunningScreen(KeyListenScene scene) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
