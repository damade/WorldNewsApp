package com.example.android.worldnewsapp.Fragments.BusinessFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.worldnewsapp.Activity.WebActivity;
import com.example.android.worldnewsapp.Adapter.FragmentNewsAdapter;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Fragments.BusinessFragment.BusinessViewModel.BusinessViewModel;
import com.example.android.worldnewsapp.Fragments.BusinessFragment.BusinessViewModel.BusinessViewModelFactory;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.Utils.AlertDialogManager;
import com.example.android.worldnewsapp.Utils.ConnectionDetector;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusinessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessFragment extends Fragment {

    private final static String API_KEY = DatabaseDetails.API_KEY;
    private BusinessViewModel businessNewsViewModel;
    private BusinessViewModelFactory businessNewsViewModelFactory;
    private RecyclerView recyclerView;


    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BusinessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusinessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusinessFragment newInstance(String param1, String param2) {
        BusinessFragment fragment = new BusinessFragment();
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
        View RootView = inflater.inflate(R.layout.fragment_business, container, false);

        recyclerView = RootView.findViewById(R.id.business_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        businessNewsViewModelFactory = new BusinessViewModelFactory(getActivity().getApplication());
        businessNewsViewModel = new ViewModelProvider(this, businessNewsViewModelFactory)
                .get(BusinessViewModel.class);

        final FragmentNewsAdapter liveNewsAdapter = new FragmentNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);

        final SwipeRefreshLayout pullToRefresh = RootView.findViewById(R.id.businessPullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            cd = new ConnectionDetector(getContext().getApplicationContext());

            // Check if Internet present
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                businessNewsViewModel.initData();
                businessNewsViewModel.getAllNews().observe(getViewLifecycleOwner(), liveNews -> liveNewsAdapter.submitList(liveNews));
                pullToRefresh.setRefreshing(false);
            }
            pullToRefresh.setRefreshing(false);
        });

        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Please obtain your API KEY from newsapi.org first!", Toast.LENGTH_LONG).show();
        }

        //businessNewsViewModel.initData();
        businessNewsViewModel.getAllNews().observe(getViewLifecycleOwner(), liveNews -> liveNewsAdapter.submitList(liveNews));

        liveNewsAdapter.setOnItemClickListener(newsLocal -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return RootView;
    }
}