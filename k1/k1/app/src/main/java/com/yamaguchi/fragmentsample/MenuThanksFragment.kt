package com.yamaguchi.fragmentsample


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_menu_thanks.*

/**
 * A simple [Fragment] subclass.
 */
class MenuThanksFragment : Fragment() {
    /*
    * 大画面かどうかの判定フラグ。
    * trueが大画面、falseが通常画面。
    * 判定ロジックは同一画面にリストフラグメントが存在するかで行う。
    */
    private var _isLayoutXLarge = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // フラグメントマネージャーからメニューリストフラグメントを取得。
        val menuListFragment = fragmentManager?.findFragmentById(R.id.fragmentMenuList)
        // メニューリストフラグメントがnull,つまり存在しないなら・・・
        if (menuListFragment == null){
            // 画面判定フラグを通常画面とする。
            _isLayoutXLarge = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // フラグメントで表示する画面をXMLファイルからインフレ―とする。
        val view = inflater.inflate(R.layout.fragment_menu_thanks, container, false)

        // Bundleオブジェクトを宣言。
        val extras: Bundle?
        // 大画面の場合・・・
        if (_isLayoutXLarge){
            // このフラグメントに埋め込まれた引き継ぎデータを取得。
            extras = arguments
        }
        //通常画面の場合
        else{
            // 所属アクティビティからインテントを取得。
            val intent = activity?.intent
            // インテントから引き継ぎデータをまとめたもの（Bundleオブジェクト）を取得。
            extras = intent?.extras
        }

        val menuName = extras?.getString("menuName")
        val menuPrice = extras?.getString("menuPrice")
        // 定食名と金額を表示させるTextViewを取得。
        val tvMenuName = view.findViewById<TextView>(R.id.tvMenuName)
        val tvMenuPrice = view.findViewById<TextView>(R.id.tvMenuPrice)
        // TextViewに定食名と金額を表示。
        tvMenuName.text = menuName
        tvMenuPrice.text = menuPrice

        // 戻るボタンを取得。
        val btBackButton = view.findViewById<Button>(R.id.btBackButton)
        // 戻るボタンにリスナを登録。
        btBackButton.setOnClickListener(ButtonClickListener())

        //インフレ―とされた画面を戻り値として返す。
        return view
    }

    // ボタンが押された時の処理が医術されたメンバクラス。
    private inner class ButtonClickListener : View.OnClickListener{
        override fun onClick(v: View?) {
            // 大画面の場合
            if (_isLayoutXLarge){
                // フラグメントトランザクションの開始。
                val transaction = fragmentManager?.beginTransaction()
                // 自分自身を削除。
                transaction?.remove(this@MenuThanksFragment)
                // フラグメントトランザクションのコミット。
                transaction?.commit()
            }
            // 通常画面の場合・・・
            else{
                // 自分が所属するアクティビティを終了。
                activity?.finish()
            }

        }
    }
}
