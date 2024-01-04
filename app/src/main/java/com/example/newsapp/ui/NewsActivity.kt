package com.example.newsapp.ui


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsBinding

import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRespository
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding


    lateinit var  viewModel : NewsViewModel





    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       // val newsRepository = NewsRespository(ArticleDatabase(this))
     //   val viewModelProviderFactory= NewsViewModelProviderFactory(  newsRepository)

      //  viewModel=ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)


        val navController=  findNavController(R.id.mainHostFragment)

         binding.bottomNavigationView.setupWithNavController(navController)
    }









}