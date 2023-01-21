package com.example.cardapp.presentation.fragments.drawer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.cardapp.R
import com.example.cardapp.presentation.adapters.MarketsRecyclerAdapter
import com.example.cardapp.databinding.FragmentMarketSearchBinding
import com.example.cardapp.extensions.navigateSafely
import com.example.cardapp.domain.model.MarketNetwork
import com.example.cardapp.presentation.viewmodels.AddCardFragmentViewModel
import com.example.cardapp.presentation.model.status.MarketDataStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMarketFragment : Fragment(R.layout.fragment_market_search) {

    private val binding by viewBinding(FragmentMarketSearchBinding::bind)
    private val viewModel: AddCardFragmentViewModel by activityViewModels()
    private val callback: (marketNetwork: MarketNetwork) -> Unit = {
        viewModel.setMarket(it)
        findNavController().navigateSafely(R.id.action_searchMarketFragment_to_addCardFragment)
    }

    private val args: SearchMarketFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        super.onViewCreated(view, savedInstanceState)
        binding.errorText.visibility = View.GONE
    }

    private fun setupObservers() {
        viewModel.marketsDataStatus.observe(viewLifecycleOwner) {
            when (it) {
                MarketDataStatus.Fail -> {
                    toastError(getString(R.string.error))
                    binding.errorText.visibility = View.VISIBLE
                    stopLoading()
                }
                is MarketDataStatus.Success -> {
                    applyMarkets(it.marketNetworks)
                    if (it.marketNetworks.isEmpty()) {
                        binding.errorText.visibility = View.VISIBLE
                        binding.errorText.text = getString(R.string.no_available_markets)
                    }
                }
                MarketDataStatus.Null -> downloadMarkets()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetMarketDataStatus()
    }

    private fun applyMarkets(marketNetworks: List<MarketNetwork>) {
        binding.marketsRecycler.adapter = MarketsRecyclerAdapter(marketNetworks, callback)
        stopLoading()
    }

    private fun downloadMarkets() {
        viewModel.downloadMarkets(args.marketNetIds)
        startLoading()
    }

    private fun toastError(msg: String) =
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()


    private fun startLoading() {
        binding.errorText.visibility = View.GONE
        binding.marketsRecycler.showShimmerAdapter()
    }

    private fun stopLoading() {
        binding.marketsRecycler.hideShimmerAdapter()
    }
}
