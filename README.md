## CTextView
通用TextView实现各种富文本或其他特殊效果

**引用方法**：
```java
implementation 'com.creditease:ctextviewlib:1.4'
```


## 具体介绍如下：
https://www.jianshu.com/u/e61fb4320b9c


![银行精选列表页](https://upload-images.jianshu.io/upload_images/4334234-bb61f4b213dbf87d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


处理如上图这5种情况


#### 1，CDrawablePadding处理原生DrawablePadding对drawableRight和drawableLeft处理的bug


```
 <LinearLayout
     android:layout_width="match_parent"
     android:layout_height="@dimen/cbase_dp_50"
     android:background="@color/cbase_white_color"
     android:gravity="center_vertical"
     android:orientation="horizontal"
     android:visibility="visible">

     <com.creditwealth.bankfinancing.widget.CTextView
         android:id="@+id/tv_tab_profit"
         android:layout_width="0dp"
         android:layout_height="match_parent"
         android:layout_weight="1"
         android:drawableRight="@drawable/bf_arrow_down_sort"
         android:gravity="center"
         android:text="收益"
         android:textColor="@color/bf_select_text_color"
         android:textSize="@dimen/cbase_dp_15"
         android:textStyle="bold"
         app:bf_CDrawablePadding="10dp" />

     <com.creditwealth.bankfinancing.widget.CTextView
         android:id="@+id/tv_tab_type"
         android:layout_width="0dp"
         android:layout_height="match_parent"
         android:layout_weight="1"
         android:drawableRight="@drawable/bf_arrow_down_float"
         android:text="产品类型"
         android:textColor="@color/bf_select_text_color"
         android:textSize="@dimen/cbase_dp_15"
         android:textStyle="bold"
         app:bf_CDrawablePadding="10dp" />

     <com.creditwealth.bankfinancing.widget.CTextView
         android:id="@+id/tv_tab_bank"
         android:layout_width="0dp"
         android:layout_height="match_parent"
         android:layout_weight="1"
         android:drawableRight="@drawable/bf_arrow_down_float"
         android:text="银行"
         android:textColor="@color/bf_select_text_color"
         android:textSize="@dimen/cbase_dp_15"
         android:textStyle="bold"
         app:bf_CDrawablePadding="10dp" />


 </LinearLayout>

```


#### 2，处理空心button/textview

在xml中使用：

```
    <com.creditwealth.bankfinancing.widget.CTextView
        android:layout_width="100dp"
        android:layout_height="30dp"
        android:text="明日1:00开抢"
        android:textColor="@color/cbase_red_color"
        android:textSize="@dimen/cbase_sp_10"
        app:bf_CStrokeColor="@color/cbase_red_color"
        app:bf_CStrokeWidth="1dp"
        app:bf_CAngleCorner="2dp"/>
```
- bf_CStrokeColor表示外面轮廓线的颜色
- bf_CStrokeWidth表示外面轮廓线的宽度
- bf_CAngleCorner表示圆角弧度

动态生成：
```
 tagText.initStrokeAttr(
     ContextCompat.getColor(ctx, R.color.bf_main),
     2,
     ScreenUtil.dimen2px(ctx, R.dimen.cbase_dp_1)
 )
```

#### 3，处理实心button/textview

在xml中使用：

```
    <com.creditwealth.bankfinancing.widget.CTextView
        android:layout_width="100dp"
        android:layout_height="30dp"
        android:text="明日1:00开抢"
        android:textColor="@color/white"
        android:textSize="@dimen/cbase_sp_10"
        app:bf_CSolidColor="@color/cbase_red_color"
        app:bf_CAngleCorner="2dp"/>
```

- bf_CSolidColor表示实心颜色
- bf_CAngleCorner表示圆角弧度

动态生成：
```
 tagText.initSolidAttr(
  ContextCompat.getColor(ctx, R.color.white),
  ContextCompat.getColor(ctx, R.color.bf_main),
  ScreenUtil.dimen2px(ctx, R.dimen.cbase_dp_1)
 )
```

#### 4，button/textview中倾斜字体

使用：
```
  <com.creditwealth.bankfinancing.widget.CTextView
      android:id="@+id/tv_sale_desc"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:padding="10dp"
      android:text="明日1:00开抢"
      android:textColor="@color/cbase_red_color"
      android:textSize="@dimen/cbase_sp_10"
      app:bf_CRotateDegree="15"
      app:layout_constraintBottom_toBottomOf="@+id/iv_sale_status"
      app:layout_constraintLeft_toLeftOf="@+id/iv_sale_status"
      app:layout_constraintRight_toRightOf="@+id/iv_sale_status"
      app:layout_constraintTop_toTopOf="@+id/iv_sale_status" />
```

```
      app:bf_CRotateDegree="15"
```
表示倾斜角度为15度

#### 5，button/textview中字符串中特定字符串字体大小与颜色

可以直接在xml中预览：


```
    <com.creditwealth.bankfinancing.widget.CTextView
        android:id="@+id/tv_expect_profit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/cbase_dp_10"
        android:textColor="@color/bf_main"
        android:textSize="@dimen/cbase_sp_27"
        app:bf_CSpecialTextReg="%"
        app:bf_CSpecialTextSize="6sp"
        app:layout_constraintLeft_toLeftOf="@+id/tv_product_title"
        app:layout_constraintTop_toBottomOf="@+id/fl_tag_container"
        android:text="4.10%" />
```

![在xml中预览](https://upload-images.jianshu.io/upload_images/4334234-d73b25f8313e048b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

也可直接使用代码：
```
 //CTextView自带函数 处理这种特殊字符串的特殊颜色或大小
 tv_expect_profit.setSpecialText(item.incValue, "%", 0, 14)
```

内部函数实现：

```
    //暴露出来的函数用来 处理特殊字符串的特殊颜色或大小
    fun setSpecialText(srcStr: String, specialTextReg: String, valueColor: Int, size: Int) {
        var valueColor = valueColor

        if (TextUtils.isEmpty(srcStr))
            return

        if (TextUtils.isEmpty(specialTextReg) || !srcStr.contains(specialTextReg))
            return

        if (valueColor == 0) {
            valueColor = this.currentTextColor
        }

        val resultSpan = SpannableString(srcStr)

        val p = Pattern.compile(specialTextReg)
        val m = p.matcher(srcStr)

        while (m.find() && specialTextReg != "") {

            resultSpan.setSpan(
                ForegroundColorSpan(valueColor),
                m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            if (size != 0) {
                resultSpan.setSpan(AbsoluteSizeSpan(size, true), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        this.text = resultSpan
        return
    }
```


