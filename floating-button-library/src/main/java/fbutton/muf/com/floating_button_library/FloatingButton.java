package fbutton.muf.com.floating_button_library;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhannad F on 4/13/2015.
 */
public class FloatingButton  extends RelativeLayout implements View.OnClickListener, AbsListView.OnScrollListener, View.OnTouchListener {
    private final ImageButton mainButton;
    private final float horizantalDistance;
    private float verticalDistance;
    private ArrayList<Integer> buttonsResources;
    private ArrayList<OnClickListener> listeners;
    private ArrayList<ImageButton> buttons;
    private TextView bg;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int mainButtonDrawable;
    private int dismisButtonDrawable;
    private Direction direction;
    private int BUTTON_HEIGHT = 0;
    private float startY;
    private boolean isScrollingDown;

    public FloatingButton(Context context) {
        this(context,null);
    }

    public FloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.floatingButtonAttrs, R.style.floating_button_default_theme, 0);

        mainButtonDrawable = a.getResourceId(R.styleable.floatingButtonAttrs_main_button_image,R.drawable.yellow_circle);
        dismisButtonDrawable = a.getResourceId(R.styleable.floatingButtonAttrs_dismis_button_image,R.drawable.dismiss_button);
        verticalDistance = a.getDimension(R.styleable.floatingButtonAttrs_vertical_distance, 20f);
        horizantalDistance = a.getDimension(R.styleable.floatingButtonAttrs_horizanal_distance,20f);
        BUTTON_HEIGHT = (int) a.getDimension(R.styleable.floatingButtonAttrs_button_height,20f);
        direction = a.getInt(R.styleable.floatingButtonAttrs_direction,0) == 0 ?
                Direction.right
                :Direction.left;
        //do something with str

        a.recycle();
        final float scale =  getContext().getResources().getDisplayMetrics().density;
        buttonsResources = new ArrayList<Integer>();
        listeners = new ArrayList<OnClickListener>();

        setPadding(0,0,0,0);

        LayoutParams mainButtonParams = new LayoutParams(BUTTON_HEIGHT, BUTTON_HEIGHT);
        mainButtonParams.alignWithParent = true;
        mainButtonParams.bottomMargin = (int) verticalDistance;
        if(direction == Direction.right)
            mainButtonParams.rightMargin = (int) horizantalDistance;
        else
            mainButtonParams.leftMargin = (int) horizantalDistance;
        mainButtonParams.addRule(ALIGN_PARENT_BOTTOM);
        mainButtonParams.addRule(direction == Direction.right ? ALIGN_PARENT_RIGHT : ALIGN_PARENT_LEFT);


        createImageButton(context, mainButtonParams);


        mainButton = createImageButton(context, mainButtonParams);
        mainButton.setOnClickListener(this);
        mainButton.setImageResource(mainButtonDrawable);
        addView(mainButton);



    }

    public void setDismisButtonRes(int res)
    {
        dismisButtonDrawable = res;
    }

    public void setMainButtonRes(int res)
    {
        mainButtonDrawable = res;
        mainButton.setImageResource(res);
    }
    public void addButton(int res,OnClickListener listener)
    {
        buttonsResources.add(new Integer(res));
        listeners.add(listener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();

        screenWidth = display.getWidth();
        screenHeight = display.getHeight();


        show();


    }

    private ImageButton createImageButton(Context context, LayoutParams mainButtonParams) {
        ImageButton button = new ImageButton(context);
        button.setAdjustViewBounds(true);
        button.setPadding(0,0,0,0);
        button.setBackgroundColor(0x00ffffff);
        button.setLayoutParams(mainButtonParams);
        return button;
    }

    public void show()
    {

        animateButtom(mainButton, (int) verticalDistance);
    }
    public void hide()
    {
        animateButtom(mainButton, (int) (-1 * (BUTTON_HEIGHT + verticalDistance)));
    }

    void fade(final boolean in, final View v, final boolean remove)
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(in ? 0f : 1f , in ? 1f : 0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(remove)
                    removeView(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(alphaAnimation);
    }


    @Override
    public void onClick(View v) {
        bg = new TextView(getContext());
        bg.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bg.setBackgroundColor(0xccffffff);
        AlphaAnimation animation = new AlphaAnimation(0, 0);
        animation.setDuration(0);
        animation.setFillAfter(true);
        bg.startAnimation(animation);
        addView(bg);
        bg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismis();
            }
        });
        buttons = new ArrayList<ImageButton>();
        int verticalSpacing = (int) (getScreenHeight() - verticalDistance / 2);
        verticalSpacing /=5;
        fade(true,bg,false);
        hide();
        for(int i = 0 ; i <= buttonsResources.size(); i ++)
        {
            LayoutParams params = new LayoutParams(BUTTON_HEIGHT,BUTTON_HEIGHT);
            params.alignWithParent = true;
            params.bottomMargin = verticalSpacing;
            if(direction == Direction.right)
                params.rightMargin = (int) horizantalDistance;
            else
                params.leftMargin = (int) horizantalDistance;

            params.addRule(ALIGN_PARENT_BOTTOM);
            params.addRule(direction == Direction.right ? ALIGN_PARENT_RIGHT : ALIGN_PARENT_LEFT);
            buttons.add(createImageButton(getContext(),params));
            if(i == buttonsResources.size()) {
                buttons.get(i).setImageResource(dismisButtonDrawable);
                buttons.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismis();
                    }
                });
            }


            else
            {
                buttons.get(i).setImageResource(buttonsResources.get(i));
                buttons.get(i).setOnClickListener(listeners.get(i));
            }

            addView(buttons.get(i));
            if(i == buttonsResources.size() && i % 2 == 0)
            {
                animate(buttons.get(i)
                        , (getScreenWidth() / 2   - BUTTON_HEIGHT / 2)
                        , getScreenHeight() - BUTTON_HEIGHT - (i / 2 + 1) * verticalSpacing );
            }
            else
                animate(buttons.get(i)
                        , ((1 + 2 * (i % 2)) * getScreenWidth() / 4  - BUTTON_HEIGHT / 2)
                        , getScreenHeight() - BUTTON_HEIGHT - (i / 2 + 1) * verticalSpacing );
        }


    }

    private int getScreenWidth() {
        return screenWidth;
    }

    private int getScreenHeight() {

        return screenHeight;
    }

    private void dismis() {
        show();
        final int vertical = BUTTON_HEIGHT;
        final int horizental = BUTTON_HEIGHT / 2;
        fade(false,bg,true);

        if(buttons == null)
            return;
        for (final View v : buttons)
        {
            final int fromVerticalValue = ((LayoutParams) v.getLayoutParams()).bottomMargin ;
            final int fromHorizentalValue = direction == Direction.left
                    ?((LayoutParams) v.getLayoutParams()).leftMargin
                    :((LayoutParams) v.getLayoutParams()).rightMargin;

            AlphaAnimation alphaAnimation = new AlphaAnimation(1f ,0f){
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    LayoutParams params = (LayoutParams) v.getLayoutParams();
                    if(vertical > 0 )
                        params.bottomMargin = (int) (fromVerticalValue + ((vertical - fromVerticalValue) * interpolatedTime));
                    if(horizental > 0 ) {
                        if (direction == Direction.right)
                            params.rightMargin = (int) (fromHorizentalValue + ((horizental - fromHorizentalValue) * interpolatedTime));
                        else
                            params.leftMargin = (int) (fromHorizentalValue + ((horizental - fromHorizentalValue) * interpolatedTime));
                    }
                    v.requestLayout();
                }
            };
            alphaAnimation.setDuration(300);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    removeView(v);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            v.startAnimation(alphaAnimation);
        }
    }

    private void animate(final View v, final int toHorizental, final int toVertical) {
        final int fromVerticalValue = ((LayoutParams) v.getLayoutParams()).bottomMargin ;
        final int fromHorizentalValue =  direction == Direction.left
                ?((LayoutParams) v.getLayoutParams()).leftMargin
                :((LayoutParams) v.getLayoutParams()).rightMargin;

        Animation a = new Animation() {


            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                LayoutParams params = (LayoutParams) v.getLayoutParams();
                if(toVertical > 0 )
                    params.bottomMargin = (int) (fromVerticalValue + ((toVertical - fromVerticalValue) * interpolatedTime));
                if(toHorizental > 0 ) {
                    if (direction == Direction.right)
                        params.rightMargin = (int) (fromHorizentalValue + ((toHorizental - fromHorizentalValue) * interpolatedTime));
                    else
                        params.leftMargin = (int) (fromHorizentalValue + ((toHorizental - fromHorizentalValue) * interpolatedTime));
                }
                v.requestLayout();
            }
        };
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.setDuration(300); // in ms
        v.startAnimation(a);
    }


    public static void animateButtom(final View v , final int toVertical )
    {
        final int fromVerticalValue = ((LayoutParams) v.getLayoutParams()).bottomMargin ;

        Animation a = new Animation() {


            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                LayoutParams params = (LayoutParams) v.getLayoutParams();
                params.bottomMargin = (int) (fromVerticalValue + ((toVertical - fromVerticalValue) * interpolatedTime));
                v.requestLayout();
            }
        };
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.setDuration(300); // in ms
        v.startAnimation(a);

    }

    public void animateWithScroll(AbsListView absListView)
    {
        absListView.setOnScrollListener(this);
        absListView.setOnTouchListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(isScrollingDown)
        {
            hide();
        }
        else
        {
            show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                isScrollingDown = event.getRawY() < startY;
                startY = event.getRawY();
                break;
        }
        return false;
    }
}
