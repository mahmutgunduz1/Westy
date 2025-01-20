package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.Model.CategoriesModel
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.BottomBinding
import com.mahmutgunduz.westy.databinding.RecyclerRowCategoriesBinding
import androidx.room.Room
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesDataBase
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CategoriesAdapter(private val categoriesList: ArrayList<CategoriesModel>, private val context: Context) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {

    private val myListBottomSheet = ArrayList<BottomShetModelSubn>()
    private lateinit var dao: FavoritesDao

    init {
        // Room veritabanı bağlantısı
        dao = FavoritesDataBase.getDatabase(context).noteDao()
    }

    class CategoriesHolder(val binding: RecyclerRowCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val binding = RecyclerRowCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoriesHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val currentItem = categoriesList[position]

        holder.binding.apply {
            categoryName.text = currentItem.textView
            categoryIcon.setImageResource(currentItem.imageView)
            arrowIcon.setImageResource(R.drawable.ic_arrow_right)

            // Tüm card view'a tıklama işlevi ekle
            root.setOnClickListener {
                handleCategoryClick(currentItem)
            }
        }
    }

    private fun handleCategoryClick(currentItem: CategoriesModel) {
        myListBottomSheet.clear()
        
        when (currentItem.id) {
            0 -> { // Üst Giyim
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(1, "T-Shirtler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(2, "Bluzlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(3, "Kazak ve Hırkalar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(4, "Sweatshirtler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(5, "Tunikler", R.drawable.shop, 0.0))
                }
            }
            1 -> { // Alt Giyim
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(6, "Pantolonlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(7, "Şortlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(8, "Etekler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(9, "Eşofman Altı", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(10, "Salopetler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(11, "Tulumlar", R.drawable.shop, 0.0))
                }
            }
            2 -> { // Ayakkabı
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(12, "Spor Ayakkabılar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(13, "Botlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(14, "Topuklu Ayakkabılar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(15, "Sandaletler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(16, "Babetler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(17, "Terlikler", R.drawable.shop, 0.0))
                }
            }
            3 -> { // Aksesuar
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(18, "Çantalar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(19, "Şapkalar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(20, "Gözlükler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(21, "Takılar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(22, "Saatler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(23, "Kemerler", R.drawable.shop, 0.0))
                }
            }
            4 -> { // Dış Giyim
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(24, "Montlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(25, "Ceketler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(26, "Kabanlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(27, "Trençkotlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(28, "Yelekler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(29, "Panço", R.drawable.shop, 0.0))
                }
            }
            5 -> { // Yeni Gelenler
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(30, "Yeni Sezon Ürünleri", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(31, "Özel Koleksiyonlar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(32, "Mevsimlik Ürünler", R.drawable.shop, 0.0))
                }
            }
            6 -> { // Çok Satanlar
                myListBottomSheet.apply {
                    add(BottomShetModelSubn(33, "En Çok Satılan Tişörtler", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(34, "En Çok Satılan Ayakkabılar", R.drawable.shop, 0.0))
                    add(BottomShetModelSubn(35, "En Çok Satılan Çantalar", R.drawable.shop, 0.0))
                }
            }
        }

        showBottomSheet(currentItem.textView)
    }

    private fun showBottomSheet(title: String) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetStyles_Dialog)
        val bottomBinding = BottomBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(bottomBinding.root)

        // BottomSheet davranışını ayarla
        val behavior = bottomSheetDialog.behavior
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
            isDraggable = true
        }

        // RecyclerView ayarları
        bottomBinding.recyclerViewBottom.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BottomSheetAdapter(
                bottomSheetModel = myListBottomSheet,
                context = context,
                bottomSheet = bottomSheetDialog
            )
            setHasFixedSize(true)
        }

        // Başlık ayarla
        bottomBinding.titleText.text = title

        // Animasyon ile göster
        bottomSheetDialog.window?.attributes?.windowAnimations = R.style.BottomSheetStyles_Animation
        bottomSheetDialog.show()
    }
}



