package com.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.response.FollowersAndFollowingItem
import com.example.mysubmission1.databinding.FragmentFollowBinding

class FollowersAndFollowingFragment : Fragment(){

    private val detailViewModel by activityViewModels<DetailViewModel>()

    companion object {
        const val ARG_POSITION: String = "arg_position"
        const val ARG_USERNAME: String = "arg_username"
    }

    private lateinit var followersandFollowingBinding: FragmentFollowBinding
    private var position: Int? = null
    private var username: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        followersandFollowingBinding = FragmentFollowBinding.inflate(layoutInflater)
        return followersandFollowingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followersandFollowingBinding.rvUsersFollowersFollowing.layoutManager = LinearLayoutManager(requireContext())
        detailViewModel.getFollowersUser(username.toString())
        detailViewModel.getFollowingUser(username.toString())

        if (position == 1) {
            detailViewModel.userFollowers.observe(viewLifecycleOwner) { followers ->
                setFollowersData(followers)
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) { following ->
                if (following != null) {
                    setFollowingData(following)
                }
            }
        }
    }

    private fun setFollowingData(following: List<FollowersAndFollowingItem>) {
        val adapter = FollowersandFollowingAdapter()
        adapter.submitList(following)
        followersandFollowingBinding.rvUsersFollowersFollowing.adapter = adapter
        followersandFollowingBinding.rvUsersFollowersFollowing.visibility = View.VISIBLE
    }

    private fun setFollowersData(followers: List<FollowersAndFollowingItem>) {
        val adapter = FollowersandFollowingAdapter()
        adapter.submitList(followers)
        followersandFollowingBinding.rvUsersFollowersFollowing.adapter = adapter
        followersandFollowingBinding.rvUsersFollowersFollowing.visibility = View.VISIBLE
    }

}