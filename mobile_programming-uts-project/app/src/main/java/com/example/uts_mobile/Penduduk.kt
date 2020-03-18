package com.example.uts_mobile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_penduduk.*
import org.json.JSONObject

class Penduduk : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penduduk)

        back.setOnClickListener {
            startActivity(Intent(this@Penduduk, Dashboard::class.java))
            finish()
        }

        add.setOnClickListener {
            startActivity(Intent(this@Penduduk, Tambah::class.java))
            finish()
        }

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val penduduk = ArrayList<PendudukModel>()

        AndroidNetworking.get("http://192.168.1.30/loginuts/data_penduduk.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("id_penduduk"))
                        Log.e("_kotlinTitle", jsonObject.optString("nama_penduduk"))
                        Log.e("_kotlinTitle", jsonObject.optString("ttl_penduduk"))
                        Log.e("_kotlinTitle", jsonObject.optString("hp_penduduk"))
                        Log.e("_kotlinTitle", jsonObject.optString("alamat_penduduk"))

                        var isi1 = jsonObject.optString("id_penduduk").toString()
                        var isi2 = jsonObject.optString("nama_penduduk").toString()
                        var isi3 = jsonObject.optString("ttl_penduduk").toString()
                        var isi4 = jsonObject.optString("hp_penduduk").toString()
                        var isi5 = jsonObject.optString("alamat_penduduk").toString()

                        penduduk.add(PendudukModel("$isi1", "$isi2", "$isi3", "$isi4", "$isi5"))


                    }
                    val adapter = CustomAdapter(penduduk)
                    recyclerView.adapter = adapter

                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}