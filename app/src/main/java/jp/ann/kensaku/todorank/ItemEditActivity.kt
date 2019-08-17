package jp.ann.kensaku.todorank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ItemEditActivity : AppCompatActivity() {
    lateinit var title: String
    lateinit var edit_text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)

        edit_text = findViewById(R.id.edit_text) as TextView

        val intent = getIntent()
        title = intent.getStringExtra("title")

        edit_text.text = title
    }
}
