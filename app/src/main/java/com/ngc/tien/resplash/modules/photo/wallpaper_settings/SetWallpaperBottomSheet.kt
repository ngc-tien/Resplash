package com.ngc.tien.resplash.modules.photo.wallpaper_settings

import android.app.WallpaperManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.databinding.SetWallpaperBottomSheetLayoutBinding

class SetWallpaperBottomSheet : BottomSheetDialogFragment() {
    private var callBack: CallBack? = null

    private var _binding: SetWallpaperBottomSheetLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = SetWallpaperBottomSheetLayoutBinding.inflate(
        inflater,
        container,
        false,
    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            dismiss()
        }
        binding.homeScreenButton.setOnClickListener {
            callBack?.setWallpaper(WallpaperManager.FLAG_SYSTEM)
        }
        binding.lockScreenButton.setOnClickListener {
            callBack?.setWallpaper(WallpaperManager.FLAG_LOCK)
        }
        binding.homeAndLockScreenButton.setOnClickListener {
            callBack?.setWallpaper(WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
        }
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    override fun getTheme(): Int {
        return R.style.ThemeOverlay_Theme_BottomSheetDialog
    }

    override fun onDestroyView() {
        callBack = null
        _binding = null
        super.onDestroyView()
    }

    interface CallBack {
        fun setWallpaper(flag: Int)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}