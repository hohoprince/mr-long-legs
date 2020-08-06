package com.kidari.mrlonglegs.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    val searchTitleFragment =
        SearchTitleFragment()
    val surroundingsFragment =
        SurroundingsFragment()
    val categoryFragment = CategoryFragment()
    var curFragment: Fragment = searchTitleFragment
    val fragments: Array<Fragment> = arrayOf(searchTitleFragment, surroundingsFragment, categoryFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager?.beginTransaction()
            ?.add(R.id.searchContainer, searchTitleFragment)
            ?.add(R.id.searchContainer, surroundingsFragment)
            ?.hide(surroundingsFragment)
            ?.add(R.id.searchContainer, categoryFragment)
            ?.hide(categoryFragment)?.commit()

        //상단 탭
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    fragmentManager?.beginTransaction()
                        ?.show(fragments[tab.position])
                        ?.hide(curFragment)?.commit()
                    curFragment = fragments[tab.position]
                }
            }
        })
    }
}