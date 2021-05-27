package br.com.herco.todoappmvp.modules.animation;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import br.com.herco.todoappmvp.R;

public class Circle extends View {

    private static final int START_ANGLE_POINT = 270;
    final int width = 110;
    final int height = 110;
    final int delta = 4;

    private final Paint paint;
    private final RectF rect;

    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 3;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setDither(true);
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        paint.setTypeface(typeface);


        //Circle color
        int color = ContextCompat.getColor(context, R.color.magenta_color);
        paint.setColor(color);


        rect = new RectF(5f, 5f, delta + width, delta + height);

        //Initial Angle (optional, it can be zero)
        angle = 20;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}