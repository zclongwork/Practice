package com.zcl.practice.layoutManager;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ZoomLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "ZoomLayoutManager";

    /**
     * 一次完整的聚焦滑动所需要的移动距离
     */
    private float onceCompleteScrollLength = -1;

    /**
     * 第一个子view的偏移量
     */
    private float firstChildCompleteScrollLength = -1;

    /**
     * 屏幕可见第一个view的position
     */
    private int mFirstVisiPos;

    /**
     * 屏幕可见的最后一个view的position
     */
    private int mLastVisiPos;

    /**
     * 水平方向累计偏移量
     */
//    private long mHorizontalOffset;
    private long mVerticalOffset;

    /**
     * 普通view之间的margin
     */
    private float normalViewGap = 30;

    private int childWidth = 0;
    private int childHeight = 0;

    /**
     * 是否自动选中
     */
    private boolean isAutoSelect = true;
    private ValueAnimator selectAnimator;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    public ZoomLayoutManager(Context context, int gap) {
        normalViewGap = dp2px(context, gap);
    }

    public ZoomLayoutManager(Context context) {
        this(context, 0);
    }

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //  super.onLayoutChildren(recycler, state);
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        onceCompleteScrollLength = -1;

        // 分离全部已有的view 放入临时缓存  mAttachedScrap 集合中
        detachAndScrapAttachedViews(recycler);

        fill(recycler, state, 0);
    }

    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        int resultDelta = dy;
        resultDelta = fillVerticalLeft(recycler, state, dy);
        recycleChildren(recycler);
        return resultDelta;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }

        float realDx = dy / 1.0f;
        if (Math.abs(realDx) < 0.00000001f) {
            return 0;
        }

        mVerticalOffset += dy;

        dy = fill(recycler, state, dy);


        return dy;
    }

    /**
     * 最大偏移量
     *
     * @return
     */
    private float getMaxOffset() {
        if (childHeight == 0 || getItemCount() == 0) return 0;
        // getWidth() / 2 + childWidth / 2 +
        return (childHeight/2 + normalViewGap) * (getItemCount() - 1);
    }

    /**
     * 获取最小的偏移量
     *
     * @return
     */
    private float getMinOffset() {
        if (childHeight == 0) return 0;
        return (getHeight() - childHeight) / 2;
    }

    private int fillVerticalLeft(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        //----------------1、边界检测-----------------
        if (dy < 0) {
            // 已到达上边界
            if (mVerticalOffset < 0) {
                mVerticalOffset = dy = 0;
            }
        }

        //由下向上滑动
        if (dy > 0) {
            if (mVerticalOffset >= getMaxOffset()) {
                mVerticalOffset = (long) getMaxOffset();
                dy = 0;
            }
        }


        // 分离全部的view，加入到临时缓存
        detachAndScrapAttachedViews(recycler);

        float startX = 0;
        float startY = 0;
        float fraction = 0f;

        View tempView = null;
        int tempPosition = -1;

        if (onceCompleteScrollLength == -1) {
            // 因为mFirstVisiPos在下面可能被改变，所以用tempPosition暂存一下
            tempPosition = mFirstVisiPos;
            tempView = recycler.getViewForPosition(tempPosition);
            measureChildWithMargins(tempView, 0, 0);
            childWidth = getDecoratedMeasurementHorizontal(tempView);
            childHeight = getDecoratedMeasurementVertical(tempView);
        }

        // 修正第一个可见view mFirstVisiPos 已经滑动了多少个完整的onceCompleteScrollLength就代表滑动了多少个item
        firstChildCompleteScrollLength = getHeight() / 2 + childHeight / 2;
        if (mVerticalOffset >= firstChildCompleteScrollLength) {
            startX = normalViewGap;
            onceCompleteScrollLength = childHeight + normalViewGap;
            mFirstVisiPos = (int) Math.floor(Math.abs(mVerticalOffset - firstChildCompleteScrollLength) / onceCompleteScrollLength) + 1;
            fraction = (Math.abs(mVerticalOffset - firstChildCompleteScrollLength) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        } else {
            mFirstVisiPos = 0;
            startX = getMinOffset();
            onceCompleteScrollLength = firstChildCompleteScrollLength;
            fraction = (Math.abs(mVerticalOffset) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        }

        // 临时将mLastVisiPos赋值为getItemCount() - 1，放心，下面遍历时会判断view是否已溢出屏幕，并及时修正该值并结束布局
        mLastVisiPos = getItemCount() - 1;

        float normalViewOffset = onceCompleteScrollLength * fraction;
        boolean isNormalViewOffsetSetted = false;

        //----------------3、开始布局-----------------
        for (int i = mFirstVisiPos; i <= mLastVisiPos; i++) {
//            Log.i(TAG, "布局 for i=" + i);

            View item;
            if (i == tempPosition && tempView != null) {
                // 如果初始化数据时已经取了一个临时view
                item = tempView;
            } else {
                item = recycler.getViewForPosition(i);
            }

            int focusPosition = (int) (Math.abs(mVerticalOffset) / (childWidth + normalViewGap));
            if (i <= focusPosition) {
                addView(item);
            } else {
                addView(item, 0);
            }
            measureChildWithMargins(item, 0, 0);

            if (!isNormalViewOffsetSetted) {
                startY += normalViewOffset;
                isNormalViewOffsetSetted = true;
            }

            int l, t, r, b;
//            l = (int) startX;
            l = 0;
            t = (int) startY;
            r = l + getDecoratedMeasurementHorizontal(item);
            b = t + getDecoratedMeasurementVertical(item);

            // 缩放子view
            final float minScale = 0.6f;
            float currentScale = 0f;

            final float fractionScale = Math.abs(mVerticalOffset) / (getMaxOffset() * 1.0f);
            currentScale = 1.0f - (1.0f - minScale) * fractionScale;

//            Log.i(TAG, String.format("currentScale=%s l=%s t=%s r=%s b=%s dy=%s", currentScale , l , t, r, b, dy));

            item.setScaleX(currentScale);
            item.setScaleY(currentScale);
            // item.setAlpha(currentScale);

            layoutDecoratedWithMargins(item, l, t, r, b);

            startX += (childWidth + normalViewGap);
            startY += (childHeight + normalViewGap);
        }

        return dy;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //当手指按下时，停止当前正在播放的动画
                cancelAnimator();
                break;
            case RecyclerView.SCROLL_STATE_IDLE:

//                mVerticalOffset = 0;


                //当列表滚动停止后，判断一下自动选中是否打开
                if (isAutoSelect) {
                    //找到离目标落点最近的item索引
//                    smoothScrollToPosition(findShouldSelectPosition(), null);
                }
                break;
            default:
                break;
        }
    }

//    public int findShouldSelectPosition() {
//        if (onceCompleteScrollLength == -1 || mFirstVisiPos == -1) {
//            return -1;
//        }
//        int position = (int) (Math.abs(mHorizontalOffset) / (childWidth + normalViewGap));
//        int remainder = (int) (Math.abs(mHorizontalOffset) % (childWidth + normalViewGap));
//        // 超过一半，应当选中下一项
//        if (remainder >= (childWidth + normalViewGap) / 2.0f) {
//            if (position + 1 <= getItemCount() - 1) {
//                return position + 1;
//            }
//        }
//        return position;
//    }

    /**
     * 平滑滚动到某个位置
     *
     * @param position 目标Item索引
     */
    public void smoothScrollToPosition(int position, StackLayoutManager.OnStackListener listener) {
        if (position > -1 && position < getItemCount()) {
            startValueAnimator(position, listener);
        }
    }

    private void startValueAnimator(int position, final StackLayoutManager.OnStackListener listener) {
//        cancelAnimator();
//
//        final float distance = getScrollToPositionOffset(position);
//
//        long minDuration = 100;
//        long maxDuration = 300;
//        long duration;
//
//        float distanceFraction = (Math.abs(distance) / (childWidth + normalViewGap));
//
//        if (distance <= (childWidth + normalViewGap)) {
//            duration = (long) (minDuration + (maxDuration - minDuration) * distanceFraction);
//        } else {
//            duration = (long) (maxDuration * distanceFraction);
//        }
//        selectAnimator = ValueAnimator.ofFloat(0.0f, distance);
//        selectAnimator.setDuration(duration);
//        selectAnimator.setInterpolator(new LinearInterpolator());
//        final float startedOffset = mHorizontalOffset;
//        selectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (float) animation.getAnimatedValue();
//                mHorizontalOffset = (long) (startedOffset + value);
//                requestLayout();
//            }
//        });
//        selectAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                if (listener != null) {
//                    listener.onFocusAnimEnd();
//                }
//            }
//        });
//        selectAnimator.start();
    }

    /**
     * @param position
     * @return
     */
    private float getScrollToPositionOffset(int position) {
//        return position * (childWidth + normalViewGap) - Math.abs(mHorizontalOffset);
        return 0;
    }

    /**
     * 取消动画
     */
    public void cancelAnimator() {
        if (selectAnimator != null && (selectAnimator.isStarted() || selectAnimator.isRunning())) {
            selectAnimator.cancel();
        }
    }

    /**
     * 回收需回收的item
     */
    private void recycleChildren(RecyclerView.Recycler recycler) {
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for (int i = 0; i < scrapList.size(); i++) {
            RecyclerView.ViewHolder holder = scrapList.get(i);
            removeAndRecycleView(holder.itemView, recycler);
        }
    }

    /**
     * 获取某个childView在水平方向所占的空间，将margin考虑进去
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间,将margin考虑进去
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public interface OnStackListener {
        void onFocusAnimEnd();
    }
}
