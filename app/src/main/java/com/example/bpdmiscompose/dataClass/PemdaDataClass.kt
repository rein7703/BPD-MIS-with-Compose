package com.example.bpdmiscompose.dataClass

import androidx.annotation.StringRes
import com.example.bpdmiscompose.R

enum class PemdaDataClass(@StringRes val title : Int){
    DIY(title = R.string.pemerintah_provinsi_diy),
    Bantul(title = R.string.pemerintah_kabupaten_bantul),
    GunungKidul(title = R.string.pemerintah_kabupaten_gunungkidul),
    KulonProgo(title = R.string.pemerintah_kabupaten_kulonprogo),
    Sleman(title = R.string.pemerintah_kabupaten_sleman),
    Yogyakarta(title = R.string.pemerintah_kota_yogya)

}
