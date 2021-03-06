package com.chenzexuan.xmlcustomviewlib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chenzexuan on 2017/1/12.
 */


/**
 * 使用方法：
 * 方法1、在xml文件中通过 app:xmlCustomView_... 的方式设置各种样式
 * <p>
 * 例：
 * <com.chenzexuan.xmlcustomviewlib.XmlCustomTextView
 * android:id="@+id/text"
 * android:layout_width="match_parent"
 * android:layout_height="40dp"
 * android:layout_margin="20dp"
 * android:text="HELLO WORLD"
 * android:textSize="18dp"
 * app:xmlCustomView_background_color="#0070FF"
 * app:xmlCustomView_background_disable_alpha="51"
 * app:xmlCustomView_radius="14dp"
 * app:xmlCustomView_text_color="#FFFFFF"
 * app:xmlCustomView_text_pressed_color="#4cffffff"
 * />
 * <p>
 * 方法2、使用XmlCustomView的updateXmlCustomViewParams(XmlCustomViewParams viewParams)方法在java代码中组装viewParams后更新
 * <p>
 * 例：
 * XmlCustomViewParams params = new XmlCustomViewParams();
 * params.setRawTextColor(getResources().getColor(android.R.color.black));
 * params.setSelectedTextColor(getResources().getColor(android.R.color.holo_red_dark));
 * XmlCustomTextView textView = new XmlCustomTextView(context, params);
 * textView.updateXmlCustomViewParams(params);
 * <p>
 * 可设置控件背景色（正常状态、按下状态、disable状态、聚焦状态） 只可设置纯色
 * <p>
 * 可设置控件背景drawable（正常状态、按下状态、disable状态、聚焦状态）  drawable暂未支持同时设置圆角 todo
 * <p>
 * 可设置控件背景alpha值（正常状态、按下状态、disable状态、聚焦状态）
 * <p>
 * 可设置控件文字色 （正常状态、按下状态、disable状态、聚焦状态）
 * <p>
 * 可设置控件描边颜色及宽度
 * <p>
 * 可设置控件圆角
 * <p>
 * 以上设置可以叠加  例如：同时设置 背景色蓝色，描边白色，5dp圆角，按下后有阴影等效果
 */
public class XmlCustomViewHelper {

    public static XmlCustomViewParams buildXmlCustomViewParams(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.XmlCustomView, 0, 0);
        XmlCustomViewParams viewParams = new XmlCustomViewParams();

