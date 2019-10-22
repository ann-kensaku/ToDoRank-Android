package jp.ann.kensaku.todorank

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import jp.ann.kensaku.todorank.databinding.ActivityRankBinding

class RankActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRankBinding>(
            this,
            R.layout.activity_rank
        )

        intent?.apply {
            binding.targetString = getStringExtra(ARGUMENT_NEW_ITEM)
            binding.compareString = getStringExtra(ARGUMENT_COMPARE_ITEM)
        }

        // 画面のサイズを取得する
        val width = DisplayMetrics().let { dm ->
            windowManager.defaultDisplay.getMetrics(dm)
            dm.widthPixels
        }

        binding.targetText.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE -> {
                    // Targetのテキストが動かしている途中も見えるように、少し上に表示する
                    view.y += motionEvent.y - view.height * 3 / 2
                    view.x += motionEvent.x - view.width / 2
                }
                MotionEvent.ACTION_UP -> {
                    if (motionEvent.rawX >= width * 2 / 3) {
                        setAnswer(1)
                        finish()
                    } else if (motionEvent.rawX <= width / 3) {
                        setAnswer(-1)
                        finish()
                    }
                }
            }
            true
        }
    }

    private fun setAnswer(value: Int) {
        setResult(RESULT_OK, Intent().apply {
            putExtra("ANSWER", value)
        })
    }

    companion object {
        private const val ARGUMENT_NEW_ITEM = "argumentNewItem"
        private const val ARGUMENT_COMPARE_ITEM = "argumentCompareItem"

        fun launch(activity: Activity, requestCode: Int, newItem: String, compareItem: String) {
            val intent = Intent(activity, RankActivity::class.java).apply {
                putExtra(ARGUMENT_NEW_ITEM, newItem)
                putExtra(ARGUMENT_COMPARE_ITEM, compareItem)
            }
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
