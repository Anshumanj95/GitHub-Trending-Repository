package com.example.gittrendingapi.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gittrendingapi.MainActivity
import com.example.gittrendingapi.R
import com.example.gittrendingapi.RepositoryViewModel
import com.example.gittrendingapi.adapter.RepoAdapter
import com.example.gittrendingapi.databinding.FragmentRepoBinding

class RepoFragment : Fragment() {

    private var _binding:FragmentRepoBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel:RepositoryViewModel
    lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentRepoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel=(activity as MainActivity).viewModel
        setupRecyclerView()
        viewModel.allrepo.observe(viewLifecycleOwner, { response ->
            showShimmer()
            response.let {
                hideShimmer()
                repoAdapter.differ.submitList(it)

            }
        })
        binding.refresh.setOnRefreshListener {
            showShimmer()
            binding.refresh.isRefreshing=false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_fork->{
                displayByForks()
                true
            }
            R.id.menu_watcher->{
                displayByWatcher()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun displayByForks(){

        viewModel.allRepoByFork.observe(viewLifecycleOwner,{response ->
            showShimmer()
            response.let {
                hideShimmer()
                repoAdapter.differ.submitList(it)

            }
        })

    }
    private fun displayByWatcher(){
        viewModel.allRepoByWatcher.observe(viewLifecycleOwner,{response ->
            showShimmer()
            response.let {
                hideShimmer()
                repoAdapter.differ.submitList(it)

            }
        })
    }


    private fun hideShimmer(){
        binding.shimmerLayout.visibility=View.GONE
    }
    private fun showShimmer(){
        binding.shimmerLayout.startShimmer()
    }
    private fun setupRecyclerView(){
        repoAdapter= RepoAdapter{}
        binding.recyclerview.apply {
            adapter =repoAdapter
            layoutManager= LinearLayoutManager(activity)
            setHasFixedSize(true)

        }
    }

}