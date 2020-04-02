package com.example.qrscan

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    //inisialisasi library scan QR
    private lateinit var intentIntegrator: IntentIntegrator
    var scanned = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //menentukan layout mana yang digunakan pada class MainActivity
        setContentView(R.layout.activity_main)

        //memanggil fungsi initOnClick
        initOnClickListener()
    }

    override fun onStart() {
        super.onStart()

//        Ketika screen orientation berubah, dan user telah melakukan scan
        if (scanned) {
            cv_content.visibility = View.VISIBLE
            cvPreviewAnimal.visibility = View.VISIBLE
            tvNotFound.visibility = View.GONE
        }else {
            cv_content.visibility = View.GONE
            cvPreviewAnimal.visibility = View.GONE
            tvNotFound.visibility = View.VISIBLE
        }
    }


    fun initOnClickListener() {
//        fungsi ini akan melakukan inisialisasi click pada tiap button di layout activity_main

        buttonScan.setOnClickListener {
//            inisialisasi variabel
            intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setPrompt("Arahkan kamera ke kode yang akan di pindai")
//            membuka kamera untuk scanning
            intentIntegrator.initiateScan()
        }

        ivBack.setOnClickListener {
//            balik ke halaman sebelumnya
            onBackPressed()
        }
    }

//        fungsi ini akan dipanggil jika QR scan mendapatkan sesuatu (entah isinya benar atau tidak)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            scanned = true
            if (result.contents == null) {
//                jika isi QR code salah, tampilkan toast dan menghapus teks data
                Toast.makeText(this,"Maaf kode tidak cocok, silahkan scan kembali",Toast.LENGTH_SHORT).show()

                tvKingdom.text = ""
                tvFilum.text = ""
                tvKelas.text = ""
                tvOrdo.text = ""
                tvFamili.text = ""
                tvGenus.text = ""
                tvSpecies.text = ""
                tvFun.text = ""
                ivPreviewAnimal.setImageResource(0)
                scanned=false

            } else {
                // jika qrcode berisi data
                try {
                    // converting data json yang didapat dari QR
                    val obj = JSONObject(result.contents)
                    // atur nilai ke tiap textviews
                    tvKingdom.text = obj.getString("kingdom")
                    tvFilum.text = obj.getString("filum")
                    tvKelas.text = obj.getString("kelas")
                    tvOrdo.text = obj.getString("ordo")
                    tvFamili.text = obj.getString("famili")
                    tvGenus.text = obj.getString("genus")
                    tvSpecies.text = obj.getString("species")
                    tvFun.text = obj.getString("fun")


                    val code = obj.getString("code")
                    if (code == "kera") {
                        ivPreviewAnimal.setImageResource(R.drawable.kera)
                    } else if (code == "singa") {
                        ivPreviewAnimal.setImageResource(R.drawable.singa)
                    }
//                    tambahin else if untuk nambah data hewan , gambar jgn lupa dimasukkan ke folder drawable (seperti contoh dibawah)
//                    else if (code == "nama-kode"){
//                        ivPreviewAnimal.setImageResource(R.drawable.<nama-gambar>)
//                    }


                } catch (e: JSONException) {
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this,"Maaf kode tidak cocok, silahkan scan kembali",Toast.LENGTH_SHORT).show()
                    tvKingdom.text = ""
                    tvFilum.text = ""
                    tvKelas.text = ""
                    tvOrdo.text = ""
                    tvFamili.text = ""
                    tvGenus.text = ""
                    tvSpecies.text = ""
                    tvFun.text = ""
                    ivPreviewAnimal.setImageResource(0)
                    scanned=false
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
