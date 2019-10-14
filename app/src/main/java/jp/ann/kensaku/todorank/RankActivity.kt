package jp.ann.kensaku.todorank

import android.annotation.SuppressLint
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
            binding.targetString = getStringExtra("NEWITEM")
            binding.compareString = getStringExtra("COMPAREITEM")
        }

        // 画面のサイズを取得する
        val width = DisplayMetrics().let { dm ->
            windowManager.defaultDisplay.getMetrics(dm)
            dm.widthPixels
        }

        binding.targetText.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE -> {
                    // TODO: 位置調整する。多分ActionBar分ずれている
                    view.y = motionEvent.rawY - view.height / 2
                    view.x = motionEvent.rawX - view.width / 2
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
}
