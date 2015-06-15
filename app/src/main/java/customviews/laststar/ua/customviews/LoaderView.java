package customviews.laststar.ua.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Andriy Biskup on 13.06.2015.
 */
public class LoaderView extends View {

    private DrawingThread drawingThread;
    private float xPadding, yPadding;
    private RectF rect;
    private int progress;

    private Paint pCircle, pShadow;

    private boolean indeterminate = true;

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawingThread = new DrawingThread();

        initPaints();
        initShapes();
    }



    private void initPaints() {
        pCircle = new Paint();
        pCircle.setColor(Color.parseColor("#FFC107"));
        pCircle.setStrokeWidth(2);
        pCircle.setAntiAlias(true);
        pCircle.setStyle(Paint.Style.FILL);
        pShadow = new Paint();
        pShadow.setColor(Color.parseColor("#212121"));
        pShadow.setStrokeWidth(2);
        pShadow.setAntiAlias(true);
        pShadow.setStyle(Paint.Style.FILL);


    }

    private void initShapes() {
        rect = new RectF(20, 20, 50, 50);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        xPadding = MeasureSpec.getSize(widthMeasureSpec) * 0.2f;
        yPadding = MeasureSpec.getSize(heightMeasureSpec) * 0.2f;

        rect.set(getLeft() + xPadding, getTop() + yPadding, MeasureSpec.getSize(widthMeasureSpec) - xPadding, MeasureSpec.getSize(heightMeasureSpec) - yPadding);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    protected class DrawingThread extends Thread{
        @Override
        public void run() {
            do {
                progress++;
                if (indeterminate | progress == 100) {
                    progress = 0;
                }
                invalidate();
            }while (progress<=100);
        }
    }



}
