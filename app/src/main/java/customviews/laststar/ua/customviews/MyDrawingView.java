package customviews.laststar.ua.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by User on 12.06.2015.
 */
public class MyDrawingView extends View implements View.OnClickListener{

    DrawingThread drawingThread;

    Paint p, pCircle, pShadow;
    Random random;
    RectF rect;
    boolean isFilled = false;

    float xPadding, yPadding;

    float progress;

    public MyDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        drawingThread = new DrawingThread();

        rect = new RectF(20, 20, 50, 50);
        random = new Random();
        progress = 0;
        setOnClickListener(this);
        initPaints();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

            canvas.drawCircle(rect.centerX() + 1f, rect.centerY() + 1f, (rect.width() / 2 + (float) Math.sqrt(progress)) + 1, pShadow);
            canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2 + (float) Math.sqrt(progress), pCircle);
            canvas.drawArc(rect, 0, progress, true, p);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        xPadding = MeasureSpec.getSize(widthMeasureSpec) * 0.2f;
        yPadding = MeasureSpec.getSize(heightMeasureSpec) * 0.2f;

        rect.set(getLeft() + xPadding, getTop() + yPadding, MeasureSpec.getSize(widthMeasureSpec) - xPadding, MeasureSpec.getSize(heightMeasureSpec) - yPadding);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }







    private void initPaints(){
        p = new Paint();
        p.setColor(Color.parseColor("#303F9F"));
        p.setStrokeWidth(10);
        p.setAntiAlias(true);
        pCircle = new Paint();
        pCircle.setColor(Color.parseColor("#FFC107"));
        pCircle.setStrokeWidth(5);
        pCircle.setAntiAlias(true);
        pCircle.setStyle(Paint.Style.FILL);
        pShadow = new Paint();
        pShadow.setColor(Color.parseColor("#212121"));
        pShadow.setStrokeWidth(5);
        pShadow.setAntiAlias(true);
        pShadow.setStyle(Paint.Style.FILL);
    }


    @Override
    public void onClick(View v) {

        if (!drawingThread.isAlive()) {
            drawingThread = new DrawingThread();
            drawingThread.start();
        }


    }


    protected class DrawingThread extends Thread{
        @Override
        public void run() {
            if (!isFilled) {
                while (progress <= 360) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                    progress++;
                }
            }else{
                while (progress >= 1) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                    progress--;
                }
            }
            isFilled = !isFilled;
        }
    }





}



