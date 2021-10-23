package com.example.gittrendingapi.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gittrendingapi.R
import com.example.gittrendingapi.adapter.RepoAdapter
import com.example.gittrendingapi.databinding.FragmentRepoBinding
import com.example.gittrendingapi.ui.MainActivity
import com.example.gittrendingapi.ui.viewmodel.RepositoryViewModel

class RepoFragment : Fragment() {

    private var _binding:FragmentRepoBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel: RepositoryViewModel
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
        if(viewModel.hasInternetConnection())
            fromApiCall()
        else
            fromRoom()
        binding.refresh.setOnRefreshListener {
            showShimmer()
            binding.refresh.isRefreshing = false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_fork->{
                if(viewModel.hasInternetConnection())
                    fromApiCallByFork()
                else
                    displayByForks()
                true
            }
            R.id.menu_watcher->{
                if(viewModel.hasInternetConnection())
                    fromApiCallByWatcher()
                else
                    displayByWatcher()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    fun fromApiCall() {
        viewModel.repositories.observe(viewLifecycleOwner, { response ->
            showShimmer()
            response.let { ApiResponse ->
                ApiResponse.data?.let { it ->
                    hideShimmer()
                    repoAdapter.differ.submitList(it.items)
                }

            }
        })

    }
    fun fromRoom(){
        viewModel.allrepo.observe(viewLifecycleOwner, { response ->
            showShimmer()
            response.let { it ->
                hideShimmer()
                repoAdapter.differ.submitList(it)

            }
        })

    }
    fun fromApiCallByFork() {
        viewModel.repositories.observe(viewLifecycleOwner, { response ->
            showShimmer()
            response.let { ApiResponse ->
                ApiResponse.data?.let { it ->
                    hideShimmer()

                    repoAdapter.differ.submitList(it.items.sortedWith(compareBy { it.forks_count }))
                }

            }
        })

    }
    fun fromApiCallByWatcher() {
        viewModel.repositories.observe(viewLifecycleOwner, { response ->
            showShimmer()
            response.let { ApiResponse ->
                ApiResponse.data?.let { it ->
                    hideShimmer()

                    repoAdapter.differ.submitList(it.items.sortedWith(compareBy { it.watchers_count }))
                }

            }
        })

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
        repoAdapter= RepoAdapter()
        binding.recyclerview.apply {
            adapter =repoAdapter
            layoutManager= LinearLayoutManager(activity)
            setHasFixedSize(true)

        }
    }

}