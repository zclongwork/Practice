package com.zcl.practice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zcl.practice.R;
import com.zcl.practice.SystemUtil;

import java.util.Calendar;

public class CustomAnalogClock extends View {

    private Paint paint; // 画笔
    private Calendar calendar; // 日历对象，用于获取当前时间
    private float centerX, centerY; // 时钟中心点坐标
    private float radius; // 时钟半径
    private float hourHandLength, minuteHandLength, secondHandLength; // 时针、分针、秒针长度
    private int mColor;

    public CustomAnalogClock(Context context) {
        this(context, null);
    }

    public CustomAnalogClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomAnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomAnalogClock, defStyleAttr,0);
        mColor = a.getColor(R.styleable.CustomAnalogClock_textColor, Color.BLACK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveAttributeDataForStyleable(context, R.styleable.CustomAnalogClock, attrs, a, defStyleAttr, 0);
        }
        a.recycle();

        paint = new Paint();
        calendar = Calendar.getInstance();

        // 设置画笔的默认属性
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStrokeCap(Paint.Cap.ROUND); // 圆形笔触
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 计算时钟中心点和半径 支持padding设置
        centerX = (w - getPaddingLeft() - getPaddingRight()) / 2f + getPaddingLeft();
        centerY = (h - getPaddingTop() - getPaddingBottom()) / 2f + getPaddingTop();
        radius = Math.min(centerX-getPaddingLeft(), centerY-getPaddingTop()) - 10; // 留出一些边距

        // 设置指针长度
        hourHandLength = radius * 0.5f;
        minuteHandLength = radius * 0.7f;
        secondHandLength = radius * 0.9f;
    }


    // 设置wrap_content的默认宽 / 高值 支持wrap_content
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = SystemUtil.dp2px(getContext(), 100);
        int mHeight = mWidth;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 每次绘制前更新时间
        calendar = Calendar.getInstance();
        // 绘制表盘
        drawClockFace(canvas);
        // 绘制时针
        drawHourHand(canvas);
        // 绘制分针
        drawMinuteHand(canvas);
        // 绘制秒针
        drawSecondHand(canvas);
        // 绘制中心点
        drawCenterPoint(canvas);
        // 每秒刷新一次
        postInvalidateDelayed(1000);
    }

    private void drawClockFace(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mColor);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, paint);

        // 绘制刻度
        paint.setStrokeWidth(1);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                paint.setStrokeWidth(2); // 整点刻度较粗
                paint.setTextSize(30);
                int result;
                if (i / 5 == 0) {
                    result = 12;
                } else {
                    result = i / 5;
                }
                String text = String.valueOf(result);
                Rect rect = new Rect();
                paint.getTextBounds(text, 0, text.length(), rect);
                float textWidth = rect.width();
                float textHeight = rect.height();
                canvas.drawText(text, centerX + (radius - 40) * (float) Math.cos(Math.toRadians(i * 6 - 90)) - textWidth / 2,
                        centerY + (radius - 40) * (float) Math.sin(Math.toRadians(i * 6 - 90)) + textHeight / 2, paint);
            } else {
                paint.setStrokeWidth(1); // 非整点刻度较细
            }
            canvas.drawLine(centerX + radius * (float) Math.cos(Math.toRadians(i * 6 - 90)),
                    centerY + radius * (float) Math.sin(Math.toRadians(i * 6 - 90)),
                    centerX + (radius - 10) * (float) Math.cos(Math.toRadians(i * 6 - 90)),
                    centerY + (radius - 10) * (float) Math.sin(Math.toRadians(i * 6 - 90)), paint);
        }
    }

    private void drawHourHand(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);

        float hourAngle = (calendar.get(Calendar.HOUR) % 12 + calendar.get(Calendar.MINUTE) / 60f) * 30 - 90;
        float hourX = centerX + hourHandLength * (float) Math.cos(Math.toRadians(hourAngle));
        float hourY = centerY + hourHandLength * (float) Math.sin(Math.toRadians(hourAngle));
        canvas.drawLine(centerX, centerY, hourX, hourY, paint);
    }

    private void drawMinuteHand(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);

        float minuteAngle = calendar.get(Calendar.MINUTE) * 6 - 90;
        float minuteX = centerX + minuteHandLength * (float) Math.cos(Math.toRadians(minuteAngle));
        float minuteY = centerY + minuteHandLength * (float) Math.sin(Math.toRadians(minuteAngle));
        canvas.drawLine(centerX, centerY, minuteX, minuteY, paint);
    }

    private void drawSecondHand(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);

        float secondAngle = calendar.get(Calendar.SECOND) * 6 - 90;
        float secondX = centerX + secondHandLength * (float) Math.cos(Math.toRadians(secondAngle));
        float secondY = centerY + secondHandLength * (float) Math.sin(Math.toRadians(secondAngle));
        canvas.drawLine(centerX, centerY, secondX, secondY, paint);
    }

    private void drawCenterPoint(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(centerX, centerY, 5, paint);
    }
}