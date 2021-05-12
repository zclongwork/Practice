package com.zcl.practice.layoutManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

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
        return childHeight / 2f + normalViewGap;
    }

    /**
     * 获取最小的偏移量
     *
     * @return
     */
    private float getMinOffset() {
        if (childHeight == 0) return 0;
        return -childHeight / 2f - normalViewGap;
    }

    private int fillVerticalLeft(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        //----------------1、边界检测-----------------
        if (dy < 0) {
            // 已到达上边界
            if (mVerticalOffset < getMinOffset()) {
                mVerticalOffset = dy = (int) getMinOffset();
            }
        }

        //由下向上滑动
        if (dy > 0) {
            if (mVerticalOffset >= getMaxOffset()) {
                mVerticalOffset = (long) getMaxOffset();
                dy = 0;
            }
        }

        Log.d(TAG, "mVerticalOffset=" + mVerticalOffset);

        // 分离全部的view，加入到临时缓存
        detachAndScrapAttachedViews(recycler);

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
            onceCompleteScrollLength = childHeight + normalViewGap;
            mFirstVisiPos = (int) Math.floor(Math.abs(mVerticalOffset - firstChildCompleteScrollLength) / onceCompleteScrollLength) + 1;
            fraction = (Math.abs(mVerticalOffset - firstChildCompleteScrollLength) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        } else {
            mFirstVisiPos = 0;
            onceCompleteScrollLength = firstChildCompleteScrollLength;
            fraction = (Math.abs(mVerticalOffset) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        }

        // 临时将mLastVisiPos赋值为getItemCount() - 1，放心，下面遍历时会判断view是否已溢出屏幕，并及时修正该值并结束布局
        mLastVisiPos = getItemCount() - 1;

        float normalViewOffset = onceCompleteScrollLength * fraction;
//        Log.i(TAG, String.format("normalViewOffset=%s onceCompleteScrollLength=%s fraction=%s mVerticalOffset=%s",
//                normalViewOffset, onceCompleteScrollLength, fraction, mVerticalOffset));
        boolean isNormalViewOffsetSetted = false;

        //----------------3、开始布局-----------------
        for (int i = mFirstVisiPos; i <= mLastVisiPos; i++) {
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

//            if (!isNormalViewOffsetSetted) {
//                if (mVerticalOffset < 0) {
//                    startY += normalViewOffset/2;
//                } else {
//                    startY -= normalViewOffset* (1f-minScale);
//                }
////                Log.e(TAG, String.format("isNormalViewOffsetSetted startY=%s i=%s", startY, i));
//                isNormalViewOffsetSetted = true;
//            }

            float currentScale = 0f;

            final float fractionScale = Math.abs(mVerticalOffset) / (getMaxOffset() * 1.0f);

            if (mVerticalOffset < 0) {
                if (i == 0) {
                    currentScale = defaultScale + (maxScale - defaultScale) * fractionScale;
                } else {
                    currentScale = defaultScale - (defaultScale - minScale) * fractionScale;
                }
            } else {
                if (i == 0) {
                    currentScale = defaultScale - (defaultScale - minScale) * fractionScale;
                } else {
                    currentScale = defaultScale + (maxScale - defaultScale) * fractionScale;
                }
            }

            int l, t, r, b;
            l = 0;
            t = (int) startY;
            r = l + getDecoratedMeasurementHorizontal(item);
            b = t + getDecoratedMeasurementVertical(item);

//            Log.i(TAG, String.format("currentScale=%s l=%s t=%s r=%s b=%s dy=%s", currentScale , l , t, r, b, dy));

            item.setScaleX(currentScale);
            item.setScaleY(currentScale);

            if (currentScale < defaultScale) {
                float alpha = 1 - (defaultScale - currentScale) / (2f * (defaultScale - minScale));
                item.setAlpha(alpha);
            } else {
                item.setAlpha(1);
            }

            layoutDecoratedWithMargins(item, l, t, r, b);

            startY += (childHeight + normalViewGap);
        }

        return dy;
    }

    // 缩放子view
    final float minScale = 0.8f;
    final float maxScale = 1f;

    final float defaultScale = 0.9f;

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //当手指按下时，停止当前正在播放的动画
                cancelAnimator();
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                //当列表滚动停止后，判断一下自动选中是否打开
                if (isAutoSelect) {
                    //找到离目标落点最近的item索引
                    smoothScrollToPosition(findBigPosition(), null);
                }
                break;
            default:
                break;
        }
    }

    public int findBigPosition() {
        if (onceCompleteScrollLength == -1 || mFirstVisiPos == -1) {
            Log.w(TAG, String.format("find pos=-1 mVerticalOffset=%s" , mVerticalOffset));
            return -1;
        }
        int position = 0;
        if (mVerticalOffset > 0) {
            position = 1;
        }
        Log.i(TAG, String.format("find pos=%s mVerticalOffset=%s" , position, mVerticalOffset));
        return position;
    }

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
        cancelAnimator();

        final float distance = getScrollToPositionOffset(position);

        long minDuration = 100;
        long maxDuration = 300;
        long duration;

        float distanceFraction = (Math.abs(distance) / (childHeight + normalViewGap));

        if (distance <= (childHeight + normalViewGap)) {
            duration = (long) (minDuration + (maxDuration - minDuration) * distanceFraction);
        } else {
            duration = (long) (maxDuration * distanceFraction);
        }
        selectAnimator = ValueAnimator.ofFloat(0.0f, distance);
        selectAnimator.setDuration(duration);
        selectAnimator.setInterpolator(new LinearInterpolator());
        final float startedOffset = mVerticalOffset;
        selectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mVerticalOffset = (long) (startedOffset + value);
                requestLayout();
            }
        });
        selectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onFocusAnimEnd();
                }
            }
        });
        selectAnimator.start();
    }

    /**
     * @param position
     * @return
     */
    private float getScrollToPositionOffset(int position) {
        float offset;
        if (position == 1) {
            offset = getMaxOffset() -  mVerticalOffset;
        } else {
            offset = getMinOffset() - mVerticalOffset;
        }


        Log.i(TAG, position + " mVerticalOffset=" + mVerticalOffset + " offset=" + offset);
//        Log.i(TAG, position + " offset=" + offset);
        return offset;
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
     * 状态回归
     */
    public void rollBack() {
        mVerticalOffset = 0;
        requestLayout();
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
