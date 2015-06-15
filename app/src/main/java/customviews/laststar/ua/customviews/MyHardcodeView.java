package customviews.laststar.ua.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by User on 11.06.2015.
 */
public class MyHardcodeView extends View {

    private int shapeColor;
    private boolean displayShapeName;
    private int shapeWidth = 100;
    private int shapeHeight = 100;
    private int textXOffset = 0;
    private int textYOffset = 30;
    private Paint paintShape;
    private String[] shapeValues = { "square", "circle", "triangle" };
    private int currentShapeIndex = 0;


    public MyHardcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        setupPaint();
    }



    private void setupAttributes(AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MyHardcodeView, 0, 0);
        // Extract custom attributes into member variables
        try {
            shapeColor = a.getColor(R.styleable.MyHardcodeView_shapeColor, Color.BLACK);
            displayShapeName = a.getBoolean(R.styleable.MyHardcodeView_displayShapeName, false);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }

    public boolean isDisplayingShapeName() {
        return displayShapeName;
    }

    public void setDisplayingShapeName(boolean state) {
        this.displayShapeName = state;
        invalidate();
        requestLayout();
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int color) {
        this.shapeColor = color;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, shapeWidth, shapeHeight, paintShape);
        if (displayShapeName) {
            canvas.drawText("Square", shapeWidth + textXOffset, shapeHeight + textXOffset, paintShape);
        }
    }

    private void setupPaint() {
        paintShape = new Paint();
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(shapeColor);
        paintShape.setTextSize(30);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Defines the extra padding for the shape name text
        int textPadding = 10;
        int contentWidth = shapeWidth;

        // Resolve the width based on our minimum and the measure spec
        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        // Ask for a height that would let the view get as big as it can
        int minh = shapeHeight + getPaddingBottom() + getPaddingTop();
        if (displayShapeName) {
            minh += textYOffset + textPadding;
        }
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        // Calling this method determines the measured width and height
        // Retrieve with getMeasuredWidth or getMeasuredHeight methods later
        setMeasuredDimension(w, h);
    }


    // Change the currentShapeIndex whenever the shape is clicked
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentShapeIndex ++;
            if (currentShapeIndex > (shapeValues.length - 1)) {
                currentShapeIndex = 0;
            }
            postInvalidate();
            return true;
        }
        return result;
    }


}
