package liz.agyei.pixify

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import liz.agyei.pixify.databinding.ActivityPhotoDetailBinding
import liz.agyei.pixify.preference.MyPreferenceActivity
import liz.agyei.pixify.viewmodel.BookmarksViewModel
import java.util.*

class PhotoDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhotoDetailBinding
    lateinit var viewModel: BookmarksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_detail)

        //Get Budle Param
        var bundle = intent.getBundleExtra("photo")
        var imageURL = bundle.getString("photo_url")
        var photoId = bundle.getString("photo_id")
        var photoTitle = bundle.getString("photo_title")

        if (!imageURL?.isEmpty()!!)
            Glide.with(this)
            .load(imageURL)
            .into(binding.ivPhotoUrl)
        binding.photoId = photoId
        binding.photoTitle = photoTitle

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
