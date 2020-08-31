package com.example.android.worldnewsapp.Fragments.SportFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.worldnewsapp.Activity.WebActivity;
import com.example.android.worldnewsapp.Adapter.FragmentNewsAdapter;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Fragments.SportFragment.SportViewModel.SportViewModel;
import com.example.android.worldnewsapp.Fragments.SportFragment.SportViewModel.SportViewModelFactory;
import com.example.android.worldnewsapp.R;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportFragment extends Fragment {

    private final static String API_KEY = DatabaseDetails.API_KEY;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SportViewModel sportViewModel;
    private SportViewModelFactory sportNewsViewModelFactory;
    private RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SportFragment newInstance(String param1, String param2) {
        SportFragment fragment = new SportFragment();
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
        View RootView = inflater.inflate(R.layout.fragment_sport, container, false);

        recyclerView = RootView.findViewById(R.id.business_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        sportNewsViewModelFactory = new SportViewModelFactory(getActivity().getApplication());
        sportViewModel = new ViewModelProvider(this, sportNewsViewModelFactory)
                .get(SportViewModel.class);

        final FragmentNewsAdapter liveNewsAdapter = new FragmentNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);

        final SwipeRefreshLayout pullToRefresh = RootView.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            sportViewModel.initData();
            sportViewModel.getAllNews().observe(getViewLifecycleOwner(), liveNews -> liveNewsAdapter.submitList(liveNews));
            pullToRefresh.setRefreshing(false);
        });

        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Please obtain your API KEY from newsapi.org first!", Toast.LENGTH_LONG).show();
        }

        sportViewModel.initData();
        sportViewModel.getAllNews().observe(getViewLifecycleOwner(), liveNews -> liveNewsAdapter.submitList(liveNews));

        liveNewsAdapter.setOnItemClickListener(newsLocal -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return RootView;
    }
}