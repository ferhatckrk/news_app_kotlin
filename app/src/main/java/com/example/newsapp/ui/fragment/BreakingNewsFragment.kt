package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {


    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    val TAG="BreakingNewsFragment"


    private val viewModel by viewModels<NewsViewModel>()
    lateinit var newsAdapter: NewsAdapter

    lateinit var binding : FragmentBreakingNewsBinding



    val TAG = "BreakingNewsFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.rvBreakingNews)
        progressBar = view.findViewById<ProgressBar>(R.id.paginationProgressBar)


        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()


        viewModel .breakingnews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgresBar()
                    response.data?.let {
                        newsResponse->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                   hideProgresBar()
                    response.message?.let {
                        message->
                         Log.e(TAG, "An error occurrend: $message" )
                    }
                }
                is Resource.Loading-> {
                    showProgressBar()
                }
            }
        })
    }



    private fun hideProgresBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }

         setupRecyclerView()

        viewModel.getBreakingNews("us")




        newsAdapter.setOnItemClickListener {

            System.out.println("clicked!")

            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFrament, bundle
            )

        }

        //   lifecycleScope.launch {
        //       viewModel?.breakingnews?.collect {
        //           when (it) {
        //               is Resource.Loading -> {
        //                   showProgressBar()
        //               }
        //               is Resource.Error -> {
        //                   Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        //               }
        //               is Resource.Success -> {
        //                   hideProgressBar()
        //               }
        //           }
        //       }
        //   }


        lifecycleScope.launch {
            viewModel.breakingnews.collect { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles?.toList())
                            //val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                            //   isLastPage = viewModel.breakingNewsPage == totalPages
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.e(TAG, "An error occured: $message")
                        }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }
//
//      viewModel.let {
//            it?.breakingnews?.observe(viewLifecycleOwner, Observer { response ->
//                when (response) {
//                    is Resource.Success -> {
//                        hideProgressBar()
//                        response.data?.let { newsResponse ->
//                            newsAdapter.differ.submitList(newsResponse.articles?.toList())
//                            //val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
//                            //   isLastPage = viewModel.breakingNewsPage == totalPages
//                        }
//                    }
//
//                    is Resource.Error -> {
//                        hideProgressBar()
//                        response.message?.let { message ->
//                            Log.e(TAG, "An error occured: $message")
//                        }
//                    }
//
//                    is Resource.Loading -> {
//                        showProgressBar()
//                    }
//                }
//            })
//        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE


    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

            // addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }


}


