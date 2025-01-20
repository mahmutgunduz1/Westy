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
            0 -> {
                // Tişörtler
                bottomShetModels.add(BottomShetModelSubn(0, "Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Basic Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Oversize Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Polo Yaka Tişörtler", R.drawable.photo9, 100.0))

            }

            1 -> {
                // Gömlekler
                bottomShetModels.add(BottomShetModelSubn(0, "Gömlekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Düz Gömlekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Desenli Gömlekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Gömlek Elbiseler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Viskon Gömlekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Kot Gömlekler", R.drawable.photo9, 100.0))

            }

            2 -> {
                // Bluzlar
                bottomShetModels.add(BottomShetModelSubn(0, "Bluzlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Pamuk Bluzlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Şifon Bluzlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Bluz Elbiseler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Salaş Bluzlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "V Yaka Bluzlar", R.drawable.photo9, 100.0))

            }

            3 -> {
                // Kazak ve Hırkalar
                bottomShetModels.add(BottomShetModelSubn(0, "Kazak ve Hırkalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Yün Kazaklar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Triko Kazaklar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Hırkalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Oversize Kazaklar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Büyük Beden Kazaklar", R.drawable.photo9, 100.0))

            }

            4 -> {
                // Sweatshirtler
                bottomShetModels.add(BottomShetModelSubn(0, "Sweatshirtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kapüşonlu Sweatshirtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Sweatshirtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Oversize Sweatshirtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Fermuarlı Sweatshirtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "V Yaka Sweatshirtler", R.drawable.photo9, 100.0))

            }

            5 -> {
                // Tunikler
                bottomShetModels.add(BottomShetModelSubn(0, "Tunikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kısa Tunikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Uzun Tunikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Çift Yönlü Tunikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Süet Tunikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Desenli Tunikler", R.drawable.photo9, 100.0))

            }

            6 -> {
                // Pantolonlar
                bottomShetModels.add(BottomShetModelSubn(0, "Pantolonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Jean Pantolonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Kumaş Pantolonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Kısa Pantolonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Desenli Pantolonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Yüksek Bel Pantolonlar", R.drawable.photo9, 100.0))
            }

            7 -> {
                // Şortlar
                bottomShetModels.add(BottomShetModelSubn(0, "Şortlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kumaş Şortlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Jean Şortlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Şortlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Yüksek Bel Şortlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Düşük Bel Şortlar", R.drawable.photo9, 100.0))
            }

            8 -> {
                // Etekler
                bottomShetModels.add(BottomShetModelSubn(0, "Etekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Mini Etekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Midi Etekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Maksi Etekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Pileli Etekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Düz Kesim Etekler", R.drawable.photo9, 100.0))

            }

            9 -> {
                // Eşofman Altı
                bottomShetModels.add(BottomShetModelSubn(0, "Eşofman Altı", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Dar Kesim Eşofman Altı", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Bol Kesim Eşofman Altı", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Eşofman Altı", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Kısa Paça Eşofman Altı", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Yüksek Bel Eşofman Altı", R.drawable.photo9, 100.0))
            }

            10 -> {
                // Salopetler
                bottomShetModels.add(BottomShetModelSubn(0, "Salopetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kot Salopetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Kısa Salopetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Uzun Salopetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Desenli Salopetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Askılı Salopetler", R.drawable.photo9, 100.0))
            }

            11 -> {
                // Tulumlar
                bottomShetModels.add(BottomShetModelSubn(0, "Tulumlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kot Tulumlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Tulumlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Tulumlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Yazlık Tulumlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Kışlık Tulumlar", R.drawable.photo9, 100.0))
            }

            12 -> {
                // Spor Ayakkabılar
                bottomShetModels.add(BottomShetModelSubn(0, "Spor Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Koşu Ayakkabıları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Basketbol Ayakkabıları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Futbol Ayakkabıları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Eğitim Ayakkabıları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Fitness Ayakkabıları", R.drawable.photo9, 100.0))
            }

            13 -> {
                // Botlar
                bottomShetModels.add(BottomShetModelSubn(0, "Botlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kış Botları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Çizme Botlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Yağmurluk Botlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Günlük Botlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Süet Botlar", R.drawable.photo9, 100.0))
            }

            14 -> {
                // Topuklu Ayakkabılar
                bottomShetModels.add(BottomShetModelSubn(0, "Topuklu Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Stiletto Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Yüksek Topuklu Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Kalın Topuklu Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Balerin Topuklu Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Platform Topuklu Ayakkabılar", R.drawable.photo9, 100.0))
            }

            15 -> {
                // Sandaletler
                bottomShetModels.add(BottomShetModelSubn(0, "Sandaletler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Topuklu Sandaletler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Düz Sandaletler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Bilekten Bağlamalı Sandaletler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Süet Sandaletler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Platform Sandaletler", R.drawable.photo9, 100.0))
            }


            16 -> {
                // Babetler
                bottomShetModels.add(BottomShetModelSubn(0, "Babetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Düz Babetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Topuklu Babetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Süet Babetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Baskılı Babetler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Deri Babetler", R.drawable.photo9, 100.0))
            }

            17 -> {
                // Terlikler
                bottomShetModels.add(BottomShetModelSubn(0, "Terlikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Plaj Terlikleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Ev Terlikleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Topuklu Terlikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Deri Terlikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Süet Terlikler", R.drawable.photo9, 100.0))
            }

            18 -> {
                // Çantalar
                bottomShetModels.add(BottomShetModelSubn(0, "Çantalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Omuz Çantaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Sırt Çantaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "El Çantaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Cüzdanlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Clutch Çantalar", R.drawable.photo9, 100.0))
            }

            19 -> {
                // Şapkalar
                bottomShetModels.add(BottomShetModelSubn(0, "Şapkalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Bere ve Şapkalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Panama Şapkalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Beyzbol Şapkaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Şapka Aksesuarları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Yazlık Şapkalar", R.drawable.photo9, 100.0))
            }

            20 -> {
                // Gözlükler
                bottomShetModels.add(BottomShetModelSubn(0, "Gözlükler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Güneş Gözlükleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Optik Gözlükler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Spor Gözlükleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Aksesuar Gözlükleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Klasik Gözlükler", R.drawable.photo9, 100.0))
            }

            21 -> {
                // Takılar
                bottomShetModels.add(BottomShetModelSubn(0, "Takılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kolye ve Yüzükler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Bilezikler ve Bileklikler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Küpe ve Salıncaklar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Broşlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Saatli Takılar", R.drawable.photo9, 100.0))
            }

            22 -> {
                // Saatler
                bottomShetModels.add(BottomShetModelSubn(0, "Saatler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Erkek Saatleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Kadın Saatleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Akıllı Saatler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Vintage Saatler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Fiyatlı Saatler", R.drawable.photo9, 100.0))
            }

            23 -> {
                // Kemerler
                bottomShetModels.add(BottomShetModelSubn(0, "Kemerler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Deri Kemerler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Geniş Kemerler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Klasik Kemerler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "İş Kemerleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Renkli Kemerler", R.drawable.photo9, 100.0))
            }

            24 -> {
                // Montlar
                bottomShetModels.add(BottomShetModelSubn(0, "Montlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kısa Montlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Uzun Montlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Kapüşonlu Montlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Spor Montlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Derili Montlar", R.drawable.photo9, 100.0))
            }

            25 -> {
                // Ceketler
                bottomShetModels.add(BottomShetModelSubn(0, "Ceketler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Baskılı Ceketler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Kot Ceketler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Blazer Ceketler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Bermuda Ceketler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Kısa Ceketler", R.drawable.photo9, 100.0))
            }

            26 -> {
                // Kabanlar
                bottomShetModels.add(BottomShetModelSubn(0, "Kabanlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Yün Kabanlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Kadın Kabanlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Erkek Kabanlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Kalın Kabanlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Modern Kabanlar", R.drawable.photo9, 100.0))
            }

            27 -> {
                // Trençkotlar
                bottomShetModels.add(BottomShetModelSubn(0, "Trençkotlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kadın Trençkotlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Erkek Trençkotlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Trençkotlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Yaz Trençkotları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Siyah Trençkotlar", R.drawable.photo9, 100.0))
            }

            28 -> {
                // Yelekler
                bottomShetModels.add(BottomShetModelSubn(0, "Yelekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Düz Yelekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Yün Yelekler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Kadın Yelekleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Erkek Yelekleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Desenli Yelekler", R.drawable.photo9, 100.0))
            }

            29 -> {
                // Panço
                bottomShetModels.add(BottomShetModelSubn(0, "Panço", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Kadın Pançolar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Erkek Pançolar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Pançolar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Büyük Beden Pançolar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Yün Pançolar", R.drawable.photo9, 100.0))
            }

            30 -> {
                // Yeni Sezon Ürünleri
                bottomShetModels.add(BottomShetModelSubn(0, "Yeni Sezon Ürünleri", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Yeni Sezon Montlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Yeni Sezon Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Yeni Sezon Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Yeni Sezon Çantalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Yeni Sezon Aksesuarlar", R.drawable.photo9, 100.0))
            }

            31 -> {
                // Özel Koleksiyonlar
                bottomShetModels.add(BottomShetModelSubn(0, "Özel Koleksiyonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "İşlemeli Koleksiyonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Sınırlı Üretim Koleksiyonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Tasarımcı Koleksiyonları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Vintage Koleksiyonlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Lüks Koleksiyonlar", R.drawable.photo9, 100.0))
            }

            32 -> {
                // Mevsimlik Ürünler
                bottomShetModels.add(BottomShetModelSubn(0, "Mevsimlik Ürünler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Yazlık Ürünler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Kışlık Ürünler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Sonbahar/Kış Koleksiyonu", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "İlkbahar/Yaz Koleksiyonu", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Mevsim Sonu İndirimli Ürünler", R.drawable.photo9, 100.0))
            }

            33 -> {
                // En Çok Satılan Tişörtler
                bottomShetModels.add(BottomShetModelSubn(0, "En Çok Satılan Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Basic Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Baskılı Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Desenli Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Oversize Tişörtler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Polo Yaka Tişörtler", R.drawable.photo9, 100.0))
            }
            34 -> {
                // En Çok Satılan Ayakkabılar
                bottomShetModels.add(BottomShetModelSubn(0, "En Çok Satılan Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Spor Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Botlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Topuklu Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Sandaletler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Babetler", R.drawable.photo9, 100.0))
            }

            35 -> {
                // En Çok Satılan Çantalar
                bottomShetModels.add(BottomShetModelSubn(0, "En Çok Satılan Çantalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Sırt Çantaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "El Çantaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Omuz Çantaları", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Clutch Çantalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Büyük Çantalar", R.drawable.photo9, 100.0))
            }


            else -> {
                bottomShetModels.add(BottomShetModelSubn(0, "Diğer Kategoriler", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(1, "Aksesuarlar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(2, "Ayakkabılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(3, "Çantalar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(4, "Takılar", R.drawable.photo9, 100.0))
                bottomShetModels.add(BottomShetModelSubn(5, "Saatler", R.drawable.photo9, 100.0))
            }

        }


    }


}