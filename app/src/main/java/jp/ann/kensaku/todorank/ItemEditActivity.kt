package jp.ann.kensaku.todorank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ItemEditActivity : AppCompatActivity() {
    lateinit var title: String
    lateinit var editText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)

        editText = findViewById(R.id.edit_text) as TextView

        val intent = getIntent()
        title = intent.getStringExtra("title") as String

        editText.text = title
    }
}