        viewParams.setRawBackgroundColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_background_color, XmlCustomViewParams.DEFAULT_COLOR));
        viewParams.setRawBackgroundDrawable(attributes.getDrawable(R.styleable.XmlCustomView_xmlCustomView_background_resource));
        viewParams.setRawBackgroundAlpha(attributes.getInt(R.styleable.XmlCustomView_xmlCustomView_background_alpha, XmlCustomViewParams.DEFAULT_ALPHA));

        viewParams.setPressedBackgroundColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_background_pressed_color, XmlCustomViewParams.DEFAULT_COLOR));
        viewParams.setPressedBackgroundDrawable(attributes.getDrawable(R.styleable.XmlCustomView_xmlCustomView_background_pressed_resource));
        viewParams.setPressedBackgroundAlpha(attributes.getInt(R.styleable.XmlCustomView_xmlCustomView_background_pressed_alpha, XmlCustomViewParams.DEFAULT_PRESS_ALPHA));

        viewParams.setSelectedBackgroundColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_background_selected_color, XmlCustomViewParams.DEFAULT_COLOR));
        viewParams.setSelectedBackgroundDrawable(attributes.getDrawable(R.styleable.XmlCustomView_xmlCustomView_background_selected_resource));
        viewParams.setSelectedBackgroundAlpha(attributes.getInt(R.styleable.XmlCustomView_xmlCustomView_background_selected_alpha, XmlCustomViewParams.DEFAULT_ALPHA));

        viewParams.setFocusedBackgroundColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_background_focused_color, XmlCustomViewParams.DEFAULT_COLOR));
        viewParams.setFocusedBackgroundDrawable(attributes.getDrawable(R.styleable.XmlCustomView_xmlCustomView_background_focused_resource));
        viewParams.setFocusedBackgroundAlpha(attributes.getInt(R.styleable.XmlCustomView_xmlCustomView_background_focused_alpha, XmlCustomViewParams.DEFAULT_ALPHA));

        viewParams.setDisableBackgroundColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_background_disable_color, XmlCustomViewParams.DEFAULT_COLOR));
        viewParams.setDisableBackgroundDrawable(attributes.getDrawable(R.styleable.XmlCustomView_xmlCustomView_background_disable_resource));
        viewParams.setDisableBackgroundAlpha(attributes.getInt(R.styleable.XmlCustomView_xmlCustomView_background_disable_alpha, XmlCustomViewParams.DEFAULT_ALPHA));

        setCornerRadiusParams(viewParams, attributes);

        setStrokeParams(viewParams, attributes);

        viewParams.setMaskAlpha(attributes.getInt(R.styleable.XmlCustomView_xmlCustomView_alpha, XmlCustomViewParams.DEFAULT_ALPHA));

        viewParams.setRawTextColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_text_color, 0));

        viewParams.setPressedTextColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_text_pressed_color, 0));

        viewParams.setDisableTextColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_text_disable_color, 0));

        viewParams.setFocusedTextColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_text_focused_color, 0));

        viewParams.setSelectedTextColor(attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_text_selected_color, 0));

        attributes.recycle();
        return viewParams;
    }

    private static void setStrokeParams(XmlCustomViewParams viewParams, TypedArray attributes) {
        viewParams.setStrokeWidth(attributes.getDimension(R.styleable.XmlCustomView_xmlCustomView_stroke_width, XmlCustomViewParams.DEFAULT_SIZE));
        int strokeColor = attributes.getColor(R.styleable.XmlCustomView_xmlCustomView_stroke_color, XmlCustomViewParams.DEFAULT_COLOR);
        viewParams.setStrokeColor(strokeColor);
    }

    public static Drawable buildBackgroundDrawable(XmlCustomViewParams viewParams) {
        Drawable rawBackgroundDrawable;
        rawBackgroundDrawable = processingDrawable(viewParams, viewParams.getRawBackgroundColor(), viewParams.getRawBackgroundDrawable(), 0);

        Drawable pressBackgroundDrawable;
        // 获取透明度、颜色及资源文件
        // 若未设置按钮状态对应的资源文件,则使用默认状态资源文件
        // 加工背景图片
        pressBackgroundDrawable = processingDrawable(viewParams,
                viewParams.getPressedBackgroundColor(),
                viewParams.getPressedBackgroundDrawable(),
                viewParams.getPressedBackgroundAlpha());

        Drawable focusBackgroundDrawable;
        // 获取透明度、颜色及资源文件
        // 若未设置按钮状态对应的资源文件,则使用默认状态资源文件
        // 加工背景图片
        focusBackgroundDrawable = processingDrawable(viewParams,
                viewParams.getFocusedBackgroundColor(),
                viewParams.getFocusedBackgroundDrawable(),
                viewParams.getFocusedBackgroundAlpha());

        Drawable disableBackgroundDrawable;
        // 获取透明度、颜色及资源文件
        // 若未设置按钮状态对应的资源文件,则使用默认状态资源文件

        // 加工背景图片
        disableBackgroundDrawable = processingDrawable(viewParams,
                viewParams.getDisableBackgroundColor(),
                viewParams.getDisableBackgroundDrawable(),
                viewParams.getDisableBackgroundAlpha());

        Drawable selectedBackgroundDrawable;
        // 获取透明度、颜色及资源文件
        // 若未设置按钮状态对应的资源文件,则使用默认状态资源文件

        // 加工背景图片
        selectedBackgroundDrawable = processingDrawable(viewParams,
                viewParams.getSelectedBackgroundColor(),
                viewParams.getSelectedBackgroundDrawable(),
                viewParams.getSelectedBackgroundAlpha());

        if (rawBackgroundDrawable == null && pressBackgroundDrawable == null
                && focusBackgroundDrawable == null
                && disableBackgroundDrawable == null
                && selectedBackgroundDrawable == null) {
            return null;
        }


        int pressed = android.R.attr.state_pressed;
        int focused = android.R.attr.state_focused;
        int selected = android.R.attr.state_selected;
        int disable = -android.R.attr.state_enabled;
        StateListDrawable background = new StateListDrawable();
        // 范围大的放前面,范围小的放后面
        background.addState(new int[]{pressed}, pressBackgroundDrawable);
        background.addState(new int[]{focused}, focusBackgroundDrawable);
        background.addState(new int[]{selected}, selectedBackgroundDrawable);
        background.addState(new int[]{disable}, disableBackgroundDrawable);
        background.addState(new int[]{}, rawBackgroundDrawable);

        return background;
    }

    /**
     * 加工背景图片
     * <p>
     * 工序:
     * 1、优先使用颜色值来创建背景图片
     * 2、处理圆角
     * 3、处理描边
     * 4、添加日间、夜间模式黑色半透明遮罩
     * 5、添加各状态设定的黑色半透明遮罩
     *
     * @return
     */
    private static Drawable processingDrawable(XmlCustomViewParams viewParams, int rawBackgroundColor, Drawable rawBackgroundDrawable, int rawBackgroundAlpha) {
        Drawable background;
        if (rawBackgroundColor != 0) {
            background = addCorners(viewParams, rawBackgroundColor);
        } else if (rawBackgroundDrawable != null) {
            background = addCorners(viewParams, rawBackgroundDrawable);
        } else if (viewParams.getRawBackgroundColor() != 0) {
            background = addCorners(viewParams, viewParams.getRawBackgroundColor());
        } else if (viewParams.getRawBackgroundDrawable() != null) {
            background = addCorners(viewParams, viewParams.getRawBackgroundDrawable());
        } else {
            background = null;
        }
        background = addStroke(viewParams, background);
        background = addThemeMask(viewParams, background);
        background = addStateMask(viewParams, background, rawBackgroundAlpha);
        return background;
    }

    private static void setCornerRadiusParams(XmlCustomViewParams viewParams, TypedArray attributes) {
        float radius = attributes.getDimension(R.styleable.XmlCustomView_xmlCustomView_radius, 0);
        float radiusLeftTop = attributes.getDimension(R.styleable.XmlCustomView_xmlCustomView_radius_left_top, radius);
        float radiusLeftBottom = attributes.getDimension(R.styleable.XmlCustomView_xmlCustomView_radius_left_bottom, radius);
        float radiusRightTop = attributes.getDimension(R.styleable.XmlCustomView_xmlCustomView_radius_right_top, radius);
        float radiusRightBottom = attributes.getDimension(R.styleable.XmlCustomView_xmlCustomView_radius_right_bottom, radius);
        if (radiusLeftBottom == 0 && radiusLeftTop == 0 && radiusRightBottom == 0 && radiusRightTop == 0) {
            viewParams.setNoCorners(true);
            viewParams.setCorners(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        }
        viewParams.setNoCorners(false);
        viewParams.setCorners(new float[]{radiusLeftTop, radiusLeftTop,
                radiusRightTop, radiusRightTop,
                radiusRightBottom, radiusRightBottom,
                radiusLeftBottom, radiusLeftBottom});
    }

    private static Drawable addCorners(XmlCustomViewParams viewParams, Drawable rawBackground) {
        if (rawBackground == null) {
            return null;
        }
        return rawBackground;
        // TODO: 2017/1/12 为drawable设置圆角
    }

    private static Drawable addCorners(XmlCustomViewParams viewParams, int rawBackgroundColor) {
        if (rawBackgroundColor == 0) {
            return null;
        }
        if (viewParams.isNoCorners()) {
            return new ColorDrawable(rawBackgroundColor);
        }
        RoundRectShape shape = new RoundRectShape(viewParams.getCorners(), null, null);
        ShapeDrawable background = new ShapeDrawable(shape);
        background.getPaint().setColor(rawBackgroundColor);
        background.getPaint().setStyle(Paint.Style.FILL);
        return background;
    }

    /**
     * 增加描边
     */
    private static Drawable addStroke(XmlCustomViewParams viewParams, Drawable rawBackground) {
        float strokeWidth = viewParams.getStrokeWidth();
        int strokeColor = viewParams.getStrokeColor();

        if (strokeWidth == XmlCustomViewParams.DEFAULT_SIZE || strokeColor == XmlCustomViewParams.DEFAULT_COLOR) {
            return rawBackground;
        }
        float[] radius = viewParams.getCorners();
        RectF rectF = new RectF(strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        RoundRectShape shape = new RoundRectShape(radius, rectF, radius);
        ShapeDrawable strokeBackground = new ShapeDrawable(shape);
        strokeBackground.getPaint().setColor(strokeColor);
        strokeBackground.getPaint().setStyle(Paint.Style.FILL);
        if (rawBackground == null) {
            return strokeBackground;
        } else {
            ArrayList<Drawable> backgroundList = new ArrayList<>();
            backgroundList.add(rawBackground);
            backgroundList.add(strokeBackground);
            return new LayerDrawable(backgroundList.toArray(new Drawable[2]));
        }
    }

    /**
     * 增加黑色遮罩
     */
    private static Drawable addThemeMask(XmlCustomViewParams viewParams, Drawable rawBackground) {
        if (rawBackground == null) {
            return null;
        }
        int alpha = viewParams.getMaskAlpha();
        if (alpha == XmlCustomViewParams.DEFAULT_ALPHA) {
            return rawBackground;
        }
        Drawable coverAlphaBackground = addCorners(viewParams, Color.argb(alpha, 0, 0, 0));
        ArrayList<Drawable> backgroundList = new ArrayList<>();
        backgroundList.add(rawBackground);
        backgroundList.add(coverAlphaBackground);
        return new LayerDrawable(backgroundList.toArray(new Drawable[2]));
    }


    private static Drawable addStateMask(XmlCustomViewParams viewParams, Drawable rawBackground, int rawBackgroundAlpha) {
        if (rawBackground == null) {
            return null;
        }
        if (rawBackgroundAlpha == 0) {
            return rawBackground;
        }
        Drawable coverAlphaBackground = addCorners(viewParams, Color.argb(rawBackgroundAlpha, 0, 0, 0));
        ArrayList<Drawable> backgroundList = new ArrayList<>();
        backgroundList.add(rawBackground);
        backgroundList.add(coverAlphaBackground);
        return new LayerDrawable(backgroundList.toArray(new Drawable[2]));
    }

    public static ColorStateList buildColorStateList(TextView textView, XmlCustomViewParams viewParams) {

        int rawTextColor = viewParams.getRawTextColor() == 0 ? textView.getTextColors().getDefaultColor() : viewParams.getRawTextColor();

        int pressedTextColor = viewParams.getPressedTextColor() == 0 ? rawTextColor : viewParams.getPressedTextColor();

        int disableTextColor = viewParams.getDisableTextColor() == 0 ? rawTextColor : viewParams.getDisableTextColor();

        int focusedTextColor = viewParams.getFocusedTextColor() == 0 ? rawTextColor : viewParams.getFocusedTextColor();

        int selectedTextColor = viewParams.getSelectedTextColor() == 0 ? rawTextColor : viewParams.getSelectedTextColor();
        if (rawTextColor == 0 && pressedTextColor == 0 && disableTextColor == 0 && focusedTextColor == 0 && selectedTextColor == 0) {
            return null;
        }
        // 范围大的放前面,范围小的放后面
        int[] colors = new int[]{pressedTextColor, selectedTextColor, focusedTextColor, disableTextColor, rawTextColor};
        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_pressed, -android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled};
        states[4] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static ColorStateList buildColorStateList(TextView textView, int color) {
        if (color == 0) {
            return textView.getTextColors();
        }
        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_pressed, -android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled};
        states[4] = new int[]{};
        ColorStateList rawColorList = textView.getTextColors();
        int defaultTextColor = rawColorList.getDefaultColor();
        int pressedTextColor = rawColorList.getColorForState(states[0], color);
        pressedTextColor = pressedTextColor == defaultTextColor ? color : pressedTextColor;
        int selectedTextColor = rawColorList.getColorForState(states[1], color);
        selectedTextColor = selectedTextColor == defaultTextColor ? color : selectedTextColor;
        int focusedTextColor = rawColorList.getColorForState(states[2], color);
        focusedTextColor = focusedTextColor == defaultTextColor ? color : focusedTextColor;
        int disableTextColor = rawColorList.getColorForState(states[3], color);
        disableTextColor = disableTextColor == defaultTextColor ? color : disableTextColor;


        int[] colors = new int[]{pressedTextColor, selectedTextColor, focusedTextColor, disableTextColor, color};
        return new ColorStateList(states, colors);
    }

    public static ColorStateList buildColorStateList(int pressedColor, int disableColor, int focusedColor, int selectedColor, int defaultColor) {
        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_pressed, -android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled};
        states[4] = new int[]{};
        int[] colors = new int[]{pressedColor, selectedColor, focusedColor, disableColor, defaultColor};
        return new ColorStateList(states, colors);
    }


    public interface OnDisableStateClickListener {
        void onDisableStateClick();
    }
}
