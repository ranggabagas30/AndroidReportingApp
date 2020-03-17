package com.domikado.bit.ui.screen.mainmenu


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.ui.screen.SettingActivity
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_main_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : BaseFragment() {

    private val menuScheduleTitle = "Jadwal"
    private val menuOTDRTitle = "FO Monitoring"
    private val menuSettings = "Pengaturan"

    private lateinit var cardMenuAllWorkTypes: MaterialCardView
    private lateinit var cardMenuOTDR: MaterialCardView
    private lateinit var cardMenuSettings: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardMenuAllWorkTypes = main_menu_item_schedule as MaterialCardView
        cardMenuOTDR     = main_menu_item_otdr as MaterialCardView
        cardMenuSettings = main_menu_item_settings as MaterialCardView

        initMenus(cardMenuAllWorkTypes, MenuModel.ScheduleMenuModel(R.drawable.ic_menu_schedule, menuScheduleTitle, -1))
        initMenus(cardMenuOTDR, MenuModel.ScheduleMenuModel(R.drawable.ic_menu_otdr, menuOTDRTitle, 1))
        initMenus(cardMenuSettings, MenuModel.SettingMenuModel(R.drawable.ic_menu_settings, menuSettings))
    }

    private fun initMenus(cardView: MaterialCardView, menuModel: MenuModel) {
        when(menuModel) {
            is MenuModel.ScheduleMenuModel -> {
                cardView.findViewById<AppCompatImageView>(R.id.main_menu_item_logo).setImageDrawable(getDrawable(requireContext(), menuModel.logoResId))
                cardView.findViewById<AppCompatTextView>(R.id.main_menu_item_title).text = menuModel.title
                cardView.setOnClickListener { navigateToScheduleList(menuModel.workTypeId) }
            }
            is MenuModel.SettingMenuModel -> {
                cardView.findViewById<AppCompatImageView>(R.id.main_menu_item_logo).setImageDrawable(getDrawable(requireContext(), menuModel.logoResId))
                cardView.findViewById<AppCompatTextView>(R.id.main_menu_item_title).text = menuModel.title
                cardView.setOnClickListener { navigateToSettings() }
            }
        }
        
    }
    
    private fun navigateToScheduleList(workTypeId: Int) {
        val action = MainMenuFragmentDirections.actionMainMenuFragmentToScheduleListFragment(workTypeId)
        findNavController().navigate(action)
    }
    
    private fun navigateToSettings() {
        val intent = Intent(requireActivity(), SettingActivity::class.java)
        requireActivity().startActivity(intent)
    }
    
    sealed class MenuModel {
        data class ScheduleMenuModel(@DrawableRes val logoResId: Int, val title: String, val workTypeId: Int): MenuModel() 
        data class SettingMenuModel(val logoResId: Int, val title: String): MenuModel() 
    }
}
