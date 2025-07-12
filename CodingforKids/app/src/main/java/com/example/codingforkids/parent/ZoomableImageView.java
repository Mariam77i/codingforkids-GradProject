package com.example.codingforkids.parent;

import static com.bumptech.glide.Glide.init;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ZoomableImageView extends AppCompatImageView {
    private Matrix matrix = new Matrix();
    private float scale = 1f;
    private ScaleGestureDetector detector;

    public ZoomableImageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ZoomableImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomableImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(Context context) {
        detector = new ScaleGestureDetector(context, new ScaleListener());
        setScaleType(ScaleType.MATRIX);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(1f, Math.min(scale, 5f));
            matrix.setScale(scale, scale, detector.getFocusX(), detector.getFocusY());
            setImageMatrix(matrix); return true;
        }
    }
}
