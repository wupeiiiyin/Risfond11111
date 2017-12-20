package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CustomDialog;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.common.utils.net.UtilHelper;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.ResumeSearchAdd;
import com.risfond.rnss.entry.ResumeSearchAddResponse;
import com.risfond.rnss.entry.ResumeSearchAll;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.entry.ResumeSearchWholeResponse;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.home.commonFuctions.myAttenDance.activity.MyAttendanceActivity;
import com.risfond.rnss.home.resume.adapter.ResumeSearchAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchAddAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchV2Adapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchWholeAdapter;
import com.risfond.rnss.home.resume.fragment.EducationFragment;
import com.risfond.rnss.home.resume.fragment.ExperienceFragment;
import com.risfond.rnss.home.resume.fragment.MoreFragment;
import com.risfond.rnss.home.resume.fragment.PositionFragment;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchAddImpl;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchAllImpl;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchImpl;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearchWhole;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;
import com.risfond.rnss.home.window.MultiSelectPopupWindow;
import com.risfond.rnss.widget.RecycleViewDivider;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 简历搜索结果页面
 * 简历搜索 >> 点击搜索框 >> 简历搜索页
 */
public class ResumeSearchResultActivity extends BaseActivity implements ResponseCallBack, SelectCallBack, BaseQuickAdapter.RequestLoadMoreListener, MoreFragment.OnResumeSelectListener {

