
package mmustapha.g_hub.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import mmustapha.g_hub.R;


/**
 * @author Okeke Paul
 * Created by paulex on 7/3/16.
 *
 * Util Class for rendering images
 */
public class RoundedCornerImage extends android.support.v7.widget.AppCompatImageView {

    private int vBorderWidth;

    public RoundedCornerImage(Context context) {
        super(context);
        initDrawingTools();
    }

    public RoundedCornerImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawingTools();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RoundedCornerImage,0,0);
        try{
            vBorderWidth = typedArray.getDimensionPixelSize(R.styleable.RoundedCornerImage_borderWidth, 0);
        }finally {
            typedArray.recycle();
        }
    }

    public RoundedCornerImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawingTools();
    }

    private void initDrawingTools(){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if(drawable == null) return;
        if(getWidth()==0 || getHeight()==0) return;

        Bitmap srcBitmap = ((BitmapDrawable) drawable).getBitmap();

        Bitmap roundBitmap = getRoundedCornerBitmap(srcBitmap,
                getWidth(), getHeight(), vBorderWidth);

        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }


    public Bitmap  getRoundedCornerBitmap(Bitmap bitmap, int width, int height, int borderWidth){
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Bitmap canvasLayer = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint vPaint = new Paint(Paint.DITHER_FLAG);
        vPaint.isAntiAlias();
        vPaint.setShader(shader);

        Canvas canvas = new Canvas(canvasLayer);
        float radius;
        if (w > h){
            radius = (float)h / 2f;
        } else radius = (float)w / 2f;

        canvas.drawCircle((float)(w / 2), (float)(h / 2), radius, vPaint);

        vPaint.setShader(null);
        vPaint.setStyle(Paint.Style.STROKE);
        vPaint.setColor(Color.parseColor("#E0E0E0"));
        vPaint.setStrokeWidth((float)borderWidth);
        //
        canvas.drawCircle((float)(w / 2), (float)(h / 2), radius - borderWidth / 2, vPaint);
        return canvasLayer;
    }

}
