package com.risfond.rnss.home.position.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.net.UtilHelper;
import com.risfond.rnss.entry.CustomerSearchResponse;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.home.customer.activity.CustomDetailActivity2;
import com.risfond.rnss.home.customer.adapter.CustomerSearchV2Adapter;
import com.risfond.rnss.home.customer.fragment.CustomerFragment;
import com.risfond.rnss.home.customer.modelImpl.CustomerSearchImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerSearch;
import com.risfond.rnss.home.position.activity.PositionDetailActivity;
import com.risfond.rnss.home.position.adapter.PositionSearchV2Adapter;
import com.risfond.rnss.home.position.modelImpl.PositionSearchImpl;
import com.risfond.rnss.home.position.modelInterface.IPositionSearch;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/4
 * @desc    我的职位
 */
public class PositionFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, ResponseCallBack {
    /**
     * 我的职位
     */
    public static String GUISHU_TYPE_MY_CUSTOM = "1";
    /**
     * 合作职位
     */
    public static String GUISHU_TYPE_COOP_CUSTOM = "2";
    /**
     * 其他职位
     */
    public static String GUISHU_TYPE_OTHER_CUSTOM = "3";

    /**
     * 职位类型 KEY
     */
    private static String TYPE_KEY = "TYPE_KEY";
    /**
     * 检索 KEY
     */
    private static String SEARCH_KEY = "SEARCH_KEY";
    /////////////////////////////////////////////


    @BindView(R.id.tv_resume_total)
    TextView mResumeTotal;
    @BindView(R.id.rv_resume_list)
    RecyclerView mResumeList;

    private boolean isPrepare, isLoadMore;
    private Map<String, String> request;
    private IPositionSearch iPositionSearch;
    private PositionSearchResponse response;
    /**
     * 职位类型
     */
    private String mCurrentType;
    private String mSearchContent;
    private PositionSearchV2Adapter mAdapter;
    private View mNullDataView;
    private int pageindex = 1;

    public static PositionFragment getInstance(String type) {
        PositionFragment positionFragment = new PositionFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, type);
        positionFragment.setArguments(args);
        return positionFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_position;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mCurrentType = getArguments().getString(TYPE_KEY);
        mSearchContent = getArguments().getString(SEARCH_KEY);
        iPositionSearch = new PositionSearchImpl();
        initComponent();
        lazyLoad();
    }

    /**
     * 初始化相关组件
     */
    private void initComponent() {
        request = new HashMap<>();
        mResumeList.setLayoutManager(new LinearLayoutManager(getContext()));
        mResumeList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(getContext(), R.color.color_home_back)));
        mAdapter = new PositionSearchV2Adapter();
        mNullDataView = getActivity().getLayoutInflater().inflate(R.layout.item_nodata, null);
        mAdapter.setOnLoadMoreListener(this, mResumeList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PositionDetailActivity.startAction(getContext(), String.valueOf(mAdapter.getData().get(position).getID()));
            }
        });
        mResumeList.setAdapter(mAdapter);
        isPrepare = true;
        initData();
    }
    private void initData() {
        if (response == null) {
            return;
        }
        if (mResumeTotal != null) {
            mResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
        }
        mAdapter.addData(response==null?null:response.getData());
        if (mAdapter.getData().size() <= 0) {
            ((TextView) mNullDataView.findViewById(R.id.id_nodata_message)).setText("您还没有职位，快去发布职位吧！");
            mAdapter.setEmptyView(mNullDataView);
        }
        if (mAdapter.getData().size() >= response.getTotal()) {
            mAdapter.setEnableLoadMore(false);
        }else{
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    protected void lazyLoad() {
        if (isPrepare && isVisible && response == null) {
            customerReqeust();
        }
    }

    private void customerReqeust() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(getContext(), "搜索中...");
        }
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(getContext())));
        request.put("pageindex", String.valueOf(pageindex));
        request.put("guishu", mCurrentType);
        iPositionSearch.positionSearchRequest(SPUtil.loadToken(getContext()), request, URLConstant.URL_JOB_SEARCH2, this);
    }

    @Override
    public void onLoadMoreRequested() {
        if (response == null) {
            mAdapter.loadMoreEnd(false);
            return;
        }

        if (mAdapter.getData().size() >= response.getTotal()) {
            mAdapter.loadMoreEnd(false);
        } else {
            isLoadMore = true;
            //加载更多
            pageindex++;
            customerReqeust();
        }
    }

    @Override
    public void onSuccess(final Object obj) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof PositionSearchResponse) {
                    response = (PositionSearchResponse) obj;
                    initData();
                    isLoadMore = false;
                }
            }
        });

    }

    @Override
    public void onFailed(String str) {
        UtilHelper.outLog(TAG, str);
    }

    @Override
    public void onError(String str) {
        UtilHelper.outLog(TAG, str);
    }
}
