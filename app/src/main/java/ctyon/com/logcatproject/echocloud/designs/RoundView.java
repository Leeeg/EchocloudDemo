package ctyon.com.logcatproject.echocloud.designs;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class RoundView extends LoaderView {
    private Circle eye;
    private Circle eyeBound;
    private Circle eyeBoundRound;
    private Circle[] sharingans;
    private int numberOfSharingan;
    private float rotate;
    private float scale;
    private float eyeBoundRadius;

    ValueAnimator rotateAnimator;
    ValueAnimator scaleAnimator;
    AnimatorSet animatorSet;

    private int animationRepeatCount = ValueAnimator.RESTART;

    public RoundView() {
        numberOfSharingan = 3;

        rotateAnimator = ValueAnimator.ofFloat(0, 360);
        rotateAnimator.setDuration(1500);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotate = (float) animation.getAnimatedValue();
                if (invalidateListener != null) {
                    invalidateListener.reDraw();
                }
            }
        });

        scaleAnimator = ValueAnimator.ofFloat(1f, 1.2f, 1f);
        scaleAnimator.setDuration(1000);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                if (invalidateListener != null) {
                    invalidateListener.reDraw();
                }
            }
        });

        animatorSet = new AnimatorSet();
        animatorSet.play(rotateAnimator).with(scaleAnimator);
    }

    @Override
    public void initializeObjects() {
        float r = Math.min(width, height) / 2f;
        eyeBoundRadius = r / 1.2f;

        eye = new Circle();
        eye.setCenter(center.x, center.y);
        eye.setColor(color);
        eye.setRadius(r / 4);

        eyeBoundRound = new Circle();
        eyeBoundRound.setCenter(center.x, center.y);
        eyeBoundRound.setColor(Color.parseColor("#FF4081"));
        eyeBoundRound.setRadius(eyeBoundRadius);
        eyeBoundRound.setWidth(r / 20f);

        eyeBound = new Circle();
        eyeBound.setCenter(center.x, center.y);
        eyeBound.setColor(color);
        eyeBound.setRadius(eyeBoundRadius);
        eyeBound.setStyle(Paint.Style.STROKE);
        eyeBound.setWidth(r / 20f);

        sharingans = new Circle[numberOfSharingan];
        for (int i = 0; i < numberOfSharingan; i++) {
            sharingans[i] = new Circle();
            sharingans[i].setCenter(center.x, center.y - eyeBoundRadius);
            sharingans[i].setColor(color);
            sharingans[i].setRadius(r / 5);
        }
    }

    @Override
    public void setAnimation() {
        Log.d("Lee_log", "Sharingan ： setAnimation");
        animatorSet.start();
    }

    private void setRepeatCount(){
        rotateAnimator.setRepeatCount(animationRepeatCount);
        scaleAnimator.setRepeatCount(animationRepeatCount);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.scale(scale, scale, center.x, center.y);
        canvas.rotate(rotate, center.x, center.y);
        eyeBoundRound.draw(canvas);
        eyeBound.draw(canvas);
        eye.draw(canvas);
        for (int i = 0; i < numberOfSharingan; i++) {
            canvas.save();
            canvas.rotate(i * 120, center.x, center.y);
            sharingans[i].draw(canvas);
            canvas.restore();
        }
        canvas.restore();
    }

    public void setAnimationRepeatAble(boolean animationRepeatAble) {
        if (animationRepeatAble){
            this.animationRepeatCount = ValueAnimator.INFINITE;
            setRepeatCount();
            animatorSet.start();
        }else {
            this.animationRepeatCount = ValueAnimator.RESTART;
            setRepeatCount();
            animatorSet.end();
        }
        Log.d("Lee_log", "Sharingan ： setAnimationRepeatAble : " + animationRepeatCount);
    }
}
