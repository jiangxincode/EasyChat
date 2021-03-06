package com.itheima.easychat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.easychat.R;
import com.itheima.easychat.fragment.ContactsFragment;
import com.itheima.easychat.fragment.SessionFragment;
import com.itheima.easychat.utils.ToolBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
	@InjectView(R.id.main_tv_title)
	TextView				mMainTvTitle;

	@InjectView(R.id.main_viewpager)
	ViewPager				mMainViewpager;

	@InjectView(R.id.main_bottom)
	LinearLayout			mMainBottom;

	private List<Fragment>	mFragments	= new ArrayList<Fragment>();
	private ToolBarUtil		mToolBarUtil;
	private String[]		mToolBarTitleArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		initData();
		initListener();
	}

	private void initListener() {
		mMainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				// 修改颜色
				mToolBarUtil.changeColor(position);
				// 修改title
				mMainTvTitle.setText(mToolBarTitleArr[position]);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mToolBarUtil.setOnToolBarClickListener(new ToolBarUtil.OnToolBarClickListener() {
			@Override
			public void onToolBarClick(int position) {
				mMainViewpager.setCurrentItem(position);
			}
		});
	}

	private void initData() {
		// viewPager-->view-->pagerAdapter
		// viewPager-->fragment-->fragmentPagerAdapter-->fragment数量比较少
		// viewPager-->fragment-->fragmentStatePagerAdapter

		// 添加fragment到集合中
		mFragments.add(new SessionFragment());
		mFragments.add(new ContactsFragment());

		mMainViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		// 底部按钮
		mToolBarUtil = new ToolBarUtil();
		// 文字内容
		mToolBarTitleArr = new String[] { "会话", "联系人" };
		// 图标内容
		int[] iconArr = { R.drawable.selector_meassage, R.drawable.selector_selfinfo };

		mToolBarUtil.createToolBar(mMainBottom, mToolBarTitleArr, iconArr);

		// 设置默认选中会话
		mToolBarUtil.changeColor(0);
	}

	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}
