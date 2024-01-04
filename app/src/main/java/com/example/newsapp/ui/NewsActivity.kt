package com.example.newsapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRespository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay

class NewsActivity : AppCompatActivity() {

    private lateinit var binding :  ActivityNewsBinding

    lateinit var  viewModel : NewsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRespository(ArticleDatabase(this))
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRespository = newsRepository)

        viewModel=ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)




        val navController=  findNavController(R.id.mainHostFragment)

        binding.bottomNavigationView.setupWithNavController(navController)
    }





}