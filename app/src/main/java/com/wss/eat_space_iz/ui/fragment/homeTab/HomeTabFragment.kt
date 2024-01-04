package com.wss.eat_space_iz.ui.fragment.homeTab

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wss.eat_space_iz.R
import com.wss.eat_space_iz.data.networkModels.tummoc.Item
import com.wss.eat_space_iz.data.networkModels.tummoc.ShoppingResponse
import com.wss.eat_space_iz.databinding.FragmentHomeTabBinding
import com.wss.eat_space_iz.ui.adapter.profileTab.CategoriesAdapter
import com.wss.eat_space_iz.ui.adapter.profileTab.HistoryAdapter
import com.wss.eat_space_iz.ui.adapter.profileTab.PopularNearYouAdapter
import com.wss.eat_space_iz.ui.common.ApiRenderState
import com.wss.eat_space_iz.ui.fragment.base.BaseFrag
import com.wss.eat_space_iz.ui.viewModel.homeTab.HomeTabViewModel
import com.wss.eat_space_iz.utils.Drawables
import com.wss.eat_space_iz.utils.Layouts
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeTabFragment :
    BaseFrag<FragmentHomeTabBinding, HomeTabViewModel>(Layouts.fragment_home_tab) {


    private lateinit var mPopularNearYouAdapter: PopularNearYouAdapter
    private lateinit var mPopularNearYouDataList: List<Any>
    private var mHistoryAdapter: HistoryAdapter? = null
    private var mHistoryDataList= ArrayList<Any>()
    private val itemList = listOf(
        Item("", 1,"Today Click",1.0),
        Item("", 2,"Tap Location", 1.0),
        Item("", 3,"Top source",1.0)
    )
    companion object {
        private val lineSet = listOf(
            "label1" to 20f,
            "label2" to 5f,
            "label3" to 4f,
            "label4" to 16f,
            "label5" to 11f,
            "label6" to 29f,
            "label7" to 6f,
            "label8" to 3f,
            "label9" to 8f,
            "label10" to 7f,
            "label11" to 5f,
            "label12" to 2f
        )

        private const val animationDuration = 1000L
    }

    private var mCategoriesAdapter: CategoriesAdapter? = null
    private var mCategoriesDataList= ArrayList<Item>()
    private var isVisible = true
    private var isVisible2 = true
    private var isVisible3 = true
    private var isVisible4 = true
    private var isVisible5 = true

    override val hasProgress: Boolean = false
    override fun getViewBinding() = FragmentHomeTabBinding.inflate(layoutInflater)
    override val vm: HomeTabViewModel by viewModels()

    override fun init() {
        setUpListener()
       // setupPopularNearYouRecyclerView(itemList)
        tummoc()
        lineGraph()
    }

    private fun tummoc() {
        vm.tummoc()
    }

    private fun lineGraph() {

        binding.homeCategories.lineChart.gradientFillColors =
            intArrayOf(
                Color.parseColor("#81FFFFFF"),
                Color.TRANSPARENT
            )
        binding.homeCategories.lineChart.animation.duration = animationDuration
        binding.homeCategories.lineChart.onDataPointTouchListener = { index, _, _ ->

        }
        binding.homeCategories.lineChart.animate(lineSet)


    }

    private fun setUpListener() {
        with(binding) {
            homeDealOfTheDay.tvOngoing.setOnClickListener(this@HomeTabFragment)
            homeDealOfTheDay.tvHistory.setOnClickListener(this@HomeTabFragment)
            homeAllRestaurents.tvAllRestaurants.setOnClickListener(this@HomeTabFragment)
            homeToolbar.ivLocationDownArrow.setOnClickListener(this@HomeTabFragment)
            homeToolbar.ivHomeNotification.setOnClickListener(this@HomeTabFragment)
            fabAddReview.setOnClickListener(this@HomeTabFragment)
            homeCategories.downArrow.setOnClickListener(this@HomeTabFragment)
            homeCategories2.downArrow.setOnClickListener(this@HomeTabFragment)
            homeCategories3.downArrow.setOnClickListener(this@HomeTabFragment)
            homeCategories4.downArrow.setOnClickListener(this@HomeTabFragment)
            homeCategories5.downArrow.setOnClickListener(this@HomeTabFragment)
        }
    }

    override fun renderState(apiRenderState: ApiRenderState) {
        when (apiRenderState) {
            is ApiRenderState.Loading -> {
                showProgress()
            }
            is ApiRenderState.ApiSuccess<*> -> {
                when (apiRenderState.result) {
                    is ShoppingResponse -> {
                        val model = apiRenderState.result
                        model.status.let {
                           // when (it) {
                              //  getString(Strings.api_success_status) -> {

                            val foodItemsList = ArrayList<Item>()
                            val beveragesItemsList = ArrayList<Item>()
                            val hygieneEssentialsItemsList = ArrayList<Item>()
                            val poojaDailyNeedsItemsList = ArrayList<Item>()
                            val electronicItemsList = ArrayList<Item>()

                            for (category in model.data) {
                                when (category.name) {
                                    "Food" -> {
                                        binding.homeCategories.categoryName.text = category.name
                                        foodItemsList.addAll(category.items)
                                    }
                                    "Beverages" -> {
                                        binding.homeCategories2.categoryName.text = category.name
                                        beveragesItemsList.addAll(category.items)
                                    }
                                    "Hygiene Essentials" -> {
                                        binding.homeCategories3.categoryName.text = category.name
                                        hygieneEssentialsItemsList.addAll(category.items)
                                    }
                                    "Pooja Daily Needs" -> {
                                        binding.homeCategories4.categoryName.text = category.name
                                        poojaDailyNeedsItemsList.addAll(category.items)
                                    }
                                    "Electronic Items" -> {
                                        binding.homeCategories5.categoryName.text = category.name
                                        electronicItemsList.addAll(category.items)
                                    }
                                }
                            }

                            setupCategoriesRecyclerView(foodItemsList)
                            setupCategoriesRecyclerView2(beveragesItemsList)
                            setupCategoriesRecyclerView3(hygieneEssentialsItemsList)
                            setupCategoriesRecyclerView4(poojaDailyNeedsItemsList)
                            setupCategoriesRecyclerView5(electronicItemsList)
                             // }
                           //     else -> {}
                         //   }
                        }
                    }
                }
            }
            is ApiRenderState.ValidationError -> {
                apiRenderState.message?.let {
                    errorToast(getString(it))
                }
            }
            is ApiRenderState.ApiError<*> -> {
                errorToast(apiRenderState.error.toString())
            }
            else -> {}
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        with(binding)
        {
            when (v) {
                homeCategories.downArrow ->
                {
                    if (isVisible) {
                        homeCategories.rvPopularNearYou.visibility = View.GONE
                    } else {
                        homeCategories.rvPopularNearYou.visibility = View.VISIBLE
                    }
                    isVisible = !isVisible
                }
                homeCategories2.downArrow ->
                {
                    //homeCategories.rvPopularNearYou.visibility = View.GONE

                    if (isVisible2) {
                        homeCategories2.rvPopularNearYou.visibility = View.GONE
                    } else {
                        homeCategories2.rvPopularNearYou.visibility = View.VISIBLE
                    }
                    isVisible2 = !isVisible2


                }
                homeCategories3.downArrow ->
                {
                   // homeCategories.rvPopularNearYou.visibility = View.GONE

                    if (isVisible3) {
                        homeCategories3.rvPopularNearYou.visibility = View.GONE
                    } else {
                        homeCategories3.rvPopularNearYou.visibility = View.VISIBLE
                    }
                    isVisible3 = !isVisible3

                }
                homeCategories4.downArrow ->
                {
                    //homeCategories.rvPopularNearYou.visibility = View.GONE

                    if (isVisible4) {
                        homeCategories4.rvPopularNearYou.visibility = View.GONE
                    } else {
                        homeCategories4.rvPopularNearYou.visibility = View.VISIBLE
                    }
                    isVisible4 = !isVisible4


                }
                homeCategories5.downArrow ->
                {
                    //homeCategories.rvPopularNearYou.visibility = View.GONE

                    if (isVisible5) {
                        homeCategories5.rvPopularNearYou.visibility = View.GONE
                    } else {
                        homeCategories5.rvPopularNearYou.visibility = View.VISIBLE
                    }
                    isVisible5 = !isVisible5


                }

                fabAddReview ->
                {
                    showMyDialog()
                }
                homeToolbar.ivLocationDownArrow -> {
                    val action = HomeTabFragmentDirections.actionHomeTabFragmentToAllRestaurantListFragment()
                    findNavController().navigate(action)
                }
                homeToolbar.ivHomeNotification ->
                {
                    val action = HomeTabFragmentDirections.actionHomeTabFragmentToCartTabFragment()
                    findNavController().navigate(action)
                }
                homeDealOfTheDay.tvHistory -> {
                    homeDealOfTheDay.tvHistory.background = ContextCompat.getDrawable(
                        requireContext(), Drawables.tv_order_type_bg
                    )
                    homeDealOfTheDay.tvOngoing.background = null
                    setupHistoryRecyclerView(mHistoryDataList)
                }
                homeAllRestaurents.tvAllRestaurants -> {

                    val text = "This is a test"
                    val toNumber = "8297368106"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("http://api.whatsapp.com/send?phone=$toNumber&text=$text")
                    startActivity(intent)

                }
                else -> {}
            }
        }
    }

    private fun setupCategoriesRecyclerView(data: ArrayList<Item>) {
        mCategoriesDataList = data
        if (mCategoriesDataList.isNotEmpty()) {
            mCategoriesAdapter = CategoriesAdapter(data)
            binding.homeCategories.rvPopularNearYou.adapter = mCategoriesAdapter
        }
    }
    private fun setupCategoriesRecyclerView2(data: ArrayList<Item>) {
        mCategoriesDataList = data
        if (mCategoriesDataList.isNotEmpty()) {
            mCategoriesAdapter = CategoriesAdapter(data)
            binding.homeCategories2.rvPopularNearYou.adapter = mCategoriesAdapter
        }
    }
    private fun setupCategoriesRecyclerView3(data: ArrayList<Item>) {
        mCategoriesDataList = data
        if (mCategoriesDataList.isNotEmpty()) {
            mCategoriesAdapter = CategoriesAdapter(data)
            binding.homeCategories3.rvPopularNearYou.adapter = mCategoriesAdapter
        }
    }
    private fun setupCategoriesRecyclerView4(data: ArrayList<Item>) {
        mCategoriesDataList = data
        if (mCategoriesDataList.isNotEmpty()) {
            mCategoriesAdapter = CategoriesAdapter(data)
            binding.homeCategories4.rvPopularNearYou.adapter = mCategoriesAdapter
        }
    }
    private fun setupCategoriesRecyclerView5(data: ArrayList<Item>) {
        mCategoriesDataList = data
        if (mCategoriesDataList.isNotEmpty()) {
            mCategoriesAdapter = CategoriesAdapter(data)
            binding.homeCategories5.rvPopularNearYou.adapter = mCategoriesAdapter
        }
    }
    private fun setupHistoryRecyclerView(data: ArrayList<Any>) {
        mHistoryDataList = data
        if (mHistoryDataList.isNotEmpty()) {
            //mHistoryAdapter = HistoryAdapter(data)
            binding.homeRecommended.rvCategories.adapter = mHistoryAdapter
        }
    }
    private fun setupPopularNearYouRecyclerView(data: List<Item>) {

        mPopularNearYouDataList = data
        if (mPopularNearYouDataList.isNotEmpty()) {
            mPopularNearYouAdapter = PopularNearYouAdapter(data)
            binding.homePopularNearYou.rvPopularNearYou.adapter = mPopularNearYouAdapter
        }
        /*   mPopularNearYouAdapter.setOnItemClickListener(object :
               PopularNearYouAdapter.OnItemClickListener {
               override fun onItemClick(position: Int, v: View) {
                   openRestaurantDetails(position)
               }
           })*/

    }
    private fun openRestaurantDetails(position: Int) {
       /* val restaurantId = mPopularNearYouDataList?.get(position)
        val action = restaurantId?.let { HomeTabFragmentDirections.actionHomeTabFragmentToScheduleTripFragment() }
        if (action != null) {
            findNavController().navigate(action)
        }*/
    }

    private fun showMyDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_user_address_delete_confirmation_dialog, null)
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()

        val okButton = dialogView.findViewById<TextView>(R.id.tv_no)
        okButton.setOnClickListener {
            // Handle button click here
            alertDialog.dismiss()
        }
    }

}