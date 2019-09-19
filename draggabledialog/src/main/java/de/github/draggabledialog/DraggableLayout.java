package de.github.draggabledialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.customview.widget.ViewDragHelper;


class DraggableLayout extends LinearLayout {
	private ViewDragHelper mDragHelper;

	public DraggableLayout(Context context) {
		super(context);
		init();
	}
 
	public DraggableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
 
	public DraggableLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
    int mflag = 1;
	private void init() {
		/**
		 * @params ViewGroup forParent 必须是一个ViewGroup
		 * @params float sensitivity 灵敏度
		 * @params Callback cb 回调
		 */
		mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
	}
	Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
			if (mflag == 0 && callback != null){
				callback.onClick();
			}
        }
    };
	private class ViewDragCallback extends ViewDragHelper.Callback {
		/**
		 * 尝试捕获子view，一定要返回true
		 * @param view 尝试捕获的view
		 * @param pointerId pointerId 指示器id？
		 * 这里可以决定哪个子view可以拖动
		 */
		@Override
		public boolean tryCaptureView(View view, int pointerId) {
//			return mCanDragView == view;
			mflag ++;
			return true;
		}
		
		/**
		 * 处理水平方向上的拖动
		 * @param child child 被拖动到view
		 * @param  left 移动到达的x轴的距离
		 * @param  dx 建议的移动的x距离
		 */
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			System.out.println("left = " + left + ", dx = " + dx);
			
			// 两个if主要是为了让viewViewGroup里
			if(getPaddingLeft() > left) {
				return getPaddingLeft();
			}
			
			if(getWidth() - child.getWidth() < left) {
				return getWidth() - child.getWidth();
			}
			
			return left;
		}
		
		/**
		 *  处理竖直方向上的拖动
		 * @param  child 被拖动到view
		 * @param  top 移动到达的y轴的距离
		 * @param  dy 建议的移动的y距离
		 */
		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			// 两个if主要是为了让viewViewGroup里
			if(getPaddingTop() > top) {
				return getPaddingTop();
			}
			
			if(getHeight() - child.getHeight() < top) {
				return getHeight() - child.getHeight();
			}
			
			return top;
		}
		
		/**
		 * 当拖拽到状态改变时回调
		 * @params 新的状态
		 */
		@Override
		public void onViewDragStateChanged(int state) {
			switch (state) {
			case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
				break;
			case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
				break;
			case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
				break;
			}
			super.onViewDragStateChanged(state);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_DOWN:
			mDragHelper.cancel(); // 相当于调用 processTouchEvent收到ACTION_CANCEL
			break;
		}
		mflag = 0 ;
		handler.sendEmptyMessageDelayed( 0 , 150 );
		/**
		 * 检查是否可以拦截touch事件
		 * 如果onInterceptTouchEvent可以return true 则这里return true
		 */
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/**
		 * 处理拦截到的事件
		 * 这个方法会在返回前分发事件
		 */
		mDragHelper.processTouchEvent(event);

		return true;
	}

    public void setBackgroundClick(OnBackgroundClick callback) {
        this.callback = callback;
    }

    OnBackgroundClick callback;
	public interface OnBackgroundClick{
       void  onClick();
    }
}
 