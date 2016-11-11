package com.peterombodi.customprogress.progress;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.peterombodi.customprogress.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 09.11.2016.
 */

public class SquareProgress extends View implements Animator.AnimatorListener {

    private static final String TAG = "SquareProgress";
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;
    private static final int BASE_DURATION = 3000;
    public static final boolean AVERS = true;
    public static final boolean REVERS = false;
    private int drawColor;
    private int viewBackgroundColor;
    private float viewStroke = 0.0f;
    private int animationSpeed;
    private List<Item> itemList = new ArrayList<Item>();
    private Paint paintForeground;
    private Paint paintBackground;
    private boolean playStarted;
    private float viewWidth;
    private float viewHeight;
    //private ObjectAnimator  anim;
    public Item itemF;

    public SquareProgress(Context context) {
        super(context);
        init(context, null);
    }

    public SquareProgress(Context context, AttributeSet attrs) {
        super(context);
        init(context, attrs);
    }

    public SquareProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.SquareProgress,
                0, 0
        );
        try {
            drawColor = a.getInt(R.styleable.SquareProgress_drawColor, 0xff000000);
            viewBackgroundColor = a.getInt(R.styleable.SquareProgress_viewBackgroundColor, 0xff000000);
            viewStroke = a.getDimension(R.styleable.SquareProgress_lineStroke, 0.0f);
            animationSpeed = a.getInt(R.styleable.SquareProgress_animationSpeed, 1);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }

        setWillNotDraw(false);

        // Set up the paint
        paintForeground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintForeground.setStyle(Paint.Style.FILL);
        paintForeground.setAntiAlias(true);
        paintForeground.setColor(drawColor);
        paintForeground.setStrokeWidth(viewStroke);

        paintBackground = new Paint();
        paintBackground.setColor(viewBackgroundColor);
        paintBackground.setStyle(Paint.Style.FILL);


//        // In edit mode it's nice to have some demo data, so add that here.
//        if (this.isInEditMode()) {
//            Resources res = getResources();
//            addItem(0, 0, 10, res.getColor(R.color.colorPrimary));
//            addItem(100, 0, 10, res.getColor(R.color.colorAccent));
//            addItem(100, 100, 10, res.getColor(R.color.colorAccent));
//            addItem(0, 100, 10, res.getColor(R.color.colorAccent));
//        }

        final ViewTreeObserver observer = this.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                    //observer.removeOnGlobalLayoutListener(this);
