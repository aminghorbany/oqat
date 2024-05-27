package com.amin.oqatsharee.ui.home

import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.amin.oqatsharee.R
import com.amin.oqatsharee.connection.NetworkBroadcastReceiver
import com.amin.oqatsharee.databinding.FragmentHomeBinding
import com.amin.oqatsharee.utils.Constants
import com.amin.oqatsharee.utils.goneWidget
import com.amin.oqatsharee.utils.showLongToast
import com.amin.oqatsharee.utils.showShortToast
import com.amin.oqatsharee.utils.showWidget
import com.amin.oqatsharee.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var job : Job
    private lateinit var receiver : NetworkBroadcastReceiver
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiver = NetworkBroadcastReceiver()
        requireContext().registerReceiver(receiver, IntentFilter(NetworkBroadcastReceiver.ACTION_CONNECTIVITY_CHANGE))
        binding.apply {
            btnSearch.setOnClickListener {
                val cityText = cityEdt.text.toString()
                homeViewModel.getAzan(Constants.API_TOKEN, cityText)
            }
            //regex
            cityEdt.addTextChangedListener(object  : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val inputText = s.toString()
                    val textPattern = "[a-zA-Z]+".toRegex()

                    if (inputText.isNotEmpty() && textPattern.containsMatchIn(inputText)) {
                        val newText = inputText.replace(textPattern, "")
                        cityEdt.removeTextChangedListener(this)
                        cityEdt.setText(newText)
                        cityEdt.addTextChangedListener(this)
                        cityEdt.setSelection(newText.length)
                        requireContext().showShortToast("لطفا فارسی بنویسید")
                    }
                }
            })
            //regex
            cityEdt.addTextChangedListener(object  : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val inputText = s.toString()
                    val charPattern = "[!-~]+".toRegex()

                    if (inputText.isNotEmpty() && charPattern.containsMatchIn(inputText)) {
                        val newText = inputText.replace(charPattern, "")
                        cityEdt.removeTextChangedListener(this)
                        cityEdt.setText(newText)
                        cityEdt.addTextChangedListener(this)
                        cityEdt.setSelection(newText.length)
                        requireContext().showShortToast("لطفا فارسی بنویسید")
                    }
                }
            })
            //load data
            homeViewModel.getAzanLiveData.observe(viewLifecycleOwner) {
                requireContext().showWidget(txtCityName)
                requireContext().showWidget(constraintLayoutFirst)
                requireContext().showWidget(constraintLayoutSecond)
                requireContext().showWidget(sparkLayout)
                lottieAnimationView.playAnimation()
                lottieAnimationView2.playAnimation()
                lottieAnimationView3.playAnimation()
                azanDetail.apply {
                    txtTimeSobh.text = it.result.azanSobh
                    txtTimeZohr.text = it.result.azanZohre
                    txtTimeMaqreb.text = it.result.azanMaghreb
                }
                azanDetail1.apply {
                    txtTimeToloeAftab.text = it.result.toloeAftab
                    txtTimeQorobAftab.text = it.result.ghorobAftab
                    txtTimeNimeShab.text = it.result.nimeShabeSharie
                }
                txtCityName.text = "اوقات شرعی به افق " + it.result.city
                cityEdt.setText("")
            }
            //loading
            homeViewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    requireContext().showWidget(azanLoading)
                } else {
                    requireContext().goneWidget(azanLoading)
                }
            }
            //notFoundState
            homeViewModel.notFoundState.observe(viewLifecycleOwner) {
                if (it){
                    requireContext().showLongToast("یافت نشد")
                }
            }

            // first run load background
            imgBackground.load(bgList().random()) {
                crossfade(true)
                crossfade(600)
            }
            //network problem
            receiver.networkLiveData.observe(viewLifecycleOwner){
                if (it){
                    requireContext().goneWidget(networkLayout)
                }else{
                    requireContext().showWidget(networkLayout)
                }
            }

            job = CoroutineScope(Dispatchers.Main).launch {
                repeat(200){
                    delay(7000)
                    imgBackground.load(bgList().random()) {
                        crossfade(true)
                        crossfade(3000)
                    }
                }
            }
        }

    }

    private fun bgList(startId: Int = R.drawable.bg01, count: Int = 14): ArrayList<Int> {
        val myList = arrayListOf<Int>()
        for (i in 0 until count) {
            val resourceId = startId + i
            myList.add(resourceId)
        }
        return myList
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.Main).launch {
            job.cancelAndJoin()
        }
        requireContext().unregisterReceiver(receiver)
    }
}