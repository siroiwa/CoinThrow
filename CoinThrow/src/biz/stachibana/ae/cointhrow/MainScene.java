package biz.stachibana.ae.cointhrow;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainScene extends KeyListenScene implements IOnSceneTouchListener {
	private AnimatedSprite coin;
	// ドラッグ開始座標
	private float[] touchStartPoint;
	// フリック中か否か
	private boolean isDragging;
	// 画面タッチ可否
	private boolean isTouchEnabled;
	// コインが飛翔中であるか否か
	private boolean isCoinFlying;
	// コインが飛び出す角度
	private double flyAngle;
	// コインのx座標移動速度
	private float flyXVelocity;
	// コインのy座標移動速度
	private float initialCoinSpeed;
	
	public MainScene(MultiSceneActivity baseActivity) {
		super(baseActivity);
		init();
	}

	public void init() {
		attachChild(getBaseActivity().getResourceUtil().getSprite("main_bg.png"));
		
		// フラグ初期値セット
		touchStartPoint = new float[2];
		isTouchEnabled = true;
		isDragging = false;
		isCoinFlying = false;
		// コインのy座標移動の初速を決定
		initialCoinSpeed = 30f;
		// Sceneのタッチリスナーを登録
		setOnSceneTouchListener(this);
		// アップデートハンドラーｗｐ登録
		registerUpdateHandler(updateHandler);
		
		setNewCoin();
	}
	
	public void setNewCoin(){
		// 古いコインが存在している場合は消去
		if(coin != null){
			detachChild(coin);
		}
		
		// コインをインスタンス化
		coin = getBaseActivity().getResourceUtil().getAnimatedSprite("coin_100.png", 1, 3);
		
		// x座標を画面中心、y座標を600に設定
		placeToCenterX(coin, 600);
		
		attachChild(coin);
	}

	@Override
	public void prepareSoundAndMusic() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	
	// タッチイベントが発生したら呼ばれる
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent){
		// タッチの座標を取得
		float x = pSceneTouchEvent.getX();
		float y = pSceneTouchEvent.getY();
		// 指が触れた瞬間のイベント
		// タッチの座標がコイン上であるかどうかチェック
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN
				&& isTouchEnabled
				&& (x > coin.getX() && x < coin.getX() + coin.getWidth())
				&& (y > coin.getY() && y < coin.getY() + coin.getHeight())){
			
			// フラグ
			isTouchEnabled = false;
			isDragging = true;
			
			// 開始点を登録
			touchStartPoint[0] = x;
			touchStartPoint[1] = y;
		
		// 指が離れたとき、何らかの原因でタッチ処理が中断した場合のイベント
		}else if((pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP ||
				  pSceneTouchEvent.getAction() == TouchEvent.ACTION_CANCEL) && isDragging){
			
			// 終点を登録
			float[] touchEndPoint = new float[2];
			touchEndPoint[0] = x;
			touchEndPoint[1] = y;
			
			// フリックの距離が短すぎる時にはフリックと判定しない
			if((touchEndPoint[0] - touchStartPoint[0] < 50 && touchEndPoint[0] - touchStartPoint[0] > -50) &&
			   (touchEndPoint[1] - touchStartPoint[1] < 50 && touchEndPoint[1] - touchStartPoint[1] > -50)){
					
				isTouchEnabled = true;
				isDragging = false;
				return true;
			}
			
			// フリックの角度を求める
			flyAngle = getAngleByTwoPosition(touchStartPoint, touchEndPoint);
			// 下から上へのフリックを0度に調整
			flyAngle -= 180;
			// フリックの角度が前向きでない時はフリックを無効に
			if(flyAngle < -80 || flyAngle > 80){
				isTouchEnabled = true;
				isDragging = false;
				return true;
			}
			
			// コインのx座標移動速度を調整
			flyXVelocity = (float)(flyAngle / 10.0f);
			// フラグをONに
			isCoinFlying = true;
			// アニメーション開始
			coin.animate(50);
			// コインの角度をランダムに設定
			coin.setRotation((int)(Math.random() * 360));
		}
		
		return true;
	}
	
	// アップデートハンドラ。1秒間に60回呼び出される
	public TimerHandler updateHandler = new TimerHandler(
			1f / 60f,
			true,
			new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					if(isCoinFlying){
						// コインが飛んでいるなら実行
						if(initialCoinSpeed > 3.0f){
							// 速度を徐々に落とす
							initialCoinSpeed *= 0.96;
						}
						// コインを徐々に小さく
						coin.setScale(coin.getScaleX() * 0.97f);
						// x座標の移動
						coin.setX(coin.getX() + flyXVelocity);
						// xの移動量を徐々に大きく
						flyXVelocity *= 1.03f;
						// y座標の移動
						coin.setY(coin.getY() - initialCoinSpeed);
					}
				}
			});
	
	// 2点間の角度を求める公式
	private double getAngleByTwoPosition(float[] start, float[] end){
		double result = 0;
		
		float xDistance = end[0] - start[0];
		float yDistance = end[1] - start[1];
		
		result = Math.atan2((double)yDistance, (double)xDistance) * 180 / Math.PI;
		
		result += 270;
		
		return result;
	}
}
