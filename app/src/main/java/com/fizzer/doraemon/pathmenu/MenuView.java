package com.fizzer.doraemon.pathmenu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Fizzer on 2016/12/12.
 * Email: doraemonmqq@sina.com
 */

public class MenuView extends RelativeLayout {

    private Context mContext;
    private List<ImageView> imgList;

    private ImageView ivCamera;
    private ImageView ivDelete;
    private ImageView ivEdit;
    private ImageView ivFavor;
    private ImageView ivDeliver;
    private ImageView ivAdd;


    private boolean openFlag = false;

    private int radius = 400;

    public MenuView(Context context) {
        super(context);
        init(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    public void init(Context context) {

        this.mContext = context;
        imgList = new ArrayList<>();
        View view = View.inflate(context, R.layout.view_menu, null);

        ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        imgList.add(ivCamera);

        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        imgList.add(ivDelete);

        ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        imgList.add(ivEdit);

        ivFavor = (ImageView) view.findViewById(R.id.ivFavor);
        imgList.add(ivFavor);

        ivDeliver = (ImageView) view.findViewById(R.id.ivDeliver);
        imgList.add(ivDeliver);

        ivAdd = (ImageView) view.findViewById(R.id.ivAdd);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        this.addView(view);

        initListener();
    }


    private void initListener() {
        ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (openFlag) {
                    closeAnim();
                } else {
                    startAnim();
                }
            }
        });


    }

    /**
     * 开始动画
     */
    private void startAnim() {
        for (int index = 0; index < imgList.size(); index++) {
            imgList.get(index).setVisibility(VISIBLE);
            ObjectAnimator an = ObjectAnimator.ofFloat(imgList.get(index),"rotation",0,360);
            an.setStartDelay(200);
            an.setRepeatCount(3);
            an.setDuration(200).start();
            ObjectAnimator animation = ObjectAnimator.ofFloat(imgList.get(index), "translationX", "translationY", getOpenPointPath(index));
            animation.setInterpolator(new BounceInterpolator());
            animation.setDuration(1000).start();
            for (int i = 0; i < imgList.size(); i++) {
                final int finalI = i;
                imgList.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,"点击" + finalI,Toast.LENGTH_SHORT).show();
                    }
                });
            }
            openFlag = true;
        }
    }

    private void closeAnim() {
        for (int index = 0; index < imgList.size(); index++) {
            ObjectAnimator oa = ObjectAnimator.ofFloat(imgList.get(index), "translationX", "translationY", getClosePointPath(index));
            oa.setDuration(500).start();
            openFlag = false;
        }
    }

    private Path getOpenPointPath(int index) {
        Path mPath = new Path();

        int size = imgList.size() - 1;
        float px = (float) (radius * Math.cos(Math.toRadians(index * 90 / size)));
        float py = (float) (radius * Math.sin(Math.toRadians(index * 90 / size)));

        mPath.lineTo(px, py);

        return mPath;
    }

    public Path getClosePointPath(int index) {
        int size = imgList.size() - 1;
        float px = (float) (radius * Math.cos(Math.toRadians(index * 90 / size)));
        float py = (float) (radius * Math.sin(Math.toRadians(index * 90 / size)));

        Path mPath = new Path();
        mPath.moveTo(px, py);
        mPath.lineTo(0, 0);
        return mPath;
    }
}
