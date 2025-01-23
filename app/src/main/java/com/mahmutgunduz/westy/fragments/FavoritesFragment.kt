package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mahmutgunduz.westy.adapters.FavoritesAdapter
import com.mahmutgunduz.westy.databinding.FragmentFavoritesBinding
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesDataBase
import com.mahmutgunduz.westy.dataBase.FavoritesData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import android.database.sqlite.SQLiteException
import com.mahmutgunduz.westy.R

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var dao: FavoritesDao
    private lateinit var adapter: FavoritesAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = FavoritesDataBase.getDatabase(requireContext()).noteDao()
        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter(ArrayList(), requireContext(), dao)
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@FavoritesFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeFavorites() {
        compositeDisposable.add(
            dao.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favorites ->
                    updateUI(favorites)
                }, { error ->
                    handleError(error)
                })
        )
    }

    private fun updateUI(favorites: List<FavoritesData>) {
        if (view == null) return
        
        if (favorites.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.rcv.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.rcv.visibility = View.VISIBLE
            adapter.updateList(favorites)
        }
    }

    private fun handleError(error: Throwable) {
        if (view == null) return
        
        binding.emptyView.visibility = View.VISIBLE
        binding.rcv.visibility = View.GONE
        
        val errorMessage = when (error) {
            is IOException -> requireContext().getString(R.string.network_error)
            is SQLiteException -> requireContext().getString(R.string.database_error)
            else -> requireContext().getString(R.string.general_error)
        }
        
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}