    @BindView(R.id.et_resume_search)
    EditText etResumeSearch;
    @BindView(R.id.rv_resume_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.ll_resume)
    LinearLayout llResume;
    @BindView(R.id.activity_resume_search_result)
    LinearLayout activityResumeSearchResult;
    @BindView(R.id.cb_position)
    CheckBox cbPosition;
    @BindView(R.id.cb_experience)
    CheckBox cbExperience;
    @BindView(R.id.cb_education)
    CheckBox cbEducation;
    @BindView(R.id.cb_more)
    CheckBox cbMore;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.frame)
    View Frame;
    @BindView(R.id.tv_search_savetiaojian)
    TextView tvSave;//保存搜索简历
    @BindView(R.id.cb_whole)
    CheckBox cbWhole;//搜索分类按钮

    private Context context;
    private ResumeSearchV2Adapter adapter;
    private ResumeSearchHistoryAdapter historyAdapter;
    private Map<String, String> request;
    private Map<String, String> requestadd;
    private IResumeSearch iResumeSearch;
    private IResumeSearch iResumeSearchWhole;
    private IResumeSearch iResumeSearchAdd;
    private ResumeSearchResponse response;
    private ResumeSearchWholeResponse wholeResponse;
    private ResumeSearchAddResponse responseAdd;
    private List<ResumeSearch> searches = new ArrayList<>();
    private List<ResumeSearch> temp = new ArrayList<>();
    private List<ResumeSearchAdd> add = new ArrayList<>();
    private List<ResumeSearchAdd> addd_temp = new ArrayList<>();

    private List<ResumeSearchAll> searcheall = new ArrayList<>();
    private List<ResumeSearchAll> searche_temp = new ArrayList<>();
    private ResumeSearchAll responseall;
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private List<String> histories;
    private List<String> historiesAESC;
    private int mRequestType;

    private FragmentTransaction transaction;
    private ExperienceFragment experienceFragment;
    private PositionFragment positionFragment;
    private EducationFragment educationFragment;
    private MoreFragment moreFragment;

    private List<String> selectedIds = new ArrayList<>();//城市ID
    private List<String> selectedNames = new ArrayList<>();//城市名称
    private List<String> educations = new ArrayList<>();//学历ID
    private List<String> educationNames = new ArrayList<>();//学历名称
    private String experience_from = "";//经验from
    private String experience_to = "";//经验to
    private List<String> languages = new ArrayList<>();//语言ID
    private List<String> recommends = new ArrayList<>();//推荐状态ID
    private List<String> sexs = new ArrayList<>();//性别ID


    private String yearfrom = "";//工作经验开始  0 不限
    private String yearto = "";//工作经验结束  0 不限
    private String salaryfrom = "";
    private String salaryto = "";
    private boolean isHasData;

    private ResumeSearchWholeAdapter resumeSearchWholeAdapter;
    private ResumeSearchAllImpl resumeSearchAll;
    private ResumeSearchAddAdapter addAdapter;
    private String eTResumeSearch;//获取搜索的内容
    private SharedPreferences king;
    private MultiSelectPopupWindow mMultiSelectPopupWindow;

    private ResumeWhole mResumeWhole = new ResumeWhole();
    @Override
    public int getContentViewResId() {
        return R.layout.activity_resume_search_result;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        king = getSharedPreferences("KING", MODE_PRIVATE);
        context = ResumeSearchResultActivity.this;
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();

        iResumeSearch = new ResumeSearchImpl();//创建impl

        iResumeSearchWhole = new ResumeSearchAllImpl();//搜索全部

        iResumeSearchAdd = new ResumeSearchAddImpl();//保存


        cbWhole.setText("全部");//初始值
        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));

        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        adapter = new ResumeSearchV2Adapter();
        adapter.setOnLoadMoreListener(this, rvResumeList);

        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);
        resumeSearchWholeAdapter = new ResumeSearchWholeAdapter(context, searcheall);


        rvResumeList.setAdapter(adapter);

        //        rvResumeList.setAdapter(resumeSearchWholeAdapter);
        addAdapter = new ResumeSearchAddAdapter(context, add);

        checkSearchEditText();
        etResumeSearch.setFocusable(false);
        initHistoryData();
        itemClick();
    }

    /**
     * 请求简历列表
     *
     * @param contact
     */
    private void resumeRequest(String contact) {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request = new HashMap<>();
        request.put("keywordstype", mRequestType + "");//根据传入的值来执行搜索
        request.put("keyword", contact);
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(mResumeWhole.getPageindex()));
        //判断是否选择职位类型
        /**
         * 0 > 简历搜索
         * 1 > 职位搜索
         */
        request.put("selecttype", "0");

        //工作点
        join(request, selectedIds, "worklocation");

        //工作经验
        request.put("yearfrom", yearfrom);
        request.put("yearto", yearto);

        //学历
        join(request, educations, "edu");

        //年龄
        request.put("agefrom", String.valueOf(mResumeWhole.getAgefrom()));
        request.put("ageto", String.valueOf(mResumeWhole.getAgeto()));
        //性别
        if (Integer.parseInt(mResumeWhole.getGender().get(0)) != 0) {
            request.put("gender[0]", mResumeWhole.getGender().get(0));

        }
        //年薪
        request.put("salaryfrom", String.valueOf(mResumeWhole.getSalaryfrom()));
        request.put("salaryto", String.valueOf(mResumeWhole.getSalaryto()));
        //推荐状态
        if (Integer.parseInt(mResumeWhole.getResumestatus().get(0)) !=0) {
            request.put("resumestatus[0]", mResumeWhole.getResumestatus().get(0));
        }
        //语言
        join(request, mResumeWhole.getLang(), "lang");
        //当前行业
        join(request, mResumeWhole.getIndustrys(), "industrys");
        //期望行业
        join(request, mResumeWhole.getDesiredIndustries(), "desiredIndustries");
        //期望地点
        join(request, mResumeWhole.getDesiredlocations(), "desiredlocations");
        //期望职位
        join(request, mResumeWhole.getDesiredoccupations(), "desiredoccupations");
        //学校名称
        request.put("schoolname", String.valueOf(mResumeWhole.getSchoolname()));
        //专业名称
        request.put("major", String.valueOf(mResumeWhole.getMajor()));

        //推荐历史
        if (mResumeWhole.getHistory() != 0) {
            request.put("history", String.valueOf(mResumeWhole.getHistory()));
        }
        //更新时间
        request.put("lastupdatetimefrom", "");
        request.put("lastupdatetimeto", "");

        iResumeSearchWhole.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_SEARCHALL3, this);


    }
    private Map join(Map map,List<String> datas,String key) {
        if (datas == null) {
            return map;
        }
        for (int i = 0; i < datas.size(); i++) {
            map.put(key+"["+i+"]", datas.get(i));
        }
        return map;
    }

    @OnClick({R.id.tv_search_cancel, R.id.tv_search_savetiaojian})//监听事件
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            ImeUtil.hideSoftKeyboard(etResumeSearch);
            onFinish();
        }
        if (v.getId() == R.id.tv_search_savetiaojian) {

            resumeRequestAdd();//请求保存

        }
    }

    /**
     * 保存搜索条件
     */
    private void resumeRequestAdd() {//请求保存
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request = new HashMap<>();
        request.put("keyword", etResumeSearch.getText().toString().trim());
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(mResumeWhole.getPageindex()));
        for (int i = 0; i < selectedIds.size(); i++) {
            String key = "worklocation[" + i + "]";
            request.put(key, selectedIds.get(i));
            Log.i("TAGs", selectedIds + "------selectedIds---------------");
        }

        for (int i = 0; i < selectedNames.size(); i++) {//城市名称
            String key = "worklocations[" + i + "]";
            request.put(key, selectedNames.get(i));
            Log.i("TAGs", selectedNames + "------selectedNames---------------");
        }

        request.put("yearfrom", yearfrom);
        request.put("yearto", yearto);

        for (int i = 0; i < educations.size(); i++) {
            String key = "edu[" + i + "]";
            request.put(key, educations.get(i));
        }

        /*request.put("agefrom", age_From);//agefrom  年龄
        request.put("ageto", age_To);//ageto
*/
        if (sexs.size() > 0) {
            String s = sexs.get(0);
            request.put("gender[0]", s);

        }

        request.put("salaryfrom", salaryfrom);
        request.put("salaryto", salaryto);

        if (recommends.size() > 0) {
            request.put("resumestatus[0]", recommends.get(0));
        }

        for (int i = 0; i < languages.size(); i++) {
            String key = "lang[" + i + "]";
            request.put(key, languages.get(i));
        }

        request.put("experience_from", experience_from);//经验
        request.put("experience_to", experience_to);//经验

        iResumeSearchAdd.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_ADDRESUMEQUERY, this);
        //        callBack.onMoreConfirm(recommends, age_From, age_To, sexs, salary_From, salary_To, languages, 1);//回调
    }

    private void checkSearchEditText() {
        etResumeSearch.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                etResumeSearch.setFocusableInTouchMode(true);
                etResumeSearch.setFocusable(true);
                etResumeSearch.requestFocus();
                initHistoryData();
                clearAllSelected();
                return false;
            }
        });

        etResumeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击“搜索”软键盘
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    mResumeWhole.setPageindex(1);
                    searches.clear();
                    eTResumeSearch = etResumeSearch.getText().toString().trim();
                    //                    resumeRequest(eTSearch);
                    //                    resumeRequest(etResumeSearch.getText().toString().trim());
                    //                    saveHistory(etResumeSearch.getText().toString().trim());
                    resumeRequest(eTResumeSearch);
                    saveHistory(eTResumeSearch);
                    Log.i("TAG", eTResumeSearch + "-----eTResumeSearch--------");
                    //                    SharedPreferences.Editor edit = king.edit();
                    //                    edit.putString("etresumeSearch",eTResumeSearch);
                    //                    edit.commit();

                }
                return false;
            }
        });

    }

    private void initResumeData() {
        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }
        if (llResume != null) {
            llResume.setVisibility(View.VISIBLE);
        }
        if (response != null) {
            adapter.addData(response.getData());
        }
        //hideResume();
    }

    private void initResumeWholeData() {
        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }
        if (llResume != null) {
            llResume.setVisibility(View.VISIBLE);
        }
        resumeSearchWholeAdapter.updateData(searcheall);

    }

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        llResume.setVisibility(View.GONE);
        historiesAESC.clear();
        histories = SPUtil.loadSearchHistoryArray(context);
        for (int i = histories.size() - 1; i >= 0; i--) {
            historiesAESC.add(histories.get(i));
        }
        rvResumeSearchHistory.setAdapter(historyAdapter);
        historyAdapter.updateHistory(historiesAESC);
        hideHistory();
    }

    private void itemClick() {
        //搜索里的列表点击
        historyAdapter.setOnItemClickListener(new ResumeSearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //如果点击的是清除历史记录
                if (historyAdapter.isFooterView(position)) {
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    histories.clear();
                    historiesAESC.clear();
                    historyAdapter.updateHistory(historiesAESC);
                    SPUtil.saveSearchHistoryArray(context, histories);
                    hideHistory();
                } else {
                    if (NetUtil.isConnected(context)) {
                        mResumeWhole.setPageindex(1);
                        searches.clear();
                        ImeUtil.hideSoftKeyboard(etResumeSearch);
                        etResumeSearch.setText(historiesAESC.get(position));
                        adapter.setNewData(null);
                        resumeRequest(historiesAESC.get(position));
                        saveHistory(historiesAESC.get(position));
                    } else {
                        ToastUtil.showImgMessage(context, "请检查网络连接");
                    }

                }
            }
        });

        //回调适配器中的删除接口执行删除
        historyAdapter.setOnDeleteClickListener(new ResumeSearchHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                histories.remove(historiesAESC.get(position));
                SPUtil.saveSearchHistoryArray(context, histories);
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
            }
        });

        //简历列表点击
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                ResumeDetailActivity.startAction(context, String.valueOf(adapter.getData().get(position).getId()));
            }
        });

    }

    private void updateHistory(String text) {
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).equals(text)) {
                histories.remove(i);
                break;
            }
        }
        if (histories.size() == 15) {
            histories.remove(0);
        }
        histories.add(text);
        SPUtil.saveSearchHistoryArray(context, histories);
    }

    /**
     * 搜素记录只保存15条
     */
    private void saveHistory(String text) {
        updateHistory(text);
    }

    private void hideHistory() {
        if (historiesAESC.size() > 0) {
            rvResumeSearchHistory.setVisibility(View.VISIBLE);
            llEmptyHistory.setVisibility(View.GONE);
        } else {
            rvResumeSearchHistory.setVisibility(View.GONE);
            llEmptyHistory.setVisibility(View.VISIBLE);
        }
    }

    private void hideResume() {
        if (searches.size() > 0) {
            if (rvResumeList != null) {
                rvResumeList.setVisibility(View.VISIBLE);
            }
            if (llEmptySearch != null) {
                llEmptySearch.setVisibility(View.GONE);
            }
        } else {
            if (rvResumeList != null) {
                rvResumeList.setVisibility(View.GONE);
            }
            if (llEmptySearch != null) {
                llEmptySearch.setVisibility(View.VISIBLE);
            }
        }
    }


    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ResumeSearchResultActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }

                if (obj instanceof ResumeSearchResponse) {
                    response = (ResumeSearchResponse) obj;
                    loadServiceInfoToResumeList();
                }


                if (obj instanceof ResumeSearchAddResponse) {
                    responseAdd = (ResumeSearchAddResponse) obj;

                    //弹框对话
                    CustomDialog.Builder builder = new CustomDialog.Builder(context);
                    builder.setMessage("您已保存成功，可在快捷搜索查看");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            //
                            //                                        List<ResumeSearchAdd> data = responseAdd.getData();
                            //                                        ResumeSearchAdd resumeSearchAdd = data.get(0);
                            //                                        int id = resumeSearchAdd.getId();
                            //                                        SharedPreferences king = getSharedPreferences("KING", MODE_PRIVATE);
                            //                                        SharedPreferences.Editor edit = king.edit();
                            //                                        edit.putInt("Id",id);
                            //                                        edit.commit();
                        }
                    });

                    builder.create().show();//显示
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                isLoadMore = false;
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 将查询到的数据加载到列表中
     */
    private void loadServiceInfoToResumeList() {
        if (tvResumeTotal != null) {
            tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
        }


        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }
        if (llResume != null) {
            llResume.setVisibility(View.VISIBLE);
        }
        adapter.addData(response.getData());
        adapter.loadMoreComplete();
        if (adapter.getData().size() >= response.getTotal()) {
            adapter.setEnableLoadMore(false);
        }else{
            adapter.loadMoreComplete();
        }
        isLoadMore = false;
    }

    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void onFailed(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                    initResumeData();
                    ToastUtil.showShort(context, "搜索失败");
                }
            }
        });
    }

    @Override
    public void onError(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                    initResumeData();
                }
            }
        });
    }

    @OnCheckedChanged({R.id.cb_position, R.id.cb_experience, R.id.cb_education, R.id.cb_more, R.id.cb_whole})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ImeUtil.hideSoftKeyboard(etResumeSearch);

        switch (buttonView.getId()) {
            case R.id.cb_position:
                if (isChecked) {
                    checkedPosition();
                    initPositionFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearPositionFragment();
                }
                break;
            case R.id.cb_experience:
                if (isChecked) {
                    checkedExperience();
                    initExperienceFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearExperienceFragment();
                }
                break;
            case R.id.cb_education:
                if (isChecked) {
                    checkedEducation();
                    initEducationFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearEducationFragment();
                }
                break;
            case R.id.cb_more:
                if (isChecked) {
                    checkedMore();
                    initMoreFragment();
                } else {
                    Frame.setVisibility(View.GONE);
                    clearMoreFragment();
                }
                break;
            case R.id.cb_whole://搜索分类
                if (isChecked) {
                    if (mMultiSelectPopupWindow == null) {
                        initmPopupWindowView();
                        mMultiSelectPopupWindow.showView(buttonView, 0, 5);
                    } else {
                        mMultiSelectPopupWindow.showView(buttonView, 0, 5);
                    }
                } else {
                    if (mMultiSelectPopupWindow != null) {
                        mMultiSelectPopupWindow.hide();
                    }

                }
                break;
        }
    }
    private void initmPopupWindowView() {
        HashMap<String, String> map = new HashMap<>();
        map.put("0", "全部");
        map.put("1", "职位");
        map.put("2", "公司");
        mMultiSelectPopupWindow = new MultiSelectPopupWindow(this, this, map, new MultiSelectPopupWindow.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v) {
                String type = String.valueOf(v.getId());
                if (type.equals(String.valueOf(mRequestType))) {
                    return;
                }
                mRequestType = Integer.parseInt(type);
                cbWhole.setText(((TextView) v).getText());
                String content = etResumeSearch.getText().toString().trim();
                if (content.length() > 0) {
                    isLoadMore = false;
                    saveHistory(content);
                    onChangeKeywordtypes();
                }
                mMultiSelectPopupWindow.hide();
            }
        });
        mMultiSelectPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cbWhole.setChecked(false);
            }
        });
    }

    /**
     * 切换检索类型
     * 公司、职位
     */
    private void onChangeKeywordtypes() {
        mResumeWhole.setPageindex(1);
        adapter.setNewData(null);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    //填充职位页面
    private void initPositionFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        positionFragment = new PositionFragment(selectedIds, selectedNames, this);
        transaction.add(R.id.frame, positionFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);

    }

    private void clearPositionFragment() {
        if (positionFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(positionFragment);
            transaction.commit();
        }
    }

    //填充经验页面
    private void initExperienceFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        experienceFragment = new ExperienceFragment(experience_from, experience_to, this);
        transaction.add(R.id.frame, experienceFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);

    }

    private void clearExperienceFragment() {
        if (experienceFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(experienceFragment);
            transaction.commit();
        }
    }

    //填充学历页面
    private void initEducationFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        educationFragment = new EducationFragment(educations, educationNames, this);
        transaction.add(R.id.frame, educationFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);
    }

    private void clearEducationFragment() {
        if (educationFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(educationFragment);
            transaction.commit();
        }
    }

    //填充更多页面
    private void initMoreFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        if (searches.size() > 0) {
            isHasData = true;
        } else {
            isHasData = false;
        }
        moreFragment = new MoreFragment(mResumeWhole, this);
        transaction.add(R.id.frame, moreFragment);
        transaction.commit();

        Frame.setVisibility(View.VISIBLE);
    }

    private void clearMoreFragment() {
        if (moreFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(moreFragment);
            transaction.commit();
        }
    }

    private void ClearAllFragment() {
        clearPositionFragment();
        clearExperienceFragment();
        clearEducationFragment();
        clearMoreFragment();
    }

    private void checkedPosition() {
        cbExperience.setChecked(false);
        cbEducation.setChecked(false);
        cbMore.setChecked(false);
    }

    private void checkedExperience() {
        cbPosition.setChecked(false);
        cbEducation.setChecked(false);
        cbMore.setChecked(false);
    }

    private void checkedEducation() {
        cbPosition.setChecked(false);
        cbExperience.setChecked(false);
        cbMore.setChecked(false);
    }

    private void checkedMore() {
        cbPosition.setChecked(false);
        cbExperience.setChecked(false);
        cbEducation.setChecked(false);
    }

    private void clearAll() {
        cbPosition.setChecked(false);
        cbExperience.setChecked(false);
        cbEducation.setChecked(false);
        cbMore.setChecked(false);
    }

    private void onFinish() {
        if (Frame.getVisibility() == View.VISIBLE) {
            Frame.setVisibility(View.GONE);
            clearAll();
            ClearAllFragment();
        } else {
            finish();
        }
    }

    private void clearAllSelected() {
        Frame.setVisibility(View.GONE);
        clearAll();
        ClearAllFragment();

        experience_from = "";
        experience_to = "";

        yearfrom = "";
        yearto = "";

        salaryfrom = "";
        salaryto = "";

        languages.clear();
        recommends.clear();
        selectedIds.clear();
        selectedNames.clear();
        educations.clear();
        educationNames.clear();
        languages.clear();
        recommends.clear();
        sexs.clear();
        //        sexs_texts.clear();//add
        //        languages_texts.clear();//add

        setPositionValue();
        setExperienceValue();
        setCbEducationValue();
        setMoreValue();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onFinish();
        }
        return false;
    }

    private void setPositionValue() {
        String value = "";
        for (String str : selectedNames) {
            value += str + "+";
        }
        if (TextUtils.isEmpty(value)) {
            cbPosition.setText("地点");
        } else {
            cbPosition.setText(value.substring(0, value.length() - 1));
        }

    }

    private void setExperienceValue() {
        /*if (TextUtils.isEmpty(experience_from)) {
            yearfrom = "0";
        } else {
            yearfrom = experience_from;
        }
        if (TextUtils.isEmpty(experience_to)) {
            yearto = "0";
        } else {
            yearto = experience_to;
        }*/
        yearfrom = experience_from;
        yearto = experience_to;
        if (yearfrom.equals("") && yearto.equals("")) {
            cbExperience.setText("经验");
        } else if (yearfrom.equals("") && !yearto.equals("")) {
            cbExperience.setText("不限-" + yearto);
        } else if (!yearfrom.equals("") && yearto.equals("")) {
            cbExperience.setText(yearfrom + "-不限");
        } else {
            cbExperience.setText(yearfrom + "-" + yearto);
        }

    }

    private void setCbEducationValue() {
        String value = "";
        for (String str : educationNames) {
            value += str + "+";
        }
        if (TextUtils.isEmpty(value)) {
            cbEducation.setText("学历");
        } else {
            cbEducation.setText(value.substring(0, value.length() - 1));
        }

    }

    private void setMoreValue() {

    }

    /**
     * 城市选择回调
     *
     * @param positions
     * @param names
     */
    @Override
    public void onPositionConfirm(List<String> positions, List<String> names) {
        selectedIds = positions;
        selectedNames = names;
        setPositionValue();
        onFinish();
        searches.clear();
        mResumeWhole.setPageindex(1);
        adapter.setNewData(null);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onSelected(List<String> positions, List<String> names) {

    }

    @Override
    public void onExperienceConfirm(String from, String to) {
        experience_from = from;
        Log.i("TAGs", experience_from + "experience_from--------------");
        experience_to = to;
        setExperienceValue();
        onFinish();
        searches.clear();
        mResumeWhole.setPageindex(1);
        adapter.setNewData(null);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onEducationConfirm(List<String> education, List<String> educationName) {
        educations = education;
        educationNames = educationName;
        setCbEducationValue();
        onFinish();
        searches.clear();
        mResumeWhole.setPageindex(1);
        adapter.setNewData(null);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onMoreConfirm(List<String> recommend, String from1, String to1, List<String> sex,
                              String from2, String to2, List<String> language, String page) {
        recommends = recommend;

        sexs = sex;
        //        sexs_texts = sexs_text;//add

        languages = language;
        //        languages_texts = languages_t;//add

        setMoreValue();
        onFinish();
        searches.clear();
        //        resumeRequest(etResumeSearch.getText().toString().trim());
        adapter.setNewData(null);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onOutside() {
        onFinish();
    }

    @Override
    public void onLoadMoreRequested() {
        if (response == null) {
            adapter.loadMoreEnd(false);
            return;
        }

        if (adapter.getData().size() >= response.getTotal()) {
            adapter.loadMoreEnd(false);
        } else {
            isLoadMore = true;
            //加载更多
            mResumeWhole.setPageindex(mResumeWhole.getPageindex()+1);
            resumeRequest(etResumeSearch.getText().toString().trim());
        }
    }

    @Override
    public void onConfirm(ResumeWhole resumeWhole) {
        this.mResumeWhole = resumeWhole;
        UtilHelper.outLog("onConfirm",mResumeWhole.toString());
        onFinish();
        searches.clear();
        adapter.setNewData(null);
        resumeRequest(etResumeSearch.getText().toString().trim());
    }
}
