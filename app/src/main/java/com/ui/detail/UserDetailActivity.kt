package com.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.data.response.DetailUserResponse
import com.example.mysubmission1.R
import com.example.mysubmission1.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var userDetailBinding: ActivityUserDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var name: String? = null
    var _isChecked = false
    private var userId: Int = 0
    private var username: String = ""
    private var userUrl: String = ""

    companion object{
        const val USER_NAME = ""
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(userDetailBinding.root)


        name = intent.getStringExtra(USER_NAME)
        detailViewModel.getDetailUser(name.toString())

        detailViewModel.user.observe(this){user ->
            if (user != null){
                setUserDetailData(user)
            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailViewModel.isFavoriteUser.observe(this){isFavorite ->
            if (isFavorite){
                userDetailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            }else{
                userDetailBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
        userDetailBinding.fabFavorite.setOnClickListener(this)

    }

    private fun setUserDetailData(user: DetailUserResponse) {
        userDetailBinding.tvName.visibility = View.VISIBLE
        userDetailBinding.tvName.text = user.name

        userDetailBinding.tvUserNameDetail.visibility = View.VISIBLE
        userDetailBinding.tvUserNameDetail.text = getString(R.string.username_detail, user.login)

        userDetailBinding.tvUserFollowersDetail.visibility = View.VISIBLE
        userDetailBinding.tvUserFollowersDetail.text = getString(R.string.followers, user.followers)

        userDetailBinding.tvUserFollowingDetail.visibility = View.VISIBLE
        userDetailBinding.tvUserFollowingDetail.text = getString(R.string.following, user.following)

        userDetailBinding.ivUserPictureDetail.visibility = View.VISIBLE
        Glide.with(userDetailBinding.ivUserPictureDetail.context).load(user.avatarUrl)
            .into(userDetailBinding.ivUserPictureDetail)


        userDetailBinding.viewPager.visibility = View.VISIBLE
        userDetailBinding.tabs.visibility = View.VISIBLE

        userId = user.id
        username = user.login
        userUrl = user.avatarUrl


        val followerAndFollowingPagerAdapter = FollowersandFollowingPagerAdapter(this)
        followerAndFollowingPagerAdapter.username = name.toString()

        val viewPager: ViewPager2 = userDetailBinding.viewPager
        viewPager.adapter = followerAndFollowingPagerAdapter

        val tabLayout: TabLayout = userDetailBinding.tabs
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showLoading(it: Boolean?) {
        userDetailBinding.progressBar2.visibility = if (it == true) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_favorite->{
                _isChecked = !_isChecked
                if (_isChecked){
                    detailViewModel.addFavUser(userId, username, userUrl)
                }else{
                    detailViewModel.deleteFavUser(userId)
                }
            }
        }
    }


}