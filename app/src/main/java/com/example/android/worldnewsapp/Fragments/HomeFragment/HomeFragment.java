package com.example.android.worldnewsapp.Fragments.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Fragments.HomeFragment.HomeViewModel.HomeViewModel;
import com.example.android.worldnewsapp.Fragments.HomeFragment.HomeViewModel.HomeViewModelFactory;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.Utils.AlertDialogManager;
import com.example.android.worldnewsapp.Utils.ConnectionDetector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private final static String API_KEY = DatabaseDetails.API_KEY;
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    private HomeViewModel homeViewModel;
    private HomeViewModelFactory homeViewModelFactory;
    private RecyclerView generalRecyclerView;
    private RecyclerView entertainmentRecyclerView;
    private RecyclerView topSportRecyclerView;
    private RecyclerView topBusinessRecyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_home, container, false);

        /*generalRecyclerView = RootView.findViewById(R.id.general_recycler_view);
        generalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        generalRecyclerView.setHasFixedSize(true);

        entertainmentRecyclerView = RootView.findViewById(R.id.entertainment_recycler_view);
        entertainmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entertainmentRecyclerView.setHasFixedSize(true);

        topBusinessRecyclerView = RootView.findViewById(R.id.top_business_recycler_view);
        topBusinessRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        topBusinessRecyclerView.setHasFixedSize(true);

        topSportRecyclerView = RootView.findViewById(R.id
        .top_sport_recycler_view);
        topSportRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        topSportRecyclerView.setHasFixedSize(true);

6
        homeViewModelFactory = new HomeViewModelFactory(getActivity().getApplication());
        homeViewModel = new ViewModelProvider(this, homeViewModelFactory)
                .get(HomeViewModel.class);

        final FragmentNewsAdapter generalLiveNewsAdapter = new FragmentNewsAdapter();
        final FragmentNewsAdapter entertainmentLiveNewsAdapter = new FragmentNewsAdapter();
        final FragmentNewsAdapter businessLiveNewsAdapter = new FragmentNewsAdapter();
        final FragmentNewsAdapter sportLiveNewsAdapter = new FragmentNewsAdapter();

        generalRecyclerView.setAdapter(generalLiveNewsAdapter);
        entertainmentRecyclerView.setAdapter(entertainmentLiveNewsAdapter);
        topBusinessRecyclerView.setAdapter(businessLiveNewsAdapter);
        topSportRecyclerView.setAdapter(sportLiveNewsAdapter);


        final SwipeRefreshLayout pullToRefresh = RootView.findViewById(R.id.homePullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {

            cd = new ConnectionDetector(getContext().getApplicationContext());

            // Check if Internet present
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                homeViewModel.initData();
                homeViewModel.getAllGeneralNews().observe(getViewLifecycleOwner(), newsLocals -> generalLiveNewsAdapter.submitList(newsLocals));
                homeViewModel.getAllEntertainmentNews().observe(getViewLifecycleOwner(), newsLocals -> entertainmentLiveNewsAdapter.submitList(newsLocals));
                homeViewModel.getAllTopBusinessNews().observe(getViewLifecycleOwner(), newsLocals -> businessLiveNewsAdapter.submitList(newsLocals));
                homeViewModel.getAllTopSportNews().observe(getViewLifecycleOwner(), newsLocals -> sportLiveNewsAdapter.submitList(newsLocals));
                pullToRefresh.setRefreshing(false);
            }
            pullToRefresh.setRefreshing(false);
        });

        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Please obtain your API KEY from newsapi.org first!", Toast.LENGTH_LONG).show();
        }

        //homeViewModel.initData();
        homeViewModel.getAllGeneralNews().observe(getViewLifecycleOwner(),liveNews -> entertainmentLiveNewsAdapter.submitList(liveNews));
        homeViewModel.getAllEntertainmentNews().observe(getViewLifecycleOwner(), newsLocals -> entertainmentLiveNewsAdapter.submitList(newsLocals));
        homeViewModel.getAllTopBusinessNews().observe(getViewLifecycleOwner(), newsLocals -> businessLiveNewsAdapter.submitList(newsLocals));
        homeViewModel.getAllTopSportNews().observe(getViewLifecycleOwner(), newsLocals -> sportLiveNewsAdapter.submitList(newsLocals));


        generalLiveNewsAdapter.setOnItemClickListener(newsLocal -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
            startActivity(intent);
        });

        entertainmentLiveNewsAdapter.setOnItemClickListener(newsLocal -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
            startActivity(intent);
        });

        businessLiveNewsAdapter.setOnItemClickListener(newsLocal -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
            startActivity(intent);
        });

        sportLiveNewsAdapter.setOnItemClickListener(newsLocal -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
            startActivity(intent);
        });*/


        return RootView;
    }


    public void viewAllNews(View view) {

    }

}