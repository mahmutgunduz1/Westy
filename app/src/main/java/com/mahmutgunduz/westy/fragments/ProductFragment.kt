package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Dao
import androidx.room.Room
import com.mahmutgunduz.westy.Model.BottomSheetModelSubn
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.adapters.ProductAdapter
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesDataBase
import com.mahmutgunduz.westy.databinding.FragmentProductBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable


class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val bottomShetModels = ArrayList<BottomShetModelSubn>()
    private lateinit var dao: FavoritesDao
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCategoryId = arguments?.getInt("selectedCategoryId") ?: -1
        deneme(selectedCategoryId)

        dao = FavoritesDataBase.getDatabase(requireContext()).noteDao()
        compositeDisposable = CompositeDisposable()

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.Rcv.layoutManager = layoutManager
        val adapter = ProductAdapter(bottomShetModels, requireContext(), dao)
        binding.Rcv.adapter = adapter
    }

    private fun deneme(selectedCategoryId: Int) {
        bottomShetModels.clear()
        when (selectedCategoryId) {
            0 -> addTshirtCategories()
            1 -> addShirtCategories()
            2 -> addBlouseCategories()
            3 -> addSweatshirtCategories()
            4 -> addTunikCategories()
            5 -> addPantsCategories()
            6 -> addShortsCategories()
            7 -> addCoatsCategories()
            8 -> addDressesCategories()
            9 -> addPantsCategories()
            10 -> addSalopetCategories()
            11 -> addTulumCategories()
            12 -> addSporAyakkabılarCategories()
            13 -> addBotlarCategories()
            14 -> addTopukluAyakkabılarCategories()
            15 -> addSandaletlerCategories()
            16 -> addBabetlerCategories()
            17 -> addTerliklerCategories()
            18 -> addÇantalarCategories()
            19 -> addŞapkalarCategories()
            20 -> addGözlüklerCategories()
            21 -> addTakılarCategories()
            22 -> addSaatlerCategories()
            23 -> addKemerlerCategories()
            24 -> addMontlarCategories()
            25 -> addCeketlerCategories()
            26 -> addKabanlarCategories()
            27 -> addTrençkotlarCategories()
            28 -> addYeleklerCategories()
            29 -> addPançoCategories()
            30 -> addYeniSezonÜrünleriCategories()
            31 -> addÖzelKoleksiyonlarCategories()
            32 -> addMevsimlikÜrünlerCategories()
            33 -> addEnÇokSatılanTişörtlerCategories()
            34 -> addEnÇokSatılanAyakkabılarCategories()
            35 -> addEnÇokSatılanÇantalarCategories()
            else -> addDiğerKategorilerCategories()
        }
    }

    private fun addTshirtCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Tişörtler", R.drawable.t, 100.0,2.6,4.4,"Temel Tişört"))
        bottomShetModels.add(BottomShetModelSubn(1, "Basic Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"Sade Tasarım"))
        bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Tişörtler", R.drawable.photo5, 100.0,2.6,4.4,"Özel Baskı"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"Modern Desen"))
        bottomShetModels.add(BottomShetModelSubn(4, "Oversize Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"Rahat Kesim"))
        bottomShetModels.add(BottomShetModelSubn(5, "Polo Yaka Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"Şık Tasarım"))
    }

    private fun addShirtCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Gömlekler", R.drawable.photo9, 100.0,2.6,4.4,"Klasik Gömlek"))
        bottomShetModels.add(BottomShetModelSubn(1, "Düz Gömlekler", R.drawable.photo9, 100.0,2.6,4.4,"Sade Tasarım"))
        bottomShetModels.add(BottomShetModelSubn(2, "Desenli Gömlekler", R.drawable.photo9, 100.0,2.6,4.4,"Modern Desen"))
        bottomShetModels.add(BottomShetModelSubn(3, "Gömlek Elbiseler", R.drawable.photo9, 100.0,2.6,4.4,"Şık Kesim"))
        bottomShetModels.add(BottomShetModelSubn(4, "Viskon Gömlekler", R.drawable.photo9, 100.0,2.6,4.4,"Rahat Kumaş"))
        bottomShetModels.add(BottomShetModelSubn(5, "Kot Gömlekler", R.drawable.photo9, 100.0,2.6,4.4,"Jean Stil"))
    }

    private fun addBlouseCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Bluzlar", R.drawable.photo9, 100.0,2.6,4.4,"Klasik Bluz"))
        bottomShetModels.add(BottomShetModelSubn(1, "Pamuk Bluzlar", R.drawable.photo9, 100.0,2.6,4.4,"Pamuklu Kumaş"))
        bottomShetModels.add(BottomShetModelSubn(2, "Şifon Bluzlar", R.drawable.photo9, 100.0,2.6,4.4,"Şifon Detay"))
        bottomShetModels.add(BottomShetModelSubn(3, "Bluz Elbiseler", R.drawable.photo9, 100.0,2.6,4.4,"Uzun Kesim"))
        bottomShetModels.add(BottomShetModelSubn(4, "Salaş Bluzlar", R.drawable.photo9, 100.0,2.6,4.4,"Rahat Form"))
        bottomShetModels.add(BottomShetModelSubn(5, "V Yaka Bluzlar", R.drawable.photo9, 100.0,2.6,4.4,"V Yaka Detay"))
    }

    private fun addSweatshirtCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Sweatshirtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kapüşonlu Sweatshirtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Sweatshirtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Oversize Sweatshirtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Fermuarlı Sweatshirtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "V Yaka Sweatshirtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addTunikCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Tunikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kısa Tunikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Uzun Tunikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Çift Yönlü Tunikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Süet Tunikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Desenli Tunikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addPantsCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Pantolonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Jean Pantolonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Kumaş Pantolonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Kısa Pantolonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Desenli Pantolonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Yüksek Bel Pantolonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addShortsCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Şortlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kumaş Şortlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Jean Şortlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Şortlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Yüksek Bel Şortlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Düşük Bel Şortlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addCoatsCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Etekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Mini Etekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Midi Etekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Maksi Etekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Pileli Etekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Düz Kesim Etekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addDressesCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Eşofman Altı", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Dar Kesim Eşofman Altı", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Bol Kesim Eşofman Altı", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Eşofman Altı", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Kısa Paça Eşofman Altı", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Yüksek Bel Eşofman Altı", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addSalopetCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Salopetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kot Salopetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Kısa Salopetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Uzun Salopetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Desenli Salopetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Askılı Salopetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addTulumCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Tulumlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kot Tulumlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Tulumlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Tulumlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Yazlık Tulumlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Kışlık Tulumlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addSporAyakkabılarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Spor Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Koşu Ayakkabıları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Basketbol Ayakkabıları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Futbol Ayakkabıları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Eğitim Ayakkabıları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Fitness Ayakkabıları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addBotlarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Botlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kış Botları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Çizme Botlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Yağmurluk Botlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Günlük Botlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Süet Botlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addTopukluAyakkabılarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Topuklu Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Stiletto Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Yüksek Topuklu Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Kalın Topuklu Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Balerin Topuklu Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Platform Topuklu Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addSandaletlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Topuklu Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Düz Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Bilekten Bağlamalı Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Süet Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Platform Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addBabetlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Düz Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Topuklu Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Süet Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Baskılı Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Deri Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addTerliklerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Terlikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Plaj Terlikleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Ev Terlikleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Topuklu Terlikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Deri Terlikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Süet Terlikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addÇantalarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Omuz Çantaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Sırt Çantaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "El Çantaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Cüzdanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Clutch Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addŞapkalarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Şapkalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Bere ve Şapkalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Panama Şapkalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Beyzbol Şapkaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Şapka Aksesuarları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Yazlık Şapkalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addGözlüklerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Gözlükler", R.drawable.photo9, 110.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Güneş Gözlükleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Optik Gözlükler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Spor Gözlükleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Aksesuar Gözlükleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Klasik Gözlükler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addTakılarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Takılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kolye ve Yüzükler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Bilezikler ve Bileklikler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Küpe ve Salıncaklar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Broşlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Saatli Takılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addSaatlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Saatler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Erkek Saatleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Kadın Saatleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Akıllı Saatler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Vintage Saatler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Fiyatlı Saatler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addKemerlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Kemerler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Deri Kemerler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Geniş Kemerler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Klasik Kemerler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "İş Kemerleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Renkli Kemerler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addMontlarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kısa Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Uzun Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Kapüşonlu Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Spor Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Derili Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addCeketlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Ceketler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Baskılı Ceketler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Kot Ceketler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Blazer Ceketler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Bermuda Ceketler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Kısa Ceketler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addKabanlarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Kabanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Yün Kabanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Kadın Kabanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Erkek Kabanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Kalın Kabanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Modern Kabanlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addTrençkotlarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Trençkotlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kadın Trençkotlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Erkek Trençkotlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Trençkotlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Yaz Trençkotları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Siyah Trençkotlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addYeleklerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Yelekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Düz Yelekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Yün Yelekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Kadın Yelekleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Erkek Yelekleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Desenli Yelekler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addPançoCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Panço", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Kadın Pançolar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Erkek Pançolar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Pançolar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Büyük Beden Pançolar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Yün Pançolar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addYeniSezonÜrünleriCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Yeni Sezon Ürünleri", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Yeni Sezon Montlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Yeni Sezon Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Yeni Sezon Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Yeni Sezon Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Yeni Sezon Aksesuarlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addÖzelKoleksiyonlarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Özel Koleksiyonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "İşlemeli Koleksiyonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Sınırlı Üretim Koleksiyonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Tasarımcı Koleksiyonları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Vintage Koleksiyonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Lüks Koleksiyonlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addMevsimlikÜrünlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Mevsimlik Ürünler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Yazlık Ürünler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Kışlık Ürünler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Sonbahar/Kış Koleksiyonu", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "İlkbahar/Yaz Koleksiyonu", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Mevsim Sonu İndirimli Ürünler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addEnÇokSatılanTişörtlerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "En Çok Satılan Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Basic Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Desenli Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Oversize Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Polo Yaka Tişörtler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addEnÇokSatılanAyakkabılarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "En Çok Satılan Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Spor Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Botlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Topuklu Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Sandaletler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Babetler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addEnÇokSatılanÇantalarCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "En Çok Satılan Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Sırt Çantaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "El Çantaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Omuz Çantaları", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Clutch Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Büyük Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }

    private fun addDiğerKategorilerCategories() {
        bottomShetModels.add(BottomShetModelSubn(0, "Diğer Kategoriler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(1, "Aksesuarlar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(2, "Ayakkabılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(3, "Çantalar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(4, "Takılar", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
        bottomShetModels.add(BottomShetModelSubn(5, "Saatler", R.drawable.photo9, 100.0,2.6,4.4,"deneme"))
    }
}