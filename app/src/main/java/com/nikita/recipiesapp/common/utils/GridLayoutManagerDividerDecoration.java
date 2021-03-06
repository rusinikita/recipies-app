package com.nikita.recipiesapp.common.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Adds interior dividers to a RecyclerView with a GridLayoutManager.
 */
public final class GridLayoutManagerDividerDecoration extends RecyclerView.ItemDecoration {

    private Drawable mHorizontalDivider;
    private Drawable mVerticalDivider;
    private int mNumColumns;

    /**
     * Sole constructor. Takes in {@link Drawable} objects to be used as
     * horizontal and vertical dividers.
     *
     * @param horizontalDivider A divider {@code Drawable} to be drawn on the
     *                          rows of the grid of the RecyclerView
     * @param verticalDivider A divider {@code Drawable} to be drawn on the
     *                        columns of the grid of the RecyclerView
     * @param numColumns The number of columns in the grid of the RecyclerView
     */
    public GridLayoutManagerDividerDecoration(Drawable horizontalDivider, Drawable verticalDivider, int numColumns) {
        mHorizontalDivider = horizontalDivider;
        mVerticalDivider = verticalDivider;
        mNumColumns = numColumns;
    }

    /**
     * Draws horizontal and/or vertical dividers onto the parent RecyclerView.
     *
     * @param canvas The {@link Canvas} onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     * @param state The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        drawHorizontalDividers(canvas, parent);
        drawVerticalDividers(canvas, parent);
    }

    /**
     * Determines the size and location of offsets between items in the parent
     * RecyclerView.
     *
     * @param outRect The {@link Rect} of offsets to be added around the child view
     * @param view The child view to be decorated with an offset
     * @param parent The RecyclerView onto which dividers are being added
     * @param state The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        boolean childIsInLeftmostColumn = (parent.getChildAdapterPosition(view) % mNumColumns) == 0;
        if (!childIsInLeftmostColumn) {
            outRect.left = mHorizontalDivider.getIntrinsicWidth();
        }

        boolean childIsInFirstRow = (parent.getChildAdapterPosition(view)) < mNumColumns;
        if (!childIsInFirstRow) {
            outRect.top = mVerticalDivider.getIntrinsicHeight();
        }
    }

    /**
     * Adds horizontal dividers to a RecyclerView with a GridLayoutManager or
     * its subclass.
     *
     * @param canvas The {@link Canvas} onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     */
    private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        if (childCount == 0) return;

        for (int i = 0; i < Math.min(mNumColumns, childCount); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentLeft = child.getRight() + params.rightMargin;
            int parentRight = parentLeft + mHorizontalDivider.getIntrinsicWidth();

            mHorizontalDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mHorizontalDivider.draw(canvas);
        }
    }

    /**
     * Adds vertical dividers to a RecyclerView with a GridLayoutManager or its
     * subclass.
     *
     * @param canvas The {@link Canvas} onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     */
    private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        int numChildrenOnLastRow = childCount % mNumColumns;
        int numRows = childCount / mNumColumns;
        if (numChildrenOnLastRow == 0) { // TODO: Replace this with math
            numRows--;
        }
        for (int i = 0; i < numRows; i++) {
            View child = parent.getChildAt(i * mNumColumns);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + mVerticalDivider.getIntrinsicHeight();

            mVerticalDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mVerticalDivider.draw(canvas);
        }
    }
}

