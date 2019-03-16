package io.potter.partum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

class BadgedDrawerArrowDrawable extends DrawerArrowDrawable {
    public BadgedDrawerArrowDrawable(Context context) {
        super(context);

        setColor(context.getResources().getColor(R.color.colorEdt));
    }
}