//                }
                if (!playStarted) playAnimation();
            }
        });

    }

    public void playAnimation() {
        playStarted = true;
        if (itemList.get(0) != null) {
            Log.d(TAG, "-------------+++ playAnimation ------------------" + itemList.get(0).getClass().getName());
//            itemF = itemList.get(0);
//            ObjectAnimator anim = ObjectAnimator.ofFloat(itemF, "x", 0f, viewWidth-itemF.size);
//            anim.setDuration(3000);
//            anim.addUpdateListener(this);
//            anim.start();

            ValueAnimator a0 = getAlphaAnimator(itemList.get(4), 3);

            ValueAnimator a1 = getOffsetAnimator(itemList.get(2), UP, 0, AVERS);
            ValueAnimator a2 = getOffsetAnimator(itemList.get(3), RIGHT, 1000, AVERS);
            ValueAnimator a3 = getOffsetAnimator(itemList.get(0), DOWN, 0, AVERS);
            ValueAnimator a4 = getOffsetAnimator(itemList.get(1), LEFT, 1000, AVERS);

            ValueAnimator a5 = getOffsetAnimator(itemList.get(2), LEFT, 0, AVERS);
            ValueAnimator a6 = getOffsetAnimator(itemList.get(3), UP, 1000, AVERS);
            ValueAnimator a7 = getOffsetAnimator(itemList.get(0), RIGHT, 0, AVERS);
            ValueAnimator a8 = getOffsetAnimator(itemList.get(1), DOWN, 1000, AVERS);

            ValueAnimator a9 = getAlphaAnimator(itemList.get(1), 4);

            ValueAnimator a1r = getOffsetAnimator(itemList.get(2), UP, 0, REVERS);
            ValueAnimator a2r = getOffsetAnimator(itemList.get(3), RIGHT, 1000, REVERS);
            ValueAnimator a3r = getOffsetAnimator(itemList.get(0), DOWN, 0, REVERS);
            ValueAnimator a4r = getOffsetAnimator(itemList.get(1), LEFT, 1000, REVERS);

            ValueAnimator a5r = getOffsetAnimator(itemList.get(2), LEFT, 0, REVERS);
            ValueAnimator a6r = getOffsetAnimator(itemList.get(3), UP, 1000, REVERS);
            ValueAnimator a7r = getOffsetAnimator(itemList.get(0), RIGHT, 0, REVERS);
            ValueAnimator a8r = getOffsetAnimator(itemList.get(1), DOWN, 1000, REVERS);

            ValueAnimator a10 = getAlphaAnimator(itemList.get(4), 3);

            AnimatorSet animSet = new AnimatorSet();
            animSet.play(a0);

            animSet.play(a1).with(a2).after(a0);
            animSet.play(a3).with(a4).after(a1);
            animSet.play(a5).with(a6).after(a3);
            animSet.play(a7).with(a8).after(a5);

            animSet.play(a9).after(a8);

            animSet.play(a5r).with(a6r).after(a9);
            animSet.play(a7r).with(a8r).after(a5r);
            animSet.play(a1r).with(a2r).after(a7r);
            animSet.play(a3r).with(a4r).after(a1r);

            animSet.play(a10).after(a4r);

            animSet.addListener(this);
            animSet.start();
        }
    }


    @NonNull
    private ValueAnimator getOffsetAnimator(final Item item, final int direction, long startDelay, boolean avers) {
        float start = 0;
        float end = 0;
        float offset = (viewWidth - itemList.get(0).size) / 2;
        switch (direction) {
            case LEFT:
                start = item.left;
                end = item.left - offset;
                break;
            case RIGHT:
                start = item.left;
                end = item.left + offset;
                break;
            case UP:
                start = item.top;
                end = item.top - offset;
                break;
            case DOWN:
                start = item.top;
                end = item.top + offset;
                break;
        }
        if (!avers) {
            float tmp = start;
            start = end;
            end = tmp;
        }
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.setDuration(BASE_DURATION / animationSpeed);
        anim.setStartDelay(startDelay / animationSpeed);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (direction == LEFT || direction == RIGHT) {
                    item.left = (float) animation.getAnimatedValue();
                } else {
                    item.top = (float) animation.getAnimatedValue();
                }
                invalidate();
            }
        });
        return anim;
    }

    @NonNull
    private ValueAnimator getAlphaAnimator(final Item item, int repeatCount) {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 255);
        anim.setDuration((long) (BASE_DURATION / animationSpeed));
        anim.setRepeatCount(repeatCount);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = Math.round((Float) animation.getAnimatedValue());
                if (item.center) {
                    item.alpha = alpha;
                } else {
                    for (Item it : itemList) {
                        if (!it.center) it.alpha = alpha;
                    }
                }
                invalidate();
            }
        });
        return anim;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
        int sizeSmallSquare = Math.min(w, h) / 10;
        int sizeBigSquare = Math.min(w, h) / 3;

        addItem(0, 0, sizeSmallSquare, false);
        addItem(w - sizeSmallSquare, 0, sizeSmallSquare, false);
        addItem(w - sizeSmallSquare, h - sizeSmallSquare, sizeSmallSquare, false);
        addItem(0, h - sizeSmallSquare, sizeSmallSquare, false);
        addItem((w - sizeBigSquare) / 2, (h - sizeBigSquare) / 2, sizeBigSquare, true);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        playAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }


    public class Item {
        int id;
        float left;
        float top;
        float size;
        int alpha;
        boolean center;
    }

    public int addItem(float x1, float y1, float size, boolean center) {
        Log.d(TAG, "_______________________addItem x = " + x1 + "; y = " + y1);
        Item it = new Item();
        it.left = x1;
        it.top = y1;
        it.size = size;
        it.center = center;
        it.alpha = 255;
        it.id = itemList.size() + 1;
        itemList.add(it);
        return itemList.size() - 1;
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "______________onDraw___________________");
        int i = 0;
        for (Item it : itemList) {

            paintForeground.setAlpha(it.alpha);
            canvas.drawRect(it.left, it.top, it.left + it.size, it.top + it.size, paintForeground);
            paintForeground.setAlpha(255);
            if (it.center) break;
            for (int k = i; k >= 0; k--) {
                drawLine(canvas, it, itemList.get(i - k));
            }

//            if (i > 0) drawLine(canvas, it, itemList.get(0));
//            if (i > 1) drawLine(canvas, it, itemList.get(i-1));
//            if (i > 2) drawLine(canvas, it, itemList.get(i-2));

            Log.d(TAG, "________________________onDraw it.top= " + it.top + "/ it.left" + it.left);
            i += 1;
        }
    }


    private void drawLine(Canvas canvas, Item itemStart, Item itemEnd) {
        float startX = itemStart.left + itemStart.size / 2;
        float startY = itemStart.top + itemStart.size / 2;
        float endX = itemEnd.left + itemEnd.size / 2;
        float endY = itemEnd.top + itemEnd.size / 2;
        canvas.drawLine(startX, startY, endX, endY, paintForeground);
    }
}